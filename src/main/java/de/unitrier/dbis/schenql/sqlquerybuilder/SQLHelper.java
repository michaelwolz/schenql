package de.unitrier.dbis.schenql.sqlquerybuilder;

public class SQLHelper {
    public static String encloseInApostrophe(String str) {
        return "`" + str + "`";
    }
    public static String encloseInQMarks(String str) {
        return "'" + str + "'";
    }
}
