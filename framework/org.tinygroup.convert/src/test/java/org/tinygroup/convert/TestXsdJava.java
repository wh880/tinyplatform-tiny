/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.convert;

import junit.framework.TestCase;
import org.tinygroup.convert.xsdjava.ClassToSchema;
import org.tinygroup.convert.xsdjava.SchemaToClass;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class TestXsdJava extends TestCase {

	public void testClass2Schema() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(Classes.class);
		list.add(Student.class);
		list.add(Birthday.class);

		File src = new File("test");
		ClassToSchema classToSchema = new ClassToSchema(src);
		List<File> files = classToSchema.convert(list);
		assertTrue(files.size() > 0);
		for (File file : files) {
			System.out.println(file.getAbsolutePath());
		}
		src.delete();
	}

	public void testSchema2Class() {

		SchemaToClass schemaToClass = new SchemaToClass(
				"test",
				"src/test/resources/xjb",
				"org.tinygroup.convert");
		List<String> xsdFiles = new ArrayList<String>();
		try {
			File src = new File(getClass().getResource("/xsd").toURI());
			File[] subFiles = src.listFiles();
			if (subFiles != null) {
				for (File subFile : subFiles) {
					xsdFiles.add(subFile.getAbsolutePath());
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		schemaToClass.convert(xsdFiles);
		File src = new File("test");
		src.delete();
	}

	public void testSchema2ClassMuti() {

		try {
			File src = new File(getClass().getResource("/xsd").toURI());
			File[] subFiles = src.listFiles();
			if (subFiles != null) {
				for (File subFile : subFiles) {
					List<String> xsdFiles = new ArrayList<String>();
					SchemaToClass schemaToClass = new SchemaToClass(
							"test",
							"src/test/resources/xjb",
							"com.hundsun."
									+ subFile.getName().replaceAll("[.]", "_"));
					xsdFiles.add(subFile.getAbsolutePath());
					schemaToClass.convert(xsdFiles);
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		File src = new File("test");
		System.out.println(src.getAbsolutePath());
		src.deleteOnExit();
	}
}
