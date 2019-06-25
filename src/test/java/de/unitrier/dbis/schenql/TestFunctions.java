package de.unitrier.dbis.schenql;

import de.unitrier.dbis.schenql.compiler.SchenQLCompilerException;
import de.unitrier.dbis.schenql.compiler.Schenql;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestFunctions {
    @Test
    public void TestCount() {
        String schenQLQuery = "COUNT (PUBLICATIONS)";
        String expected = "SELECT COUNT(*) as `count` FROM (SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication`) as sub;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestMostCited() {
        String schenQLQuery1 = "MOST CITED (PUBLICATIONS)";
        String schenQLQuery2 = "PERSONS AUTHORED (MOST CITED (PUBLICATIONS))";
        String expected1 = "SELECT DISTINCT `publication`.`title`, `publication`.`year`, COUNT(*) as `count` FROM `publication` JOIN `publication_references` ON `publication_references`.`pub2_id` = `publication`.`dblpKey` GROUP BY `publication`.`title` ORDER BY COUNT(*) DESC LIMIT 5;";
        String expected2 = "SELECT DISTINCT `person`.`primaryName`, `person`.`orcid` FROM `person` JOIN `person_authored_publication` ON `person_authored_publication`.`personKey` = `person`.`dblpKey` JOIN (SELECT DISTINCT `publication`.`dblpKey` FROM `publication` JOIN `publication_references` ON `publication_references`.`pub2_id` = `publication`.`dblpKey` GROUP BY `publication`.`title` ORDER BY COUNT(*) DESC LIMIT 5) as publication_sub ON publication_sub.`dblpKey` = `person_authored_publication`.`publicationKey` LIMIT 100;";
        try {
            assertEquals(expected1, Schenql.compileSchenQL(schenQLQuery1));
            assertEquals(expected2, Schenql.compileSchenQL(schenQLQuery2));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }
}
