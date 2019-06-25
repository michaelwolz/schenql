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
        String expected = "SELECT DISTINCT `person`.`primaryName`, `person`.`orcid` FROM `person` JOIN `person_authored_publication` ON `person_authored_publication`.`personKey` = `person`.`dblpKey` JOIN (SELECT DISTINCT `publication`.`dblpKey` FROM `publication` WHERE `publication`.`title` = 'DBLP - Some Lessons Learned.') as publication_sub ON publication_sub.`dblpKey` = `person_authored_publication`.`publicationKey` LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestEdited() {
        String schenQLQuery = "PERSONS EDITED \"DBLP - Some Lessons Learned.\"";
        String expected = "SELECT DISTINCT `person`.`primaryName`, `person`.`orcid` FROM `person` JOIN `person_edited_publication` ON `person_edited_publication`.`personKey` = `person`.`dblpKey` JOIN (SELECT DISTINCT `publication`.`dblpKey` FROM `publication` WHERE `publication`.`title` = 'DBLP - Some Lessons Learned.') as publication_sub ON publication_sub.`dblpKey` = `person_edited_publication`.`publicationKey` LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestWorksFor() {
        String schenQLQuery = "PERSONS WORKING FOR \"University of Trier\"";
        String expected = "SELECT DISTINCT `person`.`primaryName`, `person`.`orcid` FROM `person` JOIN `person_works_for_institution` ON `person_works_for_institution`.`personKey` = `person`.`dblpKey` JOIN `institution` ON `institution`.`key` = `person_works_for_institution`.`institutionKey` WHERE `institution`.`key` IN (SELECT DISTINCT `institution`.`key` FROM `institution` JOIN `institution_name` ON `institution_name`.`institutionKey` = `institution`.`key` WHERE `institution_name`.`name` = 'University of Trier') LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestPublishedWith() {
        String schenQLQuery = "PERSONS PUBLISHED WITH \"University of Trier\"";
        String expected = "SELECT DISTINCT `person`.`primaryName`, `person`.`orcid` FROM `person` JOIN `person_works_for_institution` ON `person_works_for_institution`.`personKey` = `person`.`dblpKey` JOIN `institution` ON `institution`.`key` = `person_works_for_institution`.`institutionKey` WHERE `institution`.`key` IN (SELECT DISTINCT `institution`.`key` FROM `institution` JOIN `institution_name` ON `institution_name`.`institutionKey` = `institution`.`key` WHERE `institution_name`.`name` = 'University of Trier') LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestPublishedIn() {
        String schenQLQuery = "PERSONS PUBLISHED IN \"conf/vldb\"";
        String expected = "SELECT DISTINCT `person`.`primaryName`, `person`.`orcid` FROM `person` JOIN `person_authored_publication` ON `person_authored_publication`.`personKey` = `person`.`dblpKey` JOIN `publication` ON `publication`.`dblpKey` = `person_authored_publication`.`publicationKey` WHERE (`publication`.`journal_dblpKey` = 'conf/vldb' OR `publication`.`conference_dblpKey` = 'conf/vldb') LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestCitedBy() {
        String schenQLQuery = "PERSONS CITED BY \"SchenQL\"";
        String expected = "SELECT DISTINCT `person`.`primaryName`, `person`.`orcid` FROM `person` JOIN `person_authored_publication` ON `person_authored_publication`.`personKey` = `person`.`dblpKey` JOIN `publication_references` ON `publication_references`.`pub_id` = `person_authored_publication`.`publicationKey` WHERE `publication_references`.`pub2_id` IN (SELECT DISTINCT `publication`.`dblpKey` FROM `publication` WHERE `publication`.`title` = 'SchenQL') LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestReferences() {
        String schenQLQuery = "PERSONS REFERENCES \"SchenQL\"";
        String expected = "SELECT DISTINCT `person`.`primaryName`, `person`.`orcid` FROM `person` JOIN `person_authored_publication` ON `person_authored_publication`.`personKey` = `person`.`dblpKey` JOIN `publication_references` ON `publication_references`.`pub_id` = `person_authored_publication`.`publicationKey` WHERE `publication_references`.`pub_id` IN (SELECT DISTINCT `publication`.`dblpKey` FROM `publication` WHERE `publication`.`title` = 'SchenQL') LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestOrcid() {
        String schenQLQuery = "PERSONS ORCID 0000-0002-9313-7131";
        String expected = "SELECT DISTINCT `person`.`primaryName`, `person`.`orcid` FROM `person` WHERE `person`.`orcid` = '0000-0002-9313-7131' LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }
}
