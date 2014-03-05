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
package org.tinygroup.htmlparser.formatter;

import java.io.FileInputStream;
import java.nio.charset.Charset;

import junit.framework.TestCase;

import org.tinygroup.commons.file.IOUtils;
import org.tinygroup.htmlparser.HtmlDocument;
import org.tinygroup.htmlparser.parser.HtmlStringParser;

public class HtmlFormatterTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFormatDocumentSelfHtmlDocument() {
		HtmlDocument doc;
		try {
			doc = new HtmlStringParser()
					.parse("<html 中='文'><HEAD><title>aaa</title></head></html>");
			doc.write(System.out);
			System.out.println("\n================================\n");
			HtmlFormater f = new HtmlFormater();
			System.out.println(f.format(doc));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public void testDOCTYPE() {
        HtmlDocument doc;
        try {
            doc = new HtmlStringParser()
                    .parse("<html></html><a></a>");
            doc.write(System.out);
            System.out.println("\n================================");
            HtmlFormater f = new HtmlFormater();
            System.out.println(f.format(doc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void testFormatFile() {
        HtmlDocument doc;
        try {
            doc = new HtmlStringParser()
                    .parse(IOUtils.readFromInputStream(new FileInputStream("c:/a.html"),"GBK"));
            HtmlFormater f = new HtmlFormater();
            System.out.println(f.format(doc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void testFormatDocumentSelfHtmlDocumentOutputStream() {
		try {
			HtmlDocument doc = new HtmlStringParser()
					.parse("<html 中='文'><head><title>aaa</title><中>中信</中></head></html>");
			doc.write(System.out);
			System.out.println("\n================================\n");
			HtmlFormater f = new HtmlFormater();
			f.setEncode(Charset.defaultCharset().displayName());
			f.format(doc, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
