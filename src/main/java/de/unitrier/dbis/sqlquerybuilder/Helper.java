package de.unitrier.dbis.sqlquerybuilder;

public class Helper {
    public static String encloseInApostrophe(String str) {
        return "`" + str + "`";
    }
    public static String encloseInQMarks(String str) {
        return "'" + str + "'";
    }
}
