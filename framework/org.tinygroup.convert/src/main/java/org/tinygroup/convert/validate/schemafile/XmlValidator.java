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
package org.tinygroup.convert.validate.schemafile;

import org.tinygroup.commons.io.ByteArrayInputStream;
import org.tinygroup.convert.ObjectValidator;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class XmlValidator implements ObjectValidator<String> {
    URL schemaFile;
    Schema schema;
    String encode = "UTF-8";

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public XmlValidator(String xmlFile) {
        this(new File(xmlFile));
    }

    public XmlValidator(Schema schema) {
        this.schema = schema;
    }

    public XmlValidator(File xmlSchemaFile) {
        try {
            schemaFile = xmlSchemaFile.toURL();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            this.schema = schemaFactory.newSchema(schemaFile);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public XmlValidator(URL xmlSchema) {
        schemaFile = xmlSchema;
    }

    public boolean isValidate(String xmlString) {

        try {
            Source xmlFile = new StreamSource(new ByteArrayInputStream(xmlString.getBytes(encode)));
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            return true;
        } catch (Exception e) {
            System.out.println("Reason: " + e.getLocalizedMessage());
        }
        return false;
    }

}
