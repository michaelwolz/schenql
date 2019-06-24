package de.unitrier.dbis.sqlquerybuilder;

abstract class AbstractJoin {
    Field onField;
    Table onTable;

    abstract String createStatement();
}
