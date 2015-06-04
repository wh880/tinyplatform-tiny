package org.tinygroup.springmvc.exceptionresolver;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.tinygroup.commons.tools.CollectionUtil;

/**
 * 异常解析的复合类
 * @author renhui
 *
 */
public class HandlerExceptionResolverComposite implements
		HandlerExceptionResolver,InitializingBean {
	
    private List<HandlerExceptionResolver> composite;
    

	public void setComposite(List<HandlerExceptionResolver> composite) {
		this.composite = composite;
	}

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
         for (HandlerExceptionResolver resolver : composite) {
		      ModelAndView modelAndView=resolver.resolveException(request, response, handler, ex);
		      if(modelAndView!=null){
		    	  return modelAndView;
		      }
		}
        return null;
	}


	public void afterPropertiesSet() throws Exception {
		if(CollectionUtil.isEmpty(composite)){
			composite=new ArrayList<HandlerExceptionResolver>();
			composite.add(new AnnotationMethodHandlerExceptionResolver());
			composite.add(new ResponseStatusExceptionResolver());
			composite.add(new DefaultHandlerExceptionResolver());
		}
	}

}
