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
package org.tinygroup.convert.objectxml.jaxb;

import org.tinygroup.commons.io.ByteArrayInputStream;
import org.tinygroup.convert.Converter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class XmlToObject<T> implements Converter<String, T> {
    private String encode = "UTF-8";
    private JAXBContext context;
    private Unmarshaller unmarshaller;

    public XmlToObject(String className, String encode) {
        this(className);
        this.encode = encode;
    }

    public XmlToObject(String className) {
        try {
            context = JAXBContext.newInstance(className);
            unmarshaller = context.createUnmarshaller();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public XmlToObject(Class<T> clazz) {
        try {
            context = JAXBContext.newInstance(clazz);
            unmarshaller = context.createUnmarshaller();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @SuppressWarnings("unchecked")
    public T convert(String inputData) {

        try {
            T object = (T) unmarshaller.unmarshal(new ByteArrayInputStream(inputData.getBytes(encode)));
            return object;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
