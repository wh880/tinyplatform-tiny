package org.tinygroup.aopcache.util;

import org.springframework.core.ParameterNameDiscoverer;
import org.tinygroup.aopcache.base.TemplateRender;

/**
 * 
 * @author renhui
 *
 */
public class TemplateUtil {
	
	private static TemplateRender templateRender;

	public static TemplateRender createTemplateRender(ParameterNameDiscoverer parameterNameDiscoverer){
		templateRender=TemplateRender.newInstance(parameterNameDiscoverer);
		return templateRender;
	}
	
	public static TemplateRender getTemplateRender(){
		return templateRender;
	}
	
	
}
