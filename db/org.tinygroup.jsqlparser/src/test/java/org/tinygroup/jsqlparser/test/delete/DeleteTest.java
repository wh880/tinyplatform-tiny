package org.tinygroup.jsqlparser.test.delete;

import java.io.StringReader;
import static junit.framework.Assert.assertEquals;

import junit.framework.TestCase;
import org.tinygroup.jsqlparser.JSQLParserException;
import org.tinygroup.jsqlparser.parser.CCJSqlParserManager;
import org.tinygroup.jsqlparser.statement.delete.Delete;
import static org.tinygroup.jsqlparser.test.TestUtils.*;

public class DeleteTest extends TestCase {

	CCJSqlParserManager parserManager = new CCJSqlParserManager();

	public DeleteTest(String arg0) {
		super(arg0);
	}

	public void testDelete() throws JSQLParserException {
		String statement = "DELETE FROM mytable WHERE mytable.col = 9";

		Delete delete = (Delete) parserManager.parse(new StringReader(statement));
		assertEquals("mytable", delete.getTable().getName());
		assertEquals(statement, "" + delete);
	}
	
	public void testDeleteWhereProblem1() throws JSQLParserException {
		String stmt = "DELETE FROM tablename WHERE a = 1 AND b = 1";
		assertSqlCanBeParsedAndDeparsed(stmt);
	}
}
