package org.tinygroup.jsqlparser.test.alter;


import junit.framework.TestCase;
import org.tinygroup.jsqlparser.JSQLParserException;
import org.tinygroup.jsqlparser.parser.CCJSqlParserUtil;
import org.tinygroup.jsqlparser.statement.Statement;
import org.tinygroup.jsqlparser.statement.alter.Alter;

public class AlterTest extends TestCase {

	public AlterTest(String arg0) {
		super(arg0);
	}

	public void testAlterTableAddColumn() throws JSQLParserException {
		Statement stmt = CCJSqlParserUtil.parse("ALTER TABLE mytable ADD COLUMN mycolumn varchar (255)");
		assertTrue(stmt instanceof Alter);
		Alter alter = (Alter)stmt;
		assertEquals("mytable",alter.getTable().getFullyQualifiedName());
		assertEquals("mycolumn", alter.getColumnName());
		assertEquals("varchar (255)", alter.getDataType().toString());
	}
}
