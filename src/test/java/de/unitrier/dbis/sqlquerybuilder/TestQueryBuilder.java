package de.unitrier.dbis.sqlquerybuilder;

import static org.junit.Assert.*;

import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator;
import de.unitrier.dbis.sqlquerybuilder.condition.ConditionGroup;
import de.unitrier.dbis.sqlquerybuilder.condition.SubQueryCondition;
import org.junit.Test;

public class TestQueryBuilder {
    @Test
    public void TestBuildQuerySelectFrom() {
        Query query = new Query();
        query.addSelect("title");
        query.addFrom("publication");
        assertEquals("SELECT `title` FROM `publication`;", query.buildQuery());
    }

    @Test
    public void TestBuildQueryMultipleSelectFrom() {
        Query query = new Query();
        query.addSelect("title");
        query.addSelect("year");
        query.addFrom("publication");
        assertEquals("SELECT `title`, `year` FROM `publication`;", query.buildQuery());
    }

    @Test
    public void TestBuildQueryGroupBy() {
        Query query = new Query();
        query.addSelect("title");
        query.addFrom("publication");
        query.addGroupBy("title");
        assertEquals("SELECT `title` FROM `publication` GROUP BY `title`;", query.buildQuery());
    }

    @Test
    public void TestBuildQueryOrderBy() {
        Query query = new Query();
        query.addSelect("title");
        query.addFrom("publication");
        query.addOrderBy("title");
        assertEquals("SELECT `title` FROM `publication` ORDER BY `title` ASC;", query.buildQuery());
    }

    @Test
    public void TestBuildQueryCondition() {
        Query query = new Query();
        BooleanCondition condition = new BooleanCondition("title", BooleanOperator.EQUALS, "Test");
        query.addSelect("title");
        query.addFrom("publication");
        query.addCondition(condition);
        assertEquals("SELECT `title` FROM `publication` WHERE `title` = 'Test';", query.buildQuery());
    }

    @Test
    public void TestBuildQuerySubQueryCondition() {
        Query subQuery = new Query();
        subQuery.addSelect("title");
        subQuery.addFrom("publication");

        Query query = new Query();
        query.addSelect("title");
        query.addFrom("publication");

        SubQueryCondition condition = new SubQueryCondition("title", subQuery);
        query.addCondition(condition);

        assertEquals("SELECT `title` FROM `publication` WHERE `title` IN (SELECT `title` FROM `publication`);", query.buildQuery());
    }

    @Test
    public void TestBuildQueryConditionGroup() {
        Query query = new Query();
        BooleanCondition condition1 = new BooleanCondition("title", BooleanOperator.EQUALS, "Test");
        BooleanCondition condition2 = new BooleanCondition("year", BooleanOperator.LTE, 2017);
        BooleanCondition condition3 = new BooleanCondition("dblpKey", BooleanOperator.EQUALS, "DBLP");
        condition2.or();
        condition2.negate();
        ConditionGroup conditionGroup = new ConditionGroup(condition1, condition2);
        query.addSelect("title");
        query.addFrom("publication");
        query.addCondition(conditionGroup);
        query.addCondition(condition3);
        assertEquals("SELECT `title` FROM `publication` WHERE (`title` = 'Test' OR NOT `year` <= 2017) AND `dblpKey` = 'DBLP';", query.buildQuery());
    }
}