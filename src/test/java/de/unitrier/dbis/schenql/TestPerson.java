package de.unitrier.dbis.schenql;

import de.unitrier.dbis.schenql.compiler.SchenQLCompilerException;
import de.unitrier.dbis.schenql.compiler.Schenql;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


public class TestPerson {
    @Test
    public void TestBase() {
        String schenQLQuery = "PERSONS";
        String expected = "SELECT DISTINCT `person`.`primaryName`, `person`.`orcid` FROM `person` LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestNamed() {
        String schenQLQuery = "PERSONS NAMED \"Ralf Schenkel\"";
        String expected = "SELECT DISTINCT `person`.`primaryName`, `person`.`orcid` FROM `person` JOIN `person_names` ON `person_names`.`personKey` = `person`.`dblpKey` WHERE `person_names`.`name` = 'Ralf Schenkel' LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestAuthored() {
        String schenQLQuery = "PERSONS AUTHORED \"DBLP - Some Lessons Learned.\"";
        String expected = "SELECT DISTINCT `person`.`primaryName`, `person`.`orcid` FROM `person` JOIN `person_names` ON `person_names`.`personKey` = `person`.`dblpKey` WHERE `person_names`.`name` = 'Ralf Schenkel' LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }
}
