package org.tinygroup.springmvc.adapter;

import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.*;


/**
 * spring mvc的适配对象
 * @author renhui
 *
 */
public class SpringMVCAdapter {

	/** MultipartResolver used by this servlet */
	private MultipartResolver multipartResolver;
	/** LocaleResolver used by this servlet */
	private LocaleResolver localeResolver;

	/** ThemeResolver used by this servlet */
	private ThemeResolver themeResolver;

	/** HandlerMappings used by this servlet */
	private HandlerMapping handlerMapping;

	/** HandlerAdapters used by this servlet */
	private HandlerAdapter handlerAdapter;

	/** HandlerExceptionResolver used by this servlet */
	private HandlerExceptionResolver handlerExceptionResolver;

	/** RequestToViewNameTranslator used by this servlet */
	private RequestToViewNameTranslator viewNameTranslator;

	/** ViewResolver used by this servlet */
	private ViewResolver viewResolver;
	
	public MultipartResolver getMultipartResolver() {
		return multipartResolver;
	}

	public void setMultipartResolver(MultipartResolver multipartResolver) {
		this.multipartResolver = multipartResolver;
	}

	/**
	 * @return Returns the localeResolver.
	 */
	public LocaleResolver getLocaleResolver() {
		return localeResolver;
	}

	/**
	 * @param localeResolver
	 *            The localeResolver to set.
	 */
	public void setLocaleResolver(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

	/**
	 * @return Returns the themeResolver.
	 */
	public ThemeResolver getThemeResolver() {
		return themeResolver;
	}

	/**
	 * @param themeResolver
	 *            The themeResolver to set.
	 */
	public void setThemeResolver(ThemeResolver themeResolver) {
		this.themeResolver = themeResolver;
	}

	/**
	 * @return Returns the handlerMapping.
	 */
	public HandlerMapping getHandlerMapping() {
		return handlerMapping;
	}

	/**
	 * @param handlerMapping
	 *            The handlerMapping to set.
	 */
	public void setHandlerMapping(HandlerMapping handlerMapping) {
		this.handlerMapping = handlerMapping;
	}

	/**
	 * @return Returns the handlerAdapter.
	 */
	public HandlerAdapter getHandlerAdapter() {
		return handlerAdapter;
	}

	/**
	 * @param handlerAdapter
	 *            The handlerAdapter to set.
	 */
	public void setHandlerAdapter(HandlerAdapter handlerAdapter) {
		this.handlerAdapter = handlerAdapter;
	}

	/**
	 * @return Returns the handlerExceptionResolver.
	 */
	public HandlerExceptionResolver getHandlerExceptionResolver() {
		return handlerExceptionResolver;
	}

	/**
	 * @param handlerExceptionResolver
	 *            The handlerExceptionResolver to set.
	 */
	public void setHandlerExceptionResolver(
			HandlerExceptionResolver handlerExceptionResolver) {
		this.handlerExceptionResolver = handlerExceptionResolver;
	}

	/**
	 * @return Returns the viewNameTranslator.
	 */
	public RequestToViewNameTranslator getViewNameTranslator() {
		return viewNameTranslator;
	}

	/**
	 * @param viewNameTranslator
	 *            The viewNameTranslator to set.
	 */
	public void setViewNameTranslator(
			RequestToViewNameTranslator viewNameTranslator) {
		this.viewNameTranslator = viewNameTranslator;
	}

	/**
	 * @return Returns the viewResolver.
	 */
	public ViewResolver getViewResolver() {
		return viewResolver;
	}

	/**
	 * @param viewResolver
	 *            The viewResolver to set.
	 */
	public void setViewResolver(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}
}
