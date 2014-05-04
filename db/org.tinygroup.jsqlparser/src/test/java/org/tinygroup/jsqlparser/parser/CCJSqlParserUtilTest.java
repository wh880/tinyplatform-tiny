package org.tinygroup.jsqlparser.parser;

import org.tinygroup.jsqlparser.expression.Expression;
import org.tinygroup.jsqlparser.expression.LongValue;
import org.tinygroup.jsqlparser.expression.Parenthesis;
import org.tinygroup.jsqlparser.expression.operators.arithmetic.Addition;
import org.tinygroup.jsqlparser.expression.operators.arithmetic.Multiplication;
import org.tinygroup.jsqlparser.schema.Column;
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
public class CCJSqlParserUtilTest {
	
	public CCJSqlParserUtilTest() {
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
	 * Test of parseExpression method, of class CCJSqlParserUtil.
	 */
	@Test
	public void testParseExpression() throws Exception {
		Expression result = CCJSqlParserUtil.parseExpression("a+b");
		assertEquals("a + b", result.toString());
		assertTrue(result instanceof Addition);
		Addition add = (Addition)result;
		assertTrue(add.getLeftExpression() instanceof Column);
		assertTrue(add.getRightExpression() instanceof Column);
	}
	
	@Test
	public void testParseExpression2() throws Exception {
		Expression result = CCJSqlParserUtil.parseExpression("2*(a+6.0)");
		assertEquals("2 * (a + 6.0)", result.toString());
		assertTrue(result instanceof Multiplication);
		Multiplication mult = (Multiplication)result;
		assertTrue(mult.getLeftExpression() instanceof LongValue);
		assertTrue(mult.getRightExpression() instanceof Parenthesis);
	}
    
    @Test
	public void testParseCondExpression() throws Exception {
		Expression result = CCJSqlParserUtil.parseCondExpression("a+b>5 and c<3");
		assertEquals("a + b > 5 AND c < 3", result.toString());
	}
}
