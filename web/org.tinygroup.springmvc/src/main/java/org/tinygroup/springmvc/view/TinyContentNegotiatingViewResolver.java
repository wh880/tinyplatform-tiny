package org.tinygroup.springmvc.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.core.OrderComparator;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.tinygroup.assembly.AssemblyService;
import org.tinygroup.assembly.DefaultAssemblyService;

/**
 * tiny模式的内容协商视图解析
 * 
 * @author renhui
 *
 */
public class TinyContentNegotiatingViewResolver extends
		WebApplicationObjectSupport implements ViewResolver {

	private ContentNegotiatingViewResolver contentNegotiatingViewResolver;

	private Map<String, String> mediaTypes = new ConcurrentHashMap<String, String>();

	private AssemblyService<ViewResolver> assemblyService = new DefaultAssemblyService<ViewResolver>();

	public void setAssemblyService(AssemblyService<ViewResolver> assemblyService) {
		this.assemblyService = assemblyService;
	}

	@Override
	protected void initServletContext(ServletContext servletContext) {
		List<ViewResolver> exclusions = new ArrayList<ViewResolver>();
		exclusions.add(this.getApplicationContext().getBean(
				TinyViewResolver.class));
		try {
			Map<String, ContentNegotiatingViewResolver> contentNegotiatingViewResolvers = BeanFactoryUtils
					.beansOfTypeIncludingAncestors(
							this.getApplicationContext(),
							ContentNegotiatingViewResolver.class);
			if (!contentNegotiatingViewResolvers.isEmpty()) {
				exclusions.addAll(contentNegotiatingViewResolvers.values());
			}
		} catch (Exception e) {
		}
		exclusions.add(this);
		assemblyService.setApplicationContext(getApplicationContext());
		assemblyService.setExclusions(exclusions);
		List<ViewResolver> viewResolvers = assemblyService
				.findParticipants(ViewResolver.class);
		OrderComparator.sort(viewResolvers);
		contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
		contentNegotiatingViewResolver
				.setApplicationContext(getApplicationContext());
		contentNegotiatingViewResolver
				.setDefaultContentType(MediaType.TEXT_HTML);
		try {
			List<View> defaultViews = new ArrayList<View>();
			Map<String, DefaultViewsStorage> defaultViewsStorages = BeanFactoryUtils
					.beansOfTypeIncludingAncestors(
							this.getApplicationContext(),
							DefaultViewsStorage.class);
			if (!defaultViewsStorages.isEmpty()) {
				for (DefaultViewsStorage defaultViewsStorage : defaultViewsStorages
						.values()) {
					defaultViews.addAll(defaultViewsStorage.getDefaultViews());
				}
			}
			contentNegotiatingViewResolver.setDefaultViews(defaultViews);
		} catch (Exception e) {
		}
		contentNegotiatingViewResolver.setMediaTypes(mediaTypes);
		try {
			Map<String, MediaTypeMapping> mappings = BeanFactoryUtils
					.beansOfTypeIncludingAncestors(
							this.getApplicationContext(),
							MediaTypeMapping.class);
			if (!mappings.isEmpty()) {
				for (MediaTypeMapping mediaTypeMapping : mappings.values()) {
					contentNegotiatingViewResolver
							.setMediaTypes(mediaTypeMapping.getMediaTypes());
				}
			}
		} catch (Exception e) {

		}
		contentNegotiatingViewResolver.setFavorParameter(true);
		contentNegotiatingViewResolver.setFavorPathExtension(true);
		contentNegotiatingViewResolver.setIgnoreAcceptHeader(false);
		contentNegotiatingViewResolver.setServletContext(servletContext);
		contentNegotiatingViewResolver.setUseJaf(true);
		contentNegotiatingViewResolver.setUseNotAcceptableStatusCode(false);
		contentNegotiatingViewResolver.setViewResolvers(viewResolvers);
	}

	public void setMediaTypes(Map<String, String> mediaTypes) {
		this.mediaTypes = mediaTypes;
	}

	public View resolveViewName(String viewName, Locale locale)
			throws Exception {
		return contentNegotiatingViewResolver.resolveViewName(viewName, locale);
	}

}
