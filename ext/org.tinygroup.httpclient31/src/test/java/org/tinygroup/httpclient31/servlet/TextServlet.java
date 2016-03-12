package org.tinygroup.httpclient31.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tinygroup.commons.file.IOUtils;

public class TextServlet extends AbstractMockServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5863574058246844016L;

	protected void dealService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			
//			for (Enumeration<String> names = request.getHeaderNames(); names.hasMoreElements();){
//				String name = names.nextElement();
//				System.out.println(name+" "+request.getHeader(name));
//			}
			String text = IOUtils.readFromInputStream(request.getInputStream(), "ISO-8859-1");
			PrintWriter out = null;
			// 默认处理
			out = response.getWriter();
		    out.write(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
