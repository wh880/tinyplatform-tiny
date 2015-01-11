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
package org.tinygroup.weblayer.tinyprocessor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.weblayer.AbstractTinyProcessor;
import org.tinygroup.weblayer.WebContext;
import org.tinygroup.weblayer.impl.TinyServletConfig;

/**
 * 把Servlet包装成TinyProcessor
 * 
 * @author luoguo
 * 
 */
public class TinyProcessorWapper extends AbstractTinyProcessor {
	private HttpServlet servlet;
	private static final Logger logger = LoggerFactory
			.getLogger(TinyProcessorWapper.class);

	
	public void init() {
		super.init();
		String servletBeanName = getInitParamMap().get(
				TinyServletConfig.SERVLET_BEAN);
		if (servletBeanName != null) {
			servlet = BeanContainerFactory.getBeanContainer(this.getClass().getClassLoader()).getBean(servletBeanName);
			if (servlet != null) {
				try {
					TinyServletConfig servletConfig= new TinyServletConfig();
					servletConfig.setInitParams(getInitParamMap());
					servletConfig.setServletConfig(servlet.getServletConfig());
					servlet.init(servletConfig);
				} catch (ServletException e) {
					logger.errorMessage("初始化servlet:{}出现异常", e, servletBeanName);
					throw new RuntimeException("初始化servlet出现异常", e);
				}
			} else {
				throw new RuntimeException("找不到bean名称：{}对应的servlet");
			}
		}
	}

	
	public void reallyProcess(String urlString, WebContext context) throws ServletException, IOException {

		HttpServletRequest request = context.getRequest();
		HttpServletResponse response = context.getResponse();
//		try {
			servlet.service(request, response);
//		} catch (Exception e) {
//			e.printStackTrace();
////			logger.errorMessage("servlet:{}执行出现异常", e, servlet.getServletName());
//			throw new RuntimeException("servlet执行出现异常", e);
//		}

	}

	
	public void destroy() {
		super.destroy();
		servlet.destroy();
	}

	
}
