package org.tinygroup.pageflowbasiccomponent;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.tinygroup.context.Context;
import org.tinygroup.convert.objectjson.jackson.ObjectToJson;
import org.tinygroup.weblayer.WebContext;

public class WriteJsonComponent extends AbstractWriteComponent  {
	
	private ObjectToJson<Object> objectToJson = new ObjectToJson<Object>(
			JsonSerialize.Inclusion.NON_NULL);

	public void execute(Context context) {
		WebContext webContext=(WebContext)context;
		try {
			webContext.getResponse().getWriter().write(objectToJson.convert(context.get(resultKey)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
