package org.tinygroup.springmvc.exceptionresolver;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.tinygroup.commons.tools.CollectionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 异常解析的复合类
 * 
 * @author renhui
 * 
 */
public class HandlerExceptionResolverComposite extends ApplicationObjectSupport
		implements HandlerExceptionResolver, InitializingBean {

	private List<HandlerExceptionResolver> composite;

	public void setComposite(List<HandlerExceptionResolver> composite) {
		this.composite = composite;
	}

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		for (HandlerExceptionResolver resolver : composite) {
			ModelAndView modelAndView = resolver.resolveException(request,
					response, handler, ex);
			if (modelAndView != null) {
				return modelAndView;
			}
		}
		return null;
	}

	public void afterPropertiesSet() throws Exception {
		if (CollectionUtil.isEmpty(composite)) {
			Map<String, HandlerExceptionResolver> resolverMap = BeanFactoryUtils
					.beansOfTypeIncludingAncestors(
							this.getApplicationContext(),
							HandlerExceptionResolver.class);
			composite = new ArrayList<HandlerExceptionResolver>();
			composite.addAll(resolverMap.values());
		}
	}

}
