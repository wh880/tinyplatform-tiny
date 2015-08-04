/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.springmvc.handleradapter;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.http.converter.xml.XmlAwareFormHttpMessageConverter;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.DefaultSessionAttributeStore;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.servlet.support.WebContentGenerator;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springmvc.coc.ConventionHelper;
import org.tinygroup.springmvc.support.ServletHandlerMethodResolver;
import org.tinygroup.springmvc.support.TinyServletHandlerMethodInvoker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Source;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽像的MethodHandlerAdapter。
 * 
 */
public abstract class AbstractMethodHandlerAdapter extends WebContentGenerator
		implements HandlerAdapter, BeanFactoryAware {

	/**
	 * Log category to use when no mapped handler is found for a request.
	 * 
	 * @see #pageNotFoundLogger
	 */
	public static final String PAGE_NOT_FOUND_LOG_CATEGORY = "org.springframework.web.servlet.PageNotFound";

	/**
	 * Additional logger to use when no mapped handler is found for a request.
	 * 
	 * @see #PAGE_NOT_FOUND_LOG_CATEGORY
	 */
	protected static final Logger pageNotFoundLogger = LoggerFactory
			.getLogger(PAGE_NOT_FOUND_LOG_CATEGORY);

	private WebBindingInitializer webBindingInitializer;

	private SessionAttributeStore sessionAttributeStore = new DefaultSessionAttributeStore();

	private boolean synchronizeOnSession = false;

	private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

	private WebArgumentResolver[] customArgumentResolvers;

	protected UrlPathHelper urlPathHelper = new UrlPathHelper();

	protected PathMatcher pathMatcher = new AntPathMatcher();

	protected MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver();

	private final Map<Class<?>, ServletHandlerMethodResolver> methodResolverCache = new HashMap<Class<?>, ServletHandlerMethodResolver>();

	private ModelAndViewResolver[] customModelAndViewResolvers;

	private HttpMessageConverter<?>[] messageConverters;

	private ConfigurableBeanFactory beanFactory;

	private BeanExpressionContext expressionContext;

	private ConventionHelper conventionHelper;

	public void setConventionHelper(ConventionHelper conventionHelper) {
		this.conventionHelper = conventionHelper;
	}

	public ConfigurableBeanFactory getBeanFactory() {
		return beanFactory;
	}

	public BeanExpressionContext getExpressionContext() {
		return expressionContext;
	}

	public UrlPathHelper getUrlPathHelper() {
		return urlPathHelper;
	}

	public PathMatcher getPathMatcher() {
		return pathMatcher;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		if (beanFactory instanceof ConfigurableBeanFactory) {
			this.beanFactory = (ConfigurableBeanFactory) beanFactory;
			this.expressionContext = new BeanExpressionContext(
					this.beanFactory, new RequestScope());
		}
	}

	public AbstractMethodHandlerAdapter() {
		super(false);
		// See SPR-7316
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
		stringHttpMessageConverter.setWriteAcceptCharset(false);
		this.messageConverters = new HttpMessageConverter[] {
				new ByteArrayHttpMessageConverter(),
				stringHttpMessageConverter,
				new SourceHttpMessageConverter<Source>(),
				new XmlAwareFormHttpMessageConverter(),new MappingJacksonHttpMessageConverter()};
	}

	public void setCustomModelAndViewResolver(
			ModelAndViewResolver customModelAndViewResolver) {
		this.customModelAndViewResolvers = new ModelAndViewResolver[] { customModelAndViewResolver };
	}

	public void setCustomModelAndViewResolvers(
			ModelAndViewResolver[] customModelAndViewResolvers) {
		this.customModelAndViewResolvers = customModelAndViewResolvers;
	}

	public ModelAndViewResolver[] getCustomModelAndViewResolvers() {
		return customModelAndViewResolvers;
	}

	/**
	 * Set if URL lookup should always use the full path within the current
	 * servlet context. Else, the path within the current servlet mapping is
	 * used if applicable (that is, in the case of a ".../*" servlet mapping in
	 * web.xml).
	 * <p/>
	 * Default is "false".
	 * 
	 * @see org.springframework.web.util.UrlPathHelper#setAlwaysUseFullPath
	 */
	public void setAlwaysUseFullPath(boolean alwaysUseFullPath) {
		this.urlPathHelper.setAlwaysUseFullPath(alwaysUseFullPath);
	}

	/**
	 * Set if context path and request URI should be URL-decoded. Both are
	 * returned <i>undecoded</i> by the Servlet API, in contrast to the servlet
	 * path.
	 * <p/>
	 * Uses either the request encoding or the default encoding according to the
	 * Servlet spec (ISO-8859-1).
	 * 
	 * @see org.springframework.web.util.UrlPathHelper#setUrlDecode
	 */
	public void setUrlDecode(boolean urlDecode) {
		this.urlPathHelper.setUrlDecode(urlDecode);
	}

	/**
	 * Set the UrlPathHelper to use for resolution of lookup paths.
	 * <p/>
	 * Use this to override the default UrlPathHelper with a custom subclass, or
	 * to share common UrlPathHelper settings across multiple HandlerMappings
	 * and HandlerAdapters.
	 */
	public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
		Assert.notNull(urlPathHelper, "UrlPathHelper must not be null");
		this.urlPathHelper = urlPathHelper;
	}

	/**
	 * Set the PathMatcher implementation to use for matching URL paths against
	 * registered URL patterns. Default is AntPathMatcher.
	 * 
	 * @see org.springframework.util.AntPathMatcher
	 */
	public void setPathMatcher(PathMatcher pathMatcher) {
		Assert.notNull(pathMatcher, "PathMatcher must not be null");
		this.pathMatcher = pathMatcher;
	}

	/**
	 * Set the MethodNameResolver to use for resolving default handler methods
	 * (carrying an empty <code>@RequestMapping</code> annotation).
	 * <p/>
	 * Will only kick in when the handler method cannot be resolved uniquely
	 * through the annotation metadata already.
	 */
	public void setMethodNameResolver(MethodNameResolver methodNameResolver) {
		this.methodNameResolver = methodNameResolver;
	}

	/**
	 * Specify a WebBindingInitializer which will apply pre-configured
	 * configuration to every DataBinder that this controller uses.
	 */
	public void setWebBindingInitializer(
			WebBindingInitializer webBindingInitializer) {
		this.webBindingInitializer = webBindingInitializer;
	}

	/**
	 * Specify the strategy to store session attributes with.
	 * <p/>
	 * Default is
	 * {@link org.springframework.web.bind.support.DefaultSessionAttributeStore}
	 * , storing session attributes in the HttpSession, using the same attribute
	 * name as in the model.
	 */
	public void setSessionAttributeStore(
			SessionAttributeStore sessionAttributeStore) {
		Assert.notNull(sessionAttributeStore,
				"SessionAttributeStore must not be null");
		this.sessionAttributeStore = sessionAttributeStore;
	}

	/**
	 * Set if controller execution should be synchronized on the session, to
	 * serialize parallel invocations from the same client.
	 * <p/>
	 * More specifically, the execution of each handler method will get
	 * synchronized if this flag is "true". The best available session mutex
	 * will be used for the synchronization; ideally, this will be a mutex
	 * exposed by HttpSessionMutexListener.
	 * <p/>
	 * The session mutex is guaranteed to be the same object during the entire
	 * lifetime of the session, available under the key defined by the
	 * <code>SESSION_MUTEX_ATTRIBUTE</code> constant. It serves as a safe
	 * reference to synchronize on for locking on the current session.
	 * <p/>
	 * In many cases, the HttpSession reference itself is a safe mutex as well,
	 * since it will always be the same object reference for the same active
	 * logical session. However, this is not guaranteed across different servlet
	 * containers; the only 100% safe way is a session mutex.
	 * 
	 * @see org.springframework.web.util.HttpSessionMutexListener
	 * @see org.springframework.web.util.WebUtils#getSessionMutex(javax.servlet.http.HttpSession)
	 */
	public void setSynchronizeOnSession(boolean synchronizeOnSession) {
		this.synchronizeOnSession = synchronizeOnSession;
	}

	/**
	 * Set the ParameterNameDiscoverer to use for resolving method parameter
	 * names if needed (e.g. for default attribute names).
	 * <p/>
	 * Default is a
	 * {@link org.springframework.core.LocalVariableTableParameterNameDiscoverer}.
	 */
	public void setParameterNameDiscoverer(
			ParameterNameDiscoverer parameterNameDiscoverer) {
		this.parameterNameDiscoverer = parameterNameDiscoverer;
	}

	/**
	 * Set a custom ArgumentResolvers to use for special method parameter types.
	 * Such a custom ArgumentResolver will kick in first, having a chance to
	 * resolve an argument value before the standard argument handling kicks in.
	 */
	public void setCustomArgumentResolver(WebArgumentResolver argumentResolver) {
		this.customArgumentResolvers = new WebArgumentResolver[] { argumentResolver };
	}

	/**
	 * Set one or more custom ArgumentResolvers to use for special method
	 * parameter types. Any such custom ArgumentResolver will kick in first,
	 * having a chance to resolve an argument value before the standard argument
	 * handling kicks in.
	 */
	public void setCustomArgumentResolvers(
			WebArgumentResolver[] argumentResolvers) {
		this.customArgumentResolvers = argumentResolvers;
	}

	public long getLastModified(HttpServletRequest request, Object handler) {
		return -1;
	}

	public boolean isConventionHandler(Object handler) {
		return conventionHelper.isHandler(handler);
	}

	public ModelAndView handle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (handler.getClass().getAnnotation(SessionAttributes.class) != null) {
			// Always prevent caching in case of session attribute management.
			checkAndPrepare(request, response, 0, true);
			// Prepare cached set of session attributes names.
		} else {
			// Uses configured default cacheSeconds setting.
			checkAndPrepare(request, response, true);
		}

		// Execute invokeHandlerMethod in synchronized block if required.
		if (this.synchronizeOnSession) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				Object mutex = WebUtils.getSessionMutex(session);
				synchronized (mutex) {
					return invokeHandlerMethod(request, response, handler);
				}
			}
		}

		return invokeHandlerMethod(request, response, handler);

	}

	private ModelAndView invokeHandlerMethod(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			ServletHandlerMethodResolver methodResolver = getMethodResolver(handler);
			Method handlerMethod = methodResolver.resolveHandlerMethod(request);
			TinyServletHandlerMethodInvoker methodInvoker = new TinyServletHandlerMethodInvoker(
					methodResolver, webBindingInitializer,
					sessionAttributeStore, parameterNameDiscoverer,
					customArgumentResolvers, this, messageConverters);
			ServletWebRequest webRequest = new ServletWebRequest(request,
					response);
			ExtendedModelMap implicitModel = new ExtendedModelMap();
			Object result = methodInvoker.invokeHandlerMethod(handlerMethod,
					handler, webRequest, implicitModel);
			ModelAndView mav = methodInvoker.getModelAndView(handlerMethod,
					handler.getClass(), result, implicitModel, webRequest);
			methodInvoker.updateModelAttributes(handler,
					(mav != null ? mav.getModel() : null), implicitModel,
					webRequest);
			return mav;
		} catch (NoSuchRequestHandlingMethodException ex) {
			return handleNoSuchRequestHandlingMethod(ex, request, response);
		}
	}

	/**
	 * Handle the case where no request handler method was found.
	 * <p/>
	 * The default implementation logs a warning and sends an HTTP 404 error.
	 * Alternatively, a fallback view could be chosen, or the
	 * NoSuchRequestHandlingMethodException could be rethrown as-is.
	 * 
	 * @param ex
	 *            the NoSuchRequestHandlingMethodException to be handled
	 * @param request
	 *            current HTTP request
	 * @param response
	 *            current HTTP response
	 * @return a ModelAndView to render, or <code>null</code> if handled
	 *         directly
	 * @throws Exception
	 *             an Exception that should be thrown as result of the servlet
	 *             request
	 */
	protected ModelAndView handleNoSuchRequestHandlingMethod(
			NoSuchRequestHandlingMethodException ex,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		pageNotFoundLogger.logMessage(LogLevel.WARN, ex.getMessage());
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}

	protected ServletHandlerMethodResolver getMethodResolver(Object handler) {
		Class<?> handlerClass = ClassUtils.getUserClass(handler);
		ServletHandlerMethodResolver resolver = this.methodResolverCache
				.get(handlerClass);
		if (resolver == null) {
			resolver = new ServletHandlerMethodResolver(handlerClass,
					this.urlPathHelper, this.pathMatcher,
					this.methodNameResolver, conventionHelper);
			this.methodResolverCache.put(handlerClass, resolver);
		}
		return resolver;
	}

	public ServletRequestDataBinder createBinder(HttpServletRequest request,
			Object target, String objectName) throws Exception {
		return new ServletRequestDataBinder(target, objectName);
	}

	public abstract boolean supports(Object handler);

}
