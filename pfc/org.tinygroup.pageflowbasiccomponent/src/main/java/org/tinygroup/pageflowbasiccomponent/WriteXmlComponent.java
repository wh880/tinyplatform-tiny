package org.tinygroup.pageflowbasiccomponent;

import java.io.IOException;

import org.tinygroup.context.Context;
import org.tinygroup.convert.objectxml.xstream.ObjectToXml;
import org.tinygroup.weblayer.WebContext;

public class WriteXmlComponent extends AbstractWriteComponent {
	private ObjectToXml<Object> objectToXml = new ObjectToXml<Object>();
	
	public void execute(Context context) {
		WebContext webContext=(WebContext)context;
		try {
			webContext.getResponse().getWriter().write(objectToXml.convert(context.get(resultKey)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

}
