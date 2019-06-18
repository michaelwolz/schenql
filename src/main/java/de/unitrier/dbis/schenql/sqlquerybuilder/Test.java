package de.unitrier.dbis.schenql.sqlquerybuilder;

import de.unitrier.dbis.schenql.sqlquerybuilder.condition.SQLBooleanCondition;

import static de.unitrier.dbis.schenql.sqlquerybuilder.condition.SQLBooleanOperator.*;

public class Test {
    public static void main(String[] args) {
        SQLQuery query = new SQLQuery();
        query.addSelect("title");
        query.addFrom("publication");
        query.addJoin("person_authored_publication", "publicationKey", "publication", "dblpKey");
        query.addJoin("person", "dblpKey", "person_authored_publication", "personKey");

        SQLBooleanCondition cond1 = new SQLBooleanCondition("publication", "title", EQUALS, "DBLP - Some Lessons Learned.");
        SQLBooleanCondition cond2 = new SQLBooleanCondition("publication", "title", LT, 2013);
        cond2.or = true;
        cond2.negate = true;

        query.addCondition(cond1);
        query.addCondition(cond2);

        query.addGroupBy("title");
        query.addOrderBy("title");
        query.addOrderBy("person", "name");
        query.addLimit(10);
        System.out.println(query.buildQuery());
    }
}
