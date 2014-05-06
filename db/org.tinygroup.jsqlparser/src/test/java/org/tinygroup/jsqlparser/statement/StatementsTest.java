package org.tinygroup.jsqlparser.statement;

import org.tinygroup.jsqlparser.JSQLParserException;
import org.tinygroup.jsqlparser.parser.CCJSqlParserUtil;
import org.tinygroup.jsqlparser.statement.select.Select;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author toben
 */
public class StatementsTest {
    
    public StatementsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class Statements.
     */
    @Test
    public void testStatements() throws JSQLParserException {
        String sqls = "select * from mytable; select * from mytable2;";
        Statements parseStatements = CCJSqlParserUtil.parseStatements(sqls);
        
        assertEquals("SELECT * FROM mytable;\nSELECT * FROM mytable2;\n", parseStatements.toString());
        
        assertTrue(parseStatements.getStatements().get(0) instanceof Select);
        assertTrue(parseStatements.getStatements().get(1) instanceof Select);
    }
    
}
