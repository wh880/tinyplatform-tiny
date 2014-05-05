package org.tinygroup.jsqlparser.util;

import org.junit.*;
import org.tinygroup.jsqlparser.JSQLParserException;
import org.tinygroup.jsqlparser.expression.LongValue;
import org.tinygroup.jsqlparser.expression.operators.arithmetic.Addition;
import org.tinygroup.jsqlparser.expression.operators.relational.EqualsTo;
import org.tinygroup.jsqlparser.parser.CCJSqlParserUtil;
import org.tinygroup.jsqlparser.schema.Column;
import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.select.Join;
import org.tinygroup.jsqlparser.statement.select.PlainSelect;
import org.tinygroup.jsqlparser.statement.select.Select;
import org.tinygroup.jsqlparser.statement.select.SelectExpressionItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author toben
 */
public class SelectUtilsTest {

	public SelectUtilsTest() {
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
	 * Test of addColumn method, of class SelectUtils.
	 */
	@Test
	public void testAddExpr() throws JSQLParserException {
		Select select = (Select) CCJSqlParserUtil.parse("select a from mytable");
		SelectUtils.addExpression(select, new Column("b"));
		assertEquals("SELECT a, b FROM mytable", select.toString());
		
		Addition add = new Addition();
		add.setLeftExpression(new LongValue(5));
		add.setRightExpression(new LongValue(6));
		SelectUtils.addExpression(select, add);
		
		assertEquals("SELECT a, b, 5 + 6 FROM mytable", select.toString());
	}
	
	@Test
	public void testAddJoin() throws JSQLParserException {
		Select select = (Select)CCJSqlParserUtil.parse("select a from mytable");
		final EqualsTo equalsTo = new EqualsTo();
		equalsTo.setLeftExpression(new Column("a"));
		equalsTo.setRightExpression(new Column("b"));
		Join addJoin = SelectUtils.addJoin(select, new Table("mytable2"), equalsTo);
		addJoin.setLeft(true);
		assertEquals("SELECT a FROM mytable LEFT JOIN mytable2 ON a = b", select.toString());
	}
	
	@Test
	public void testBuildSelectFromTableAndExpressions() {
		Select select = SelectUtils.buildSelectFromTableAndExpressions(new Table("mytable"), new Column("a"), new Column("b"));
		assertEquals("SELECT a, b FROM mytable", select.toString());
	}
	
	@Test
	public void testBuildSelectFromTable() {
		Select select = SelectUtils.buildSelectFromTable(new Table("mytable"));
		assertEquals("SELECT * FROM mytable", select.toString());
	}
	
	@Test
	public void testBuildSelectFromTableAndParsedExpression() throws JSQLParserException {
		Select select = SelectUtils.buildSelectFromTableAndExpressions(new Table("mytable"), "a+b", "test");
		assertEquals("SELECT a + b, test FROM mytable", select.toString());
		
		assertTrue(((SelectExpressionItem)((PlainSelect)select.getSelectBody()).getSelectItems().get(0)).getExpression() instanceof Addition);
	}
	
}
