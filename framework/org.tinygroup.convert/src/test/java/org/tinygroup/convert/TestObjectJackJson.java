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

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.tinygroup.convert.objectjson.jackson.JsonToObject;
import org.tinygroup.convert.objectjson.jackson.ObjectToJson;


public class TestObjectJackJson extends AbstractConvertTestCase {

	
	protected void setUp() throws Exception {
		super.setUp();
		
	}

	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testJson2Object(){
		String json = "{\"id\":1,\"name\":\"haha\",\"email\":\"email\",\"address\":\"address\",\"birthday\":{\"birthday\":\"2010-11-22\"}}";
		JsonToObject<Student>  jsonToObject=new JsonToObject<Student>(Student.class);
		Student student= jsonToObject.convert(json);
		assertEquals("email", student.getEmail());
		assertEquals(1, student.getId());
		assertEquals("haha", student.getName());
		assertEquals("2010-11-22", student.getBirthday().getBirthday());
	}
	
	public void testObject2Json(){
		Student student = createStudent();
	    ObjectToJson<Student> objectToJson=new ObjectToJson<Student>();
		System.out.println(objectToJson.convert(student));
		
	}
	
	public void testMap2Json(){
		Map<String, Object> maps=new HashMap<String, Object>();
		Student student = createStudent();
		maps.put("student", student);
		maps.put("id",1234);
		ObjectToJson objectToXml = new ObjectToJson(JsonSerialize.Inclusion.NON_NULL);
		System.out.println(objectToXml.convert(maps));
		
	}
	
	public void testJson2Map(){
		
		String json="{\"student\":{\"id\":1,\"name\":\"haha\",\"email\":\"email\",\"address\":\"address\",\"birthday\":{\"birthday\":\"2010-11-22\"}},\"id\":1234}";
		JsonToObject jsonToObject = new JsonToObject(HashMap.class);
		Map<String, Object> maps=(Map<String, Object>)jsonToObject.convert(json);
		assertFalse(maps.get("student") instanceof Student);
	}
	
}
