package de.unitrier.dbis.sqlquerybuilder;

import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestEquals {
    @Test
    public void TestField() {
        Field f1 = new Field("test");
        Field f2 = new Field("test");
        Field f3 = new Field("tset");
        assertEquals(f1, f2);
        assertNotEquals(f1, f3);
    }

    @Test
    public void TestTable() {
        Table t1 = new Table("test");
        Table t2 = new Table("test");
        Table t3 = new Table("tset");
        assertEquals(t1, t2);
        assertNotEquals(t1, t3);
    }

    @Test
    public void TestSelect() {
        Select s1 = new Select("test", "test");
        Select s2 = new Select("test", "test");
        Select s3 = new Select("test");
        Select s4 = new Select("test");
        assertEquals(s1, s2);
        assertEquals(s3, s4);
        assertNotEquals(s1, s3);
    }

    @Test
    public void TestFrom() {
        From f1 = new From("test");
        From f2 = new From("test");
        From f3 = new From("tset");
        assertEquals(f1, f2);
        assertNotEquals(f1, f3);
    }

    @Test
    public void TestJoin() {
        Join j1 = new Join("a", "a", "b", "b");
        Join j2 = new Join("a", "a", "b", "b");
        Join j3 = new Join("a", "a", "b", "c");
        assertEquals(j1, j2);
        assertNotEquals(j1, j3);
    }

    @Test
    public void TestOrderBy() {
        OrderBy ob1 = new OrderBy("a", "b");
        OrderBy ob2 = new OrderBy("a", "b");
        OrderBy ob3 = new OrderBy("a", "b");
        ob3.setSortOrder("DESC");
        assertEquals(ob1, ob2);
        assertNotEquals(ob1, ob3);
    }

    @Test
    public void TestBooleanCondition() {
        BooleanCondition bc1 = new BooleanCondition(
                "test",
                "test",
                BooleanOperator.EQUALS,
                "test"
        );
        BooleanCondition bc2 = new BooleanCondition(
                "test",
                "test",
                BooleanOperator.EQUALS,
                "test"
        );
        BooleanCondition bc3 = new BooleanCondition(
                "test",
                "test",
                BooleanOperator.EQUALS,
                "tst"
        );
        assertEquals(bc1, bc2);
        assertNotEquals(bc1, bc3);
    }
}
