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
package org.tinygroup.springmvc.exceptionresolver;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.tinygroup.assembly.AssemblyService;
import org.tinygroup.assembly.DefaultAssemblyService;
import org.tinygroup.commons.tools.CollectionUtil;

/**
 * 异常解析的复合类
 * 
 * @author renhui
 * 
 */
public class HandlerExceptionResolverComposite extends ApplicationObjectSupport
		implements HandlerExceptionResolver, InitializingBean {

	private List<HandlerExceptionResolver> composite;
	
	private AssemblyService<HandlerExceptionResolver> assemblyService=new DefaultAssemblyService<HandlerExceptionResolver>();
	
	public void setAssemblyService(
			AssemblyService<HandlerExceptionResolver> assemblyService) {
		this.assemblyService = assemblyService;
	}

	public void setComposite(List<HandlerExceptionResolver> composite) {
		this.composite = composite;
	}

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		if (!CollectionUtil.isEmpty(composite)) {
			for (HandlerExceptionResolver resolver : composite) {
				ModelAndView modelAndView = resolver.resolveException(request,
						response, handler, ex);
				if (modelAndView != null) {
					return modelAndView;
				}
			}
		}
		return null;
	}

	public void afterPropertiesSet() throws Exception {
		if (CollectionUtil.isEmpty(composite)) {
			List<HandlerExceptionResolver> exclusions=new ArrayList<HandlerExceptionResolver>();
			exclusions.add(this.getApplicationContext().getBean(TinyHandlerExceptionResolver.class));
			exclusions.add(this);
			assemblyService.setApplicationContext(getApplicationContext());
			assemblyService.setExclusions(exclusions);
			composite = assemblyService.findParticipants(HandlerExceptionResolver.class);
		}
	}

}
