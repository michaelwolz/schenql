package de.unitrier.dbis.schenql.compiler;

import de.unitrier.dbis.schenql.SchenqlLexer;
import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.compiler.visitor.RootVisitor;
import de.unitrier.dbis.schenql.connection.DBConnection;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.Scanner;


public class Schenql {
    public static void main(String[] args) {
        printWelcomeMessage();

        DBConnection dbConnection = new DBConnection();

        String query;
        String generatedSQL;
        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.print("\nschenql> ");
            query = scan.nextLine();

            if (!query.equals("exit;")) {
                CharStream charStream = CharStreams.fromString(query);

                SchenqlLexer lexer = new SchenqlLexer(charStream);
                CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
                SchenqlParser parser = new SchenqlParser(commonTokenStream);

                SchenqlParser.RootContext rootContext = parser.root();

                RootVisitor visitor = new RootVisitor();
                generatedSQL = visitor.visit(rootContext);
                System.out.println("Query: " + generatedSQL);

                // Execute the query
                dbConnection.executeQuery(generatedSQL);
            } else {
                System.out.println("Bye Bye :)");
                break;
            }
        }
    }


    private static void printWelcomeMessage() {
        System.out.print(" ____       _                 ___  _\n");
        System.out.print("/ ___|  ___| |__   ___ _ __  / _ \\| |\n");
        System.out.print("\\___ \\ / __| '_ \\ / _ \\ '_ \\| | | | |   \n");
        System.out.print(" ___) | (__| | | |  __/ | | | |_| | |___\n");
        System.out.print("|____/ \\___|_| |_|\\___|_| |_|\\__\\_\\_____|\n\n");

        System.out.println("Welcome to SchenQL – The Schenkel Query Language");
        System.out.println("Version: 1.0.0");
        System.out.println("Type 'help;' for help. 'exit;' for exit.\n");
        System.out.println("Documentation is available at: https://dbis.uni-trier.de/xxx\n");
    }
}