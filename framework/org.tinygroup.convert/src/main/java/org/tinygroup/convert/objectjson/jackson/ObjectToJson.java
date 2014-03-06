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
package org.tinygroup.convert.objectjson.jackson;

import java.io.ByteArrayOutputStream;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.tinygroup.convert.Converter;

public class ObjectToJson<T> implements Converter<T, String> {
	ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

	public ObjectToJson() {
		mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
	}

	public ObjectToJson(Inclusion inclusion) {
		mapper.setSerializationInclusion(inclusion);
	}

	public String convert(T object) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			mapper.writeValue(outputStream, object);
			return outputStream.toString("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
