package testcase;

import java.io.IOException;
import java.net.URL;

import org.tinygroup.databasechange.TableSqlChangeUtil;

import junit.framework.TestCase;

public class SqlChangeTest extends TestCase {

	
	public void testSqlChange() throws IOException{
		URL url=getClass().getResource("/");
		if(url==null){
			url=getClass().getClassLoader().getResource("");
		}
		String filePath=url.getFile()+"test.sql";
		TableSqlChangeUtil.main(new String[]{filePath});
		
	}
	
	
}
