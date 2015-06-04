package org.tinygroup.springmvc.tinyprocessor;

import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SourceFilteringListener;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.ui.context.ThemeSource;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.support.ServletRequestHandledEvent;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.util.NestedServletException;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;
import org.tinygroup.commons.i18n.LocaleUtil;
import org.tinygroup.commons.order.Ordered;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.springmvc.adapter.SpringMVCAdapter;
import org.tinygroup.springmvc.extension.ExtensionMappingInstance;
import org.tinygroup.springmvc.extension.ExtensionMappingInstanceResolver;
import org.tinygroup.springmvc.extension.RequestInstanceHolder;
import org.tinygroup.weblayer.AbstractTinyProcessor;
import org.tinygroup.weblayer.WebContext;
import org.tinygroup.weblayer.listener.ServletContextHolder;

/**
 * tiny框架方式的springmvc
 * 
 * @author renhui
 * 
 */
public class SpringMvcTinyProcessor extends AbstractTinyProcessor implements
		ApplicationContextAware {

	private static final String CONTEXT_ATTRIBUTE_NAME = "contextAttribute";

	/** Perform cleanup of request attributes after include request? */
	private boolean cleanupAfterInclude = true;

	/**
	 * Expose LocaleContext and RequestAttributes as inheritable for child
	 * threads?
	 */
	private boolean threadContextInheritable = false;

	/**
	 * Should we publish a ServletRequestHandledEvent at the end of each
	 * request?
	 */
	private boolean publishEvents = true;

	private ApplicationContext parent;

	/** WebApplicationContext for this servlet */
	private WebApplicationContext applicationContext;

	private ExtensionMappingInstanceResolver extensionMappingInstanceResolver;

	private SpringMVCAdapter springMVCAdapter;

	private static final UrlPathHelper urlPathHelper = new UrlPathHelper();

	private String contextAttribute;

	/** Explicit context config location */
	private String contextConfigLocation;

	private boolean refreshEventReceived;

	private boolean publishContext = true;

	/**
	 * Request attribute to hold the current web application context. Otherwise
	 * only the global web app context is obtainable by tags etc.
	 * 
	 * @see org.springframework.web.servlet.support.RequestContextUtils#getWebApplicationContext
	 */
	public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = SpringMvcTinyProcessor.class
			.getName() + ".CONTEXT";

	/**
	 * Request attribute to hold the current LocaleResolver, retrievable by
	 * views.
	 * 
	 * @see org.springframework.web.servlet.support.RequestContextUtils#getLocaleResolver
	 */
	public static final String LOCALE_RESOLVER_ATTRIBUTE = SpringMvcTinyProcessor.class
			.getName() + ".LOCALE_RESOLVER";

	/**
	 * Request attribute to hold the current ThemeResolver, retrievable by
	 * views.
	 * 
	 * @see org.springframework.web.servlet.support.RequestContextUtils#getThemeResolver
	 */
	public static final String THEME_RESOLVER_ATTRIBUTE = SpringMvcTinyProcessor.class
			.getName() + ".THEME_RESOLVER";

	private static final String DEFAULT_CONFIG_LOCATION = "classpath*:conf/spring/mvc-beans.xml";

	/**
	 * Request attribute to hold the current ThemeSource, retrievable by views.
	 * 
	 * @see org.springframework.web.servlet.support.RequestContextUtils#getThemeSource
	 */
	public static final String THEME_SOURCE_ATTRIBUTE = SpringMvcTinyProcessor.class
			.getName() + ".THEME_SOURCE";

	public void setExtensionMappingInstanceResolver(
			ExtensionMappingInstanceResolver extensionMappingInstanceResolver) {
		this.extensionMappingInstanceResolver = extensionMappingInstanceResolver;
	}

	public void setCleanupAfterInclude(boolean cleanupAfterInclude) {
		this.cleanupAfterInclude = cleanupAfterInclude;
	}

	public void setSpringMVCAdapter(SpringMVCAdapter springMVCAdapter) {
		this.springMVCAdapter = springMVCAdapter;
	}

	public String getContextAttribute() {
		return contextAttribute;
	}

	public void setContextAttribute(String contextAttribute) {
		this.contextAttribute = contextAttribute;
	}

	public String getContextConfigLocation() {
		return contextConfigLocation;
	}

	public void setContextConfigLocation(String contextConfigLocation) {
		this.contextConfigLocation = contextConfigLocation;
	}

	public void setPublishEvents(boolean publishEvents) {
		this.publishEvents = publishEvents;
	}

	public void setPublishContext(boolean publishContext) {
		this.publishContext = publishContext;
	}

	@Override
	public void reallyProcess(String urlString, WebContext context)
			throws ServletException, IOException {
		HttpServletRequest request = context.getRequest();
		HttpServletResponse response = context.getResponse();
		long startTime = System.currentTimeMillis();
		Throwable failureCause = null;

		// Expose current LocaleResolver and request as LocaleContext.
		LocaleContext previousLocaleContext = LocaleContextHolder
				.getLocaleContext();
		LocaleContextHolder.setLocaleContext(buildLocaleContext(request),
				this.threadContextInheritable);

		// Expose current RequestAttributes to current thread.
		RequestAttributes previousRequestAttributes = RequestContextHolder
				.getRequestAttributes();
		ServletRequestAttributes requestAttributes = null;
		if (previousRequestAttributes == null
				|| previousRequestAttributes.getClass().equals(
						ServletRequestAttributes.class)) {
			requestAttributes = new ServletRequestAttributes(request);
			RequestContextHolder.setRequestAttributes(requestAttributes,
					this.threadContextInheritable);
		}

		logger.logMessage(LogLevel.TRACE, "Bound request context to thread: "
				+ request);
		try {
			doService(request, response);
		} catch (ServletException ex) {
			failureCause = ex;
			throw ex;
		} catch (IOException ex) {
			failureCause = ex;
			throw ex;
		} catch (Throwable ex) {
			failureCause = ex;
			throw new NestedServletException("Request processing failed", ex);
		}

		finally {
			// Clear request attributes and reset thread-bound context.
			LocaleContextHolder.setLocaleContext(previousLocaleContext,
					this.threadContextInheritable);
			if (requestAttributes != null) {
				RequestContextHolder.setRequestAttributes(
						previousRequestAttributes,
						this.threadContextInheritable);
				requestAttributes.requestCompleted();
			}
			logger.logMessage(LogLevel.TRACE,
					"Cleared thread-bound request context: " + request);

			if (failureCause != null) {
				logger.logMessage(LogLevel.DEBUG, "Could not complete request",
						failureCause);
			} else {
				logger.logMessage(LogLevel.DEBUG,
						"Successfully completed request");
			}
			if (this.publishEvents) {
				// Whether or not we succeeded, publish an event.
				long processingTime = System.currentTimeMillis() - startTime;
				applicationContext.publishEvent(new ServletRequestHandledEvent(
						this, request.getRequestURI(), request.getRemoteAddr(),
						request.getMethod(), "SpringMvcTinyProcessor", WebUtils
								.getSessionId(request),
						getUsernameForRequest(request), processingTime,
						failureCause));
			}
		}

	}

	private void doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Keep a snapshot of the request attributes in case of an include,
		// to be able to restore the original attributes after the include.
		String requestUri = urlPathHelper.getRequestUri(request);
		Map<String, Object> attributesSnapshot = null;
		if (WebUtils.isIncludeRequest(request)) {
			logger.logMessage(LogLevel.DEBUG,
					"Taking snapshot of request attributes before include");
			attributesSnapshot = new HashMap<String, Object>();
			Enumeration<?> attrNames = request.getAttributeNames();
			while (attrNames.hasMoreElements()) {
				String attrName = (String) attrNames.nextElement();
				if (this.cleanupAfterInclude
						|| attrName
								.startsWith("org.springframework.web.servlet")) {
					attributesSnapshot.put(attrName,
							request.getAttribute(attrName));
				}
			}
		}
		ExtensionMappingInstance mappingInstance = extensionMappingInstanceResolver
				.get(request);
		if (mappingInstance == null) {
			throw new ServletException(
					"cannot find ExtensionMappingInstance form url["
							+ requestUri + "]");
		}
		RequestInstanceHolder.setMappingInstance(mappingInstance);
		RequestInstanceHolder.setServletWebRequest(new ServletWebRequest(
				request, response));
		// // Make framework objects available to handlers and view objects.
		request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				applicationContext);
		request.setAttribute(LOCALE_RESOLVER_ATTRIBUTE,
				springMVCAdapter.getLocaleResolver());
		request.setAttribute(THEME_RESOLVER_ATTRIBUTE,
				springMVCAdapter.getThemeResolver());
		request.setAttribute(THEME_SOURCE_ATTRIBUTE, getThemeSource());
		try {
			doDispatch(request, response);
		} finally {
			// Restore the original attribute snapshot, in case of an include.
			if (attributesSnapshot != null) {
				restoreAttributesAfterInclude(request, attributesSnapshot);
			}
		}
	}

	private void doDispatch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpServletRequest processedRequest = request;
		HandlerExecutionChain mappedHandler = null;
		int interceptorIndex = -1;
		boolean multipartRequestParsed = false;
		try {
			ModelAndView mv;
			boolean errorView = false;
			try {
				processedRequest = checkMultipart(request);
				multipartRequestParsed = processedRequest != request;
				// Determine handler for the current request.
				mappedHandler = getHandler(processedRequest);
				if (mappedHandler == null || mappedHandler.getHandler() == null) {
					noHandlerFound(processedRequest, response);
					return;
				}
				// Determine handler adapter for the current request.
				HandlerAdapter ha = getHandlerAdapter(mappedHandler
						.getHandler());

				// Process last-modified header, if supported by the handler.
				String method = request.getMethod();
				boolean isGet = "GET".equals(method);
				if (isGet || "HEAD".equals(method)) {
					long lastModified = ha.getLastModified(request,
							mappedHandler.getHandler());
					String requestUri = urlPathHelper.getRequestUri(request);
					logger.logMessage(LogLevel.DEBUG,
							"Last-Modified value for [" + requestUri + "] is: "
									+ lastModified);
					if (new ServletWebRequest(request, response)
							.checkNotModified(lastModified) && isGet) {
						return;
					}
				}

				// Apply preHandle methods of registered interceptors.
				HandlerInterceptor[] interceptors = mappedHandler
						.getInterceptors();
				if (interceptors != null) {
					for (int i = 0; i < interceptors.length; i++) {
						HandlerInterceptor interceptor = interceptors[i];
						if (!interceptor.preHandle(processedRequest, response,
								mappedHandler.getHandler())) {
							triggerAfterCompletion(mappedHandler,
									interceptorIndex, processedRequest,
									response, null);
							return;
						}
						interceptorIndex = i;
					}
				}

				// Actually invoke the handler.
				mv = ha.handle(processedRequest, response,
						mappedHandler.getHandler());

				// Do we need view name translation?
				if (mv != null && !mv.hasView()) {
					mv.setViewName(getDefaultViewName(request));
				}

				// Apply postHandle methods of registered interceptors.
				if (interceptors != null) {
					for (int i = interceptors.length - 1; i >= 0; i--) {
						HandlerInterceptor interceptor = interceptors[i];
						interceptor.postHandle(processedRequest, response,
								mappedHandler.getHandler(), mv);
					}
				}
			} catch (ModelAndViewDefiningException ex) {
				logger.logMessage(LogLevel.DEBUG,
						"ModelAndViewDefiningException encountered", ex);
				mv = ex.getModelAndView();
			} catch (Exception ex) {
				Object handler = (mappedHandler != null ? mappedHandler
						.getHandler() : null);
				mv = processHandlerException(processedRequest, response,
						handler, ex);
				errorView = (mv != null);
			}

			// Did the handler return a view to render?
			if (mv != null && !mv.wasCleared()) {
				render(mv, processedRequest, response);
				if (errorView) {
					WebUtils.clearErrorRequestAttributes(request);
				}
			} else {
				logger.logMessage(
						LogLevel.DEBUG,
						"Null ModelAndView returned to SpringMvcTinyProcessor assuming HandlerAdapter completed request handling");
			}
			// Trigger after-completion for successful outcome.
			triggerAfterCompletion(mappedHandler, interceptorIndex,
					processedRequest, response, null);
		}

		catch (Exception ex) {
			// Trigger after-completion for thrown exception.
			triggerAfterCompletion(mappedHandler, interceptorIndex,
					processedRequest, response, ex);
			throw ex;
		} catch (Error err) {
			ServletException ex = new NestedServletException(
					"Handler processing failed", err);
			// Trigger after-completion for thrown exception.
			triggerAfterCompletion(mappedHandler, interceptorIndex,
					processedRequest, response, ex);
			throw ex;
		} finally {
			// Clean up any resources used by a multipart request.
			if (multipartRequestParsed) {
				cleanupMultipart(processedRequest);
			}
		}

	}

	public final ThemeSource getThemeSource() {
		if (applicationContext instanceof ThemeSource) {
			return (ThemeSource) applicationContext;
		} else {
			return null;
		}
	}

	/**
	 * Return the HandlerExecutionChain for this request.
	 * <p>
	 * Tries all handler mappings in order.
	 * 
	 * @param request
	 *            current HTTP request
	 * @return the HandlerExecutionChain, or <code>null</code> if no handler
	 *         could be found
	 */
	protected HttpServletRequest checkMultipart(HttpServletRequest request)
			throws Exception {
		MultipartResolver multipartResolver = springMVCAdapter
				.getMultipartResolver();
		if (multipartResolver != null && multipartResolver.isMultipart(request)) {
			if (request instanceof MultipartHttpServletRequest) {
				logger.logMessage(
						LogLevel.DEBUG,
						"Request is already a MultipartHttpServletRequest - if not in a forward,this typically results from an additional MultipartFilter in web.xml");
			} else {
				return multipartResolver.resolveMultipart(request);
			}
		}
		return request;
	}

	/**
	 * Clean up any resources used by the given multipart request (if any).
	 * 
	 * @param request
	 *            current HTTP request
	 * @see MultipartResolver#cleanupMultipart
	 */
	protected void cleanupMultipart(HttpServletRequest request) {
		MultipartResolver multipartResolver = springMVCAdapter
				.getMultipartResolver();
		if (request instanceof MultipartHttpServletRequest) {
			multipartResolver
					.cleanupMultipart((MultipartHttpServletRequest) request);
		}
	}

	/**
	 * Restore the request attributes after an include.
	 * 
	 * @param request
	 *            current HTTP request
	 * @param attributesSnapshot
	 *            the snapshot of the request attributes before the include
	 */
	private void restoreAttributesAfterInclude(HttpServletRequest request,
			Map attributesSnapshot) {
		logger.logMessage(LogLevel.DEBUG,
				"Restoring snapshot of request attributes after include");
		// Need to copy into separate Collection here, to avoid side effects
		// on the Enumeration when removing attributes.
		Set<String> attrsToCheck = new HashSet<String>();
		Enumeration attrNames = request.getAttributeNames();
		while (attrNames.hasMoreElements()) {
			String attrName = (String) attrNames.nextElement();
			if (this.cleanupAfterInclude
					|| attrName.startsWith("org.springframework.web.servlet")) {
				attrsToCheck.add(attrName);
			}
		}

		// Iterate over the attributes to check, restoring the original value
		// or removing the attribute, respectively, if appropriate.
		for (String attrName : attrsToCheck) {
			Object attrValue = attributesSnapshot.get(attrName);
			if (attrValue == null) {
				logger.logMessage(LogLevel.DEBUG, "Removing attribute ["
						+ attrName + "] after include");
				request.removeAttribute(attrName);
			} else if (attrValue != request.getAttribute(attrName)) {
				logger.logMessage(LogLevel.DEBUG,
						"Restoring original value of attribute [" + attrName
								+ "] after include");
				request.setAttribute(attrName, attrValue);
			}
		}
	}

	/**
	 * No handler found -> set appropriate HTTP response status.
	 * 
	 * @param request
	 *            current HTTP request
	 * @param response
	 *            current HTTP response
	 * @throws Exception
	 *             if preparing the response failed
	 */
	protected void noHandlerFound(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String requestUri = urlPathHelper.getRequestUri(request);
		logger.logMessage(LogLevel.WARN,
				"No mapping found for HTTP request with URI [" + requestUri
						+ "] in SpringMvcTinyProcessor");
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * Return the HandlerAdapter for this handler object.
	 * 
	 * @param handler
	 *            the handler object to find an adapter for
	 * @throws ServletException
	 *             if no HandlerAdapter can be found for the handler. This is a
	 *             fatal error.
	 */
	protected HandlerAdapter getHandlerAdapter(Object handler)
			throws ServletException {
		HandlerAdapter ha = springMVCAdapter.getHandlerAdapter();
		if (ha.supports(handler)) {
			return ha;
		}
		throw new ServletException(
				"No adapter for handler ["
						+ handler
						+ "]: Does your handler implement a supported interface like Controller?");
	}

	protected HandlerExecutionChain getHandler(HttpServletRequest request)
			throws Exception {
		HandlerExecutionChain handler = springMVCAdapter.getHandlerMapping()
				.getHandler(request);
		if (handler != null) {
			return handler;
		}
		return null;
	}

	/**
	 * Trigger afterCompletion callbacks on the mapped HandlerInterceptors. Will
	 * just invoke afterCompletion for all interceptors whose preHandle
	 * invocation has successfully completed and returned true.
	 * 
	 * @param mappedHandler
	 *            the mapped HandlerExecutionChain
	 * @param interceptorIndex
	 *            index of last interceptor that successfully completed
	 * @param ex
	 *            Exception thrown on handler execution, or <code>null</code> if
	 *            none
	 * @see HandlerInterceptor#afterCompletion
	 */
	private void triggerAfterCompletion(HandlerExecutionChain mappedHandler,
			int interceptorIndex, HttpServletRequest request,
			HttpServletResponse response, Exception ex) throws Exception {

		// Apply afterCompletion methods of registered interceptors.
		if (mappedHandler != null) {
			HandlerInterceptor[] interceptors = mappedHandler.getInterceptors();
			if (interceptors != null) {
				for (int i = interceptorIndex; i >= 0; i--) {
					HandlerInterceptor interceptor = interceptors[i];
					try {
						interceptor.afterCompletion(request, response,
								mappedHandler.getHandler(), ex);
					} catch (Throwable ex2) {
						logger.errorMessage(
								"HandlerInterceptor.afterCompletion threw exception",
								ex2);
					}
				}
			}
		}
	}

	/**
	 * Translate the supplied request into a default view name.
	 * 
	 * @param request
	 *            current HTTP servlet request
	 * @return the view name (or <code>null</code> if no default found)
	 * @throws Exception
	 *             if view name translation failed
	 */
	protected String getDefaultViewName(HttpServletRequest request)
			throws Exception {
		return springMVCAdapter.getViewNameTranslator().getViewName(request);
	}

	/**
	 * Determine an error ModelAndView via the registered
	 * HandlerExceptionResolvers.
	 * 
	 * @param request
	 *            current HTTP request
	 * @param response
	 *            current HTTP response
	 * @param handler
	 *            the executed handler, or <code>null</code> if none chosen at
	 *            the time of the exception (for example, if multipart
	 *            resolution failed)
	 * @param ex
	 *            the exception that got thrown during handler execution
	 * @return a corresponding ModelAndView to forward to
	 * @throws Exception
	 *             if no error ModelAndView found
	 */
	protected ModelAndView processHandlerException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		// Check registered HandlerExceptionResolvers...
		ModelAndView exMv = springMVCAdapter.getHandlerExceptionResolver()
				.resolveException(request, response, handler, ex);
		if (exMv != null) {
			if (exMv.isEmpty()) {
				return null;
			}
			// We might still need view name translation for a plain error
			// model...
			if (!exMv.hasView()) {
				exMv.setViewName(getDefaultViewName(request));
			}
			logger.logMessage(LogLevel.DEBUG,
					"Handler execution resulted in exception - forwarding to resolved error view: "
							+ exMv, ex);
			WebUtils.exposeErrorRequestAttributes(request, ex, this.getClass()
					.getSimpleName());
			return exMv;
		}

		throw ex;
	}

	/**
	 * Render the given ModelAndView.
	 * <p>
	 * This is the last stage in handling a request. It may involve resolving
	 * the view by name.
	 * 
	 * @param mv
	 *            the ModelAndView to render
	 * @param request
	 *            current HTTP servlet request
	 * @param response
	 *            current HTTP servlet response
	 * @throws ServletException
	 *             if view is missing or cannot be resolved
	 * @throws Exception
	 *             if there's a problem rendering the view
	 */
	protected void render(ModelAndView mv, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Determine locale for request and apply it to the response.
		Locale locale = springMVCAdapter.getLocaleResolver().resolveLocale(
				request);
		response.setLocale(locale);

		View view;
		if (mv.isReference()) {
			// We need to resolve the view name.
			view = resolveViewName(mv.getViewName(), mv.getModel(), locale,
					request);
			if (view == null) {
				throw new ServletException("Could not resolve view with name '"
						+ mv.getViewName() + "' in SpringMvcTinyProcessor");
			}
		} else {
			// No need to lookup: the ModelAndView object contains the actual
			// View object.
			view = mv.getView();
			if (view == null) {
				throw new ServletException("ModelAndView [" + mv
						+ "] neither contains a view name nor a "
						+ "View object in SpringMvcTinyProcessor");
			}
		}
		// Delegate to the View object for rendering.
		logger.logMessage(LogLevel.DEBUG,
				"Rendering view [{0}] in SpringMvcTinyProcessor", view);
		view.render(mv.getModel(), request, response);
	}

	/**
	 * Resolve the given view name into a View object (to be rendered).
	 * <p>
	 * The default implementations asks all ViewResolvers of this dispatcher.
	 * Can be overridden for custom resolution strategies, potentially based on
	 * specific model attributes or request parameters.
	 * 
	 * @param viewName
	 *            the name of the view to resolve
	 * @param model
	 *            the model to be passed to the view
	 * @param locale
	 *            the current locale
	 * @param request
	 *            current HTTP servlet request
	 * @return the View object, or <code>null</code> if none found
	 * @throws Exception
	 *             if the view cannot be resolved (typically in case of problems
	 *             creating an actual View object)
	 * @see ViewResolver#resolveViewName
	 */
	protected View resolveViewName(String viewName, Map<String, Object> model,
			Locale locale, HttpServletRequest request) throws Exception {
		View view = springMVCAdapter.getViewResolver().resolveViewName(
				viewName, locale);
		if (view != null) {
			return view;
		}
		return null;
	}

	/**
	 * Build a LocaleContext
	 * 
	 * @param request
	 *            current HTTP request
	 * @return the corresponding LocaleContext
	 */
	protected LocaleContext buildLocaleContext(HttpServletRequest request) {
		return new SimpleLocaleContext(LocaleUtil.getContext().getLocale());
	}

	/**
	 * Determine the username for the given request.
	 * <p>
	 * The default implementation takes the name of the UserPrincipal, if any.
	 * Can be overridden in subclasses.
	 * 
	 * @param request
	 *            current HTTP request
	 * @return the username, or <code>null</code> if none found
	 * @see javax.servlet.http.HttpServletRequest#getUserPrincipal()
	 */
	protected String getUsernameForRequest(HttpServletRequest request) {
		Principal userPrincipal = request.getUserPrincipal();
		return (userPrincipal != null ? userPrincipal.getName() : null);
	}

	@Override
	protected void customInit() throws ServletException {
		if (StringUtil.isBlank(contextAttribute)) {
			contextAttribute = StringUtil.defaultIfBlank(
					get(CONTEXT_ATTRIBUTE_NAME),
					WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		}
		if (StringUtil.isBlank(contextConfigLocation)) {
			contextConfigLocation = StringUtil.defaultIfBlank(
					get(CONTEXT_ATTRIBUTE_NAME), DEFAULT_CONFIG_LOCATION);
		}
		applicationContext = initWebApplicationContext();
		if (springMVCAdapter == null) {
			springMVCAdapter = (SpringMVCAdapter) applicationContext
					.getBean("springMVCAdapter");
		}
		if (extensionMappingInstanceResolver == null) {
			extensionMappingInstanceResolver = (ExtensionMappingInstanceResolver) applicationContext
					.getBean("extensionMappingInstanceResolver");
		}
	}

	/**
	 * Initialize and publish the WebApplicationContext for this servlet.
	 * <p>
	 * Delegates to {@link #createWebApplicationContext} for actual creation of
	 * the context. Can be overridden in subclasses.
	 * 
	 * @return the WebApplicationContext instance
	 * @see #setContextClass
	 * @see #setContextConfigLocation
	 */
	protected WebApplicationContext initWebApplicationContext() {
		WebApplicationContext wac = findWebApplicationContext();
		if (wac == null) {
			wac = createWebApplicationContext(parent);
		}
		if (!this.refreshEventReceived) {
			// Apparently not a ConfigurableApplicationContext with refresh
			// support:
			// triggering initial onRefresh manually here.
			onRefresh(wac);
		}

		if (this.publishContext) {
			// Publish the context as a servlet context attribute.
			String attrName = getContextAttribute();
			getServletContext().setAttribute(attrName, wac);
			getServletContext()
					.setAttribute(
							WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
							wac);
			logger.logMessage(LogLevel.DEBUG,
					"Published WebApplicationContext of tinyprocessor '"
							+ getProcessorName()
							+ "' as ServletContext attribute with name ["
							+ attrName + "]");
		}

		return wac;
	}

	protected ServletContext getServletContext() {
		return ServletContextHolder.getServletContext();
	}

	protected WebApplicationContext findWebApplicationContext() {
		String attrName = getContextAttribute();
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext(), attrName);
		return wac;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.parent = applicationContext;
	}

	protected WebApplicationContext createWebApplicationContext(
			ApplicationContext parent) {
		Class contextClass = XmlWebApplicationContext.class;
		logger.logMessage(
				LogLevel.DEBUG,
				"Servlet with name '"
						+ getProcessorName()
						+ "' will try to create custom WebApplicationContext context of class '"
						+ contextClass.getName() + "'"
						+ ", using parent context [" + parent + "]");
		ConfigurableWebApplicationContext wac = (ConfigurableWebApplicationContext) BeanUtils
				.instantiateClass(contextClass);
		// Assign the best possible id value.
		ServletContext sc = getServletContext();
		if (sc.getMajorVersion() == 2 && sc.getMinorVersion() < 5) {
			// Servlet <= 2.4: resort to name specified in web.xml, if any.
			String servletContextName = sc.getServletContextName();
			if (servletContextName != null) {
				wac.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX
						+ servletContextName + "." + getProcessorName());
			} else {
				wac.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX
						+ getProcessorName());
			}
		} else {
			wac.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX
					+ sc.getContextPath() + "/" + getProcessorName());
		}
		wac.setParent(parent);
		wac.setServletContext(getServletContext());
		wac.setConfigLocation(getContextConfigLocation());
		wac.addApplicationListener(new SourceFilteringListener(wac,
				new ContextRefreshListener()));
		postProcessWebApplicationContext(wac);
		wac.refresh();
		return wac;
	}

	protected void postProcessWebApplicationContext(
			ConfigurableWebApplicationContext wac) {
	}

	/**
	 * Callback that receives refresh events from this servlet's
	 * WebApplicationContext.
	 * <p>
	 * The default implementation calls {@link #onRefresh}, triggering a refresh
	 * of this servlet's context-dependent state.
	 * 
	 * @param event
	 *            the incoming ApplicationContext event
	 */
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.refreshEventReceived = true;
		onRefresh(event.getApplicationContext());
	}

	protected void onRefresh(ApplicationContext context) {
		// For subclasses: do nothing by default.
	}

	protected WebApplicationContext createWebApplicationContext(
			WebApplicationContext parent) {
		return createWebApplicationContext((ApplicationContext) parent);
	}

	/**
	 * ApplicationListener endpoint that receives events from this servlet's
	 * WebApplicationContext only, delegating to <code>onApplicationEvent</code>
	 * on the FrameworkServlet instance.
	 */
	private class ContextRefreshListener implements
			ApplicationListener<ContextRefreshedEvent> {

		public void onApplicationEvent(ContextRefreshedEvent event) {
			SpringMvcTinyProcessor.this.onApplicationEvent(event);
		}
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
