package de.unitrier.dbis.schenql.compiler;

import de.unitrier.dbis.schenql.SchenqlLexer;
import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.compiler.listener.SyntaxErrorListener;
import de.unitrier.dbis.schenql.compiler.visitor.RootVisitor;
import de.unitrier.dbis.schenql.connection.DBConnection;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;


public class Schenql {
    static final boolean EXACT_MATCH_STRINGS = true;
    public static final int DEFAULT_QUERY_LIMIT = 100;
    private static final boolean DEBUG_MODE = true;

    public static void main(String[] args) {
        printWelcomeMessage();

        DBConnection dbConnection = new DBConnection();

        String generatedSQL;

        try {
            Terminal terminal = TerminalBuilder.terminal();
            LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
            String prompt = "schenql> ";

            while (true) {
                String query;
                try {
                    query = reader.readLine(prompt);
                } catch (UserInterruptException e) {
                    System.out.println("Bye Bye :)");
                    break;
                }

                if (!query.equals("exit;")) {
                    try {
                        CharStream charStream = CharStreams.fromString(query);

                        // Initializing the lexer
                        SchenqlLexer lexer = new SchenqlLexer(charStream);
                        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

                        // Initializing the parser
                        SchenqlParser parser = new SchenqlParser(commonTokenStream);

                        // Adding error handling
                        SyntaxErrorListener errorListener = new SyntaxErrorListener();
                        parser.addErrorListener(errorListener);

                        // Getting the rootContext
                        SchenqlParser.RootContext rootContext = parser.root();
                        RootVisitor visitor = new RootVisitor();

                        // Generate SQL
                        generatedSQL = visitor.visit(rootContext).buildQuery();

                        if (errorListener.getSyntaxErrors().size() == 0) {
                            if (DEBUG_MODE) System.out.println("Query: " + generatedSQL);

                            // Execute the query
                            dbConnection.executeQuery(generatedSQL);
                        } else {
                            System.out.println("You have an error in your SchenQL-Syntax.");
                        }
                    } catch (java.lang.NullPointerException e) {
                        e.printStackTrace();
                        System.out.println("Something went wrong here.");
                    }
                } else {
                    System.out.println("Bye Bye :)");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void printWelcomeMessage() {
        System.out.print(" ____       _                 ___  _\n");
        System.out.print("/ ___|  ___| |__   ___ _ __  / _ \\| |\n");
        System.out.print("\\___ \\ / __| '_ \\ / _ \\ '_ \\| | | | |   \n");
        System.out.print(" ___) | (__| | | |  __/ | | | |_| | |___\n");
        System.out.print("|____/ \\___|_| |_|\\___|_| |_|\\__\\_\\_____|\n\n");

        System.out.println("Welcome to SchenQL – The Schenql Query Language");
        System.out.println("Version: 1.0.2");
        System.out.println("Type 'exit;' for exit.\n");
    }
}
