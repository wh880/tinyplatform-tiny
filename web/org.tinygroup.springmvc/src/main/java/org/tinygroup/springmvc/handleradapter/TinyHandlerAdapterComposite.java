package org.tinygroup.springmvc.handleradapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.tinygroup.assembly.AssemblyService;
import org.tinygroup.assembly.DefaultAssemblyService;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

/**
 * HandlerAdapter的合成类，分派子HandlerAdapter进行处理
 * @author renhui
 *
 */
public class TinyHandlerAdapterComposite extends ApplicationObjectSupport
		implements HandlerAdapter, InitializingBean {

	private static final Logger logger = LoggerFactory
			.getLogger(TinyHandlerAdapterComposite.class);

	private List<HandlerAdapter> handlerAdapterComposite;

	private AssemblyService<HandlerAdapter> assemblyService = new DefaultAssemblyService<HandlerAdapter>();

	public void setAssemblyService(
			AssemblyService<HandlerAdapter> assemblyService) {
		this.assemblyService = assemblyService;
	}

	public void setHandlerMappingComposite(
			List<HandlerAdapter> handlerAdapterComposite) {
		this.handlerAdapterComposite = handlerAdapterComposite;
	}

	public void afterPropertiesSet() throws Exception {
		if (CollectionUtil.isEmpty(handlerAdapterComposite)) {
			List<HandlerAdapter> exclusions = new ArrayList<HandlerAdapter>();
			exclusions.add(this.getApplicationContext().getBean(
					TinyHandlerAdapter.class));
			exclusions.add(this);
			assemblyService.setApplicationContext(getApplicationContext());
			assemblyService.setExclusions(exclusions);
			handlerAdapterComposite = assemblyService
					.findParticipants(HandlerAdapter.class);
		}

	}

	public boolean supports(Object handler) {
		HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
		if (handlerAdapter != null) {
			logger.logMessage(
					LogLevel.DEBUG,
					" invoke  handerAdapter.supports() method that will proxy [{0}]",
					handlerAdapter);
			return handlerAdapter.supports(handler);
		}
		// 如果为null
		return false;
	}

	public ModelAndView handle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
		if (handlerAdapter != null) {
			logger.logMessage(
					LogLevel.DEBUG,
					" invoke  handerAdapter.handle() method that will proxy [{0}]",
					handlerAdapter);
			return handlerAdapter.handle(request, response, handler);
		}
		return null;
	}

	public long getLastModified(HttpServletRequest request, Object handler) {
		HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
		if (handlerAdapter != null) {
			logger.logMessage(
					LogLevel.DEBUG,
					" invoke  handerAdapter.getLastModified() method that will proxy [{0}]",
					handlerAdapter);
			return handlerAdapter.getLastModified(request, handler);
		}
		return -1;
	}

	protected HandlerAdapter getHandlerAdapter(Object handler) {
		Assert.assertNotNull(handlerAdapterComposite,
				"handlerAdapterComposite must not be null");
		if (handlerAdapterComposite.size() == 1) {
			return handlerAdapterComposite.iterator().next();
		}

		Iterator<HandlerAdapter> it = handlerAdapterComposite.iterator();
		while (it.hasNext()) {
			HandlerAdapter ha = it.next();
			if (ha.supports(handler)) {
				return ha;
			}
		}
		return null;
	}

}
