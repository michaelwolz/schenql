package de.unitrier.dbis.schenql;

import de.unitrier.dbis.schenql.compiler.SchenQLCompilerException;
import de.unitrier.dbis.schenql.compiler.Schenql;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPublication {
    @Test
    public void TestBase() {
        String schenQLQuery = "PUBLICATIONS";
        String expected = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestWrittenBy() {
        String schenQLQuery = "PUBLICATIONS WRITTEN BY \"Jon Doe\"";
        String expected = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` JOIN `person_authored_publication` ON `person_authored_publication`.`publicationKey` = `publication`.`dblpKey` JOIN `person` ON `person`.`dblpKey` = `person_authored_publication`.`personKey` WHERE `person`.`dblpKey` IN (SELECT DISTINCT `person`.`dblpKey` FROM `person` JOIN `person_names` ON `person_names`.`personKey` = `person`.`dblpKey` WHERE `person_names`.`name` = 'Jon Doe') LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestEditedBy() {
        String schenQLQuery = "PUBLICATIONS EDITED BY \"Jon Doe\"";
        String expected = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` JOIN `person_edited_publication` ON `person_edited_publication`.`publicationKey` = `publication`.`dblpKey` JOIN `person` ON `person`.`dblpKey` = `person_edited_publication`.`personKey` WHERE `person`.`dblpKey` IN (SELECT DISTINCT `person`.`dblpKey` FROM `person` JOIN `person_names` ON `person_names`.`personKey` = `person`.`dblpKey` WHERE `person_names`.`name` = 'Jon Doe') LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestPublishedBy() {
        String schenQLQuery = "PUBLICATIONS PUBLISHED BY \"University of Trier\"";
        String expected = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` JOIN `person_authored_publication` ON `person_authored_publication`.`publicationKey` = `publication`.`dblpKey` JOIN `person` ON `person`.`dblpKey` = `person_authored_publication`.`personKey` JOIN `person_works_for_institution` ON `person_works_for_institution`.`personKey` = `person`.`dblpKey` WHERE `person_works_for_institution`.`institutionKey` IN (SELECT DISTINCT `institution`.`key` FROM `institution` JOIN `institution_name` ON `institution_name`.`institutionKey` = `institution`.`key` WHERE `institution_name`.`name` = 'University of Trier') LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestAbout() {
        String schenQLQuery1 = "PUBLICATIONS ABOUT \"Domain Specific Query Languages\"";
        String schenQLQuery2 = "PUBLICATIONS ABOUT KEYWORD \"xml\"";
        String schenQLQuery3 = "PUBLICATIONS ABOUT KEYWORD [\"xml\", \"databases\"]";
        String expected1 = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` WHERE MATCH (`publication`.`abstract`) AGAINST ('Domain Specific Query Languages') LIMIT 100;";
        String expected2 = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` JOIN `publication_has_keyword` ON `publication_has_keyword`.`dblpKey` = `publication`.`dblpKey` WHERE `publication_has_keyword`.`keyword` IN (SELECT DISTINCT `keyword`.`keyword` FROM `keyword` WHERE `keyword`.`keyword` IN ('xml')) LIMIT 100;";
        String expected3 = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` JOIN `publication_has_keyword` ON `publication_has_keyword`.`dblpKey` = `publication`.`dblpKey` WHERE `publication_has_keyword`.`keyword` IN (SELECT DISTINCT `keyword`.`keyword` FROM `keyword` WHERE `keyword`.`keyword` IN ('xml','databases')) LIMIT 100;";
        try {
            assertEquals(expected1, Schenql.compileSchenQL(schenQLQuery1));
            assertEquals(expected2, Schenql.compileSchenQL(schenQLQuery2));
            assertEquals(expected3, Schenql.compileSchenQL(schenQLQuery3));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestBefore() {
        String schenQLQuery = "PUBLICATIONS BEFORE 2019";
        String expected = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` WHERE `publication`.`year` < 2019 LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestAfter() {
        String schenQLQuery = "PUBLICATIONS AFTER 2019";
        String expected = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` WHERE `publication`.`year` > 2019 LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestInYear() {
        String schenQLQuery = "PUBLICATIONS IN YEAR 2019";
        String expected = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` WHERE `publication`.`year` = 2019 LIMIT 100;";
        try {
            assertEquals(expected, Schenql.compileSchenQL(schenQLQuery));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestAppearedIn() {
        String schenQLQuery1 = "PUBLICATIONS APPEARED IN (CONFERENCES ACRONYM \"VLDB\")";
        String schenQLQuery2 = "PUBLICATIONS APPEARED IN (JOURNALS ACRONYM \"VLDB\")";
        String schenQLQuery3 = "PUBLICATIONS APPEARED IN \"conf/vldb\"";
        String schenQLQuery4 = "PUBLICATIONS APPEARED IN \"VLDB\"";
        String expected1 = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` WHERE `publication`.`conference_dblpKey` IN (SELECT DISTINCT `conference`.`dblpKey` FROM `conference` WHERE `conference`.`acronym` = 'VLDB') LIMIT 100;";
        String expected2 = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` WHERE `publication`.`journal_dblpKey` IN (SELECT DISTINCT `journal`.`dblpKey` FROM `journal` WHERE `journal`.`acronym` = 'VLDB') LIMIT 100;";
        String expected3 = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` WHERE (`publication`.`journal_dblpKey` = 'conf/vldb' OR `publication`.`conference_dblpKey` = 'conf/vldb') LIMIT 100;";
        String expected4 = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` WHERE (`publication`.`journal_dblpKey` IN (SELECT `journal`.`dblpKey` FROM `journal` WHERE `journal`.`acronym` = 'VLDB') OR `publication`.`conference_dblpKey` IN (SELECT `conference`.`dblpKey` FROM `conference` WHERE `conference`.`acronym` = 'VLDB')) LIMIT 100;";
        try {
            assertEquals(expected1, Schenql.compileSchenQL(schenQLQuery1));
            assertEquals(expected2, Schenql.compileSchenQL(schenQLQuery2));
            assertEquals(expected3, Schenql.compileSchenQL(schenQLQuery3));
            assertEquals(expected4, Schenql.compileSchenQL(schenQLQuery4));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestCitedBy() {
        String schenQLQuery1 = "PUBLICATIONS CITED BY \"SchenQL\"";
        String schenQLQuery2 = "PUBLICATIONS CITED BY ~\"SchenQL\"";
        String expected1 = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` JOIN `publication_references` ON `publication_references`.`pub2_id` = `publication`.`dblpKey` WHERE `publication_references`.`pub_id` IN (SELECT DISTINCT `publication`.`dblpKey` FROM `publication` WHERE `publication`.`title` = 'SchenQL') LIMIT 100;";
        String expected2 = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` JOIN `publication_references` ON `publication_references`.`pub2_id` = `publication`.`dblpKey` WHERE `publication_references`.`pub_id` IN (SELECT DISTINCT `publication`.`dblpKey` FROM `publication` WHERE MATCH (`publication`.`title`) AGAINST ('SchenQL')) LIMIT 100;";
        try {
            assertEquals(expected1, Schenql.compileSchenQL(schenQLQuery1));
            assertEquals(expected2, Schenql.compileSchenQL(schenQLQuery2));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestReferences() {
        String schenQLQuery1 = "PUBLICATIONS REFERENCING \"SchenQL\"";
        String schenQLQuery2 = "PUBLICATIONS REFERENCING ~\"SchenQL\"";
        String expected1 = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` JOIN `publication_references` ON `publication_references`.`pub_id` = `publication`.`dblpKey` WHERE `publication_references`.`pub2_id` IN (SELECT DISTINCT `publication`.`dblpKey` FROM `publication` WHERE `publication`.`title` = 'SchenQL') LIMIT 100;";
        String expected2 = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` JOIN `publication_references` ON `publication_references`.`pub_id` = `publication`.`dblpKey` WHERE `publication_references`.`pub2_id` IN (SELECT DISTINCT `publication`.`dblpKey` FROM `publication` WHERE MATCH (`publication`.`title`) AGAINST ('SchenQL')) LIMIT 100;";
        try {
            assertEquals(expected1, Schenql.compileSchenQL(schenQLQuery1));
            assertEquals(expected2, Schenql.compileSchenQL(schenQLQuery2));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }

    @Test
    public void TestTitled() {
        String schenQLQuery1 = "PUBLICATIONS TITLED \"SchenQL\"";
        String schenQLQuery2 = "PUBLICATIONS TITLED ~\"SchenQL\"";
        String expected1 = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` WHERE `publication`.`title` = 'SchenQL' LIMIT 100;";
        String expected2 = "SELECT DISTINCT `publication`.`title`, `publication`.`year` FROM `publication` WHERE MATCH (`publication`.`title`) AGAINST ('SchenQL') LIMIT 100;";
        try {
            assertEquals(expected1, Schenql.compileSchenQL(schenQLQuery1));
            assertEquals(expected2, Schenql.compileSchenQL(schenQLQuery2));
        } catch (SchenQLCompilerException e) {
            Assert.fail("Exception " + e);
        }
    }
}
