package org.tinygroup.springmvc.handlermapping;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.OrderComparator;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.tinygroup.assembly.AssemblyService;
import org.tinygroup.assembly.DefaultAssemblyService;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class TinyHandlerMappingComposite extends ApplicationObjectSupport
		implements HandlerMapping, InitializingBean {
	private static final Logger logger = LoggerFactory
			.getLogger(TinyHandlerMappingComposite.class);
	private List<HandlerMapping> handlerMappingComposite;

	private AssemblyService<HandlerMapping> assemblyService = new DefaultAssemblyService<HandlerMapping>();

	public void setAssemblyService(
			AssemblyService<HandlerMapping> assemblyService) {
		this.assemblyService = assemblyService;
	}

	public void setHandlerMappingComposite(
			List<HandlerMapping> handlerMappingComposite) {
		this.handlerMappingComposite = handlerMappingComposite;
	}

	public void afterPropertiesSet() throws Exception {
		if (CollectionUtil.isEmpty(handlerMappingComposite)) {
			List<HandlerMapping> exclusions = new ArrayList<HandlerMapping>();
			exclusions.add(this.getApplicationContext().getBean(
					TinyHandlerMapping.class));
			exclusions.add(this);
			assemblyService.setApplicationContext(getApplicationContext());
			assemblyService.setExclusions(exclusions);
			handlerMappingComposite = assemblyService
					.findParticipants(HandlerMapping.class);
			OrderComparator.sort(this.handlerMappingComposite);
		}
	}

	public HandlerExecutionChain getHandler(HttpServletRequest request)
			throws Exception {
		if (!CollectionUtil.isEmpty(handlerMappingComposite)) {
			for (HandlerMapping handlerMapping : handlerMappingComposite) {
				HandlerExecutionChain handlerExecutionChain = handlerMapping
						.getHandler(request);
				if (handlerExecutionChain != null) {
					logger.logMessage(
							LogLevel.DEBUG,
							"invoke HandlerMapping.getHandler() method that will proxy [{0}]",
							handlerExecutionChain);
					return handlerExecutionChain;
				}
			}
		}
		return null;
	}

}
