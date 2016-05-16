package org.tinygroup.pdfindexsource;

import java.io.IOException;

import junit.framework.TestCase;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

/**
 * IText原始接口测试用例
 * 
 * @author yancheng11334
 * 
 */
public class PdfTest extends TestCase {

	public void testPdfContent() throws IOException {
		String fileName = "src/test/resources/TF-120416-0832-3.pdf";
		String content = readPdfText(fileName, null);
		System.out.println(content);
		assertTrue(content.indexOf("Tiny框架提出了一个在资产库的概念，它不是一个新概念")>0);
	}
	
	public void testPdfWithPassword() throws IOException {
		String fileName = "src/test/resources/password.pdf";
		String content = readPdfText(fileName, "tinyabc");
		System.out.println(content);
		assertTrue(content.indexOf("测试")>0);
	}

	public String readPdfText(String fileName, String password)
			throws IOException {
		PdfReader reader = null;
		
		if(password!=null){
			reader = new PdfReader(fileName,password.getBytes("UTF-8"));
		}else{
			reader = new PdfReader(fileName);
		}
		
		try {
			StringBuffer buff = new StringBuffer();
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				buff.append(PdfTextExtractor.getTextFromPage(reader, i)); 
			}
			return buff.toString();
		} finally {
			reader.close();
		}

	}
}
