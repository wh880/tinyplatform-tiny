package org.tinygroup.pageflowbasiccomponent;

import org.tinygroup.context.Context;
import org.tinygroup.weblayer.WebContext;

public class WriteTextComponent extends AbstractWriteComponent {

	public void execute(Context context) {
		WebContext webContext=(WebContext)context;
		try {
			webContext.getResponse().getWriter().write(String.valueOf(context.get(resultKey)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
