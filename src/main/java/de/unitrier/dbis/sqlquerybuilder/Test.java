package de.unitrier.dbis.sqlquerybuilder;

import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.ConditionGroup;
import de.unitrier.dbis.sqlquerybuilder.condition.SubQueryCondition;

import static de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator.*;

public class Test {
    public static void main(String[] args) {
        Query query = new Query();
        Query query2 = new Query();
        query2.addSelect("dblpKey");
        query2.addFrom("publication");
        query.addSelect("title");
        query.addFrom("publication");
        query.addJoin("person_authored_publication", "publicationKey", "publication", "dblpKey");
        query.addJoin("person", "dblpKey", "person_authored_publication", "personKey");

        BooleanCondition cond1 = new BooleanCondition("publication", "title", EQUALS, "DBLP - Some Lessons Learned.");
        BooleanCondition cond2 = new BooleanCondition("publication", "title", LT, 2013);
        ConditionGroup condGroup = new ConditionGroup(cond1, cond2);
        SubQueryCondition sqcondition = new SubQueryCondition("dblpKey", query2);
        cond2.or();
        cond2.negate();

        query.addCondition(condGroup);
        query.addCondition(sqcondition);

        query.addGroupBy("title");
        query.addOrderBy("title");
        query.addOrderBy("person", "name");
        query.addLimit(10);
        System.out.println(query.buildQuery());
    }
}
