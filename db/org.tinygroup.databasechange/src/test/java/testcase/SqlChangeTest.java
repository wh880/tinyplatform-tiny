/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package testcase;

import junit.framework.TestCase;
import org.tinygroup.databasechange.TableSqlChangeUtil;

import java.io.IOException;
import java.net.URL;

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
