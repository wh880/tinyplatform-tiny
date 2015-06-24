package org.tinygroup.springmvc.support;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * WebBindingInitializer的复合对象
 * @author renhui
 *
 */
public class WebBindingInitializerComposite extends ApplicationObjectSupport implements WebBindingInitializer {

	private List<WebBindingInitializer> webBindingInitializerComposite=new ArrayList<WebBindingInitializer>();

	public void setWebBindingInitializerComposite(
			List<WebBindingInitializer> webBindingInitializerComposite) {
		this.webBindingInitializerComposite = webBindingInitializerComposite;
	}

	public void initBinder(WebDataBinder binder, WebRequest request) {
		  for (WebBindingInitializer wbi : webBindingInitializerComposite) {
              wbi.initBinder(binder, request);
          }
	}

}
