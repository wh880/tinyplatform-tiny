package org.tinygroup.springmvc.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.tinygroup.assembly.AssemblyService;
import org.tinygroup.assembly.DefaultAssemblyService;
import org.tinygroup.commons.tools.CollectionUtil;

/**
 * HttpMessageConverter的复合类
 * @author renhui
 *
 */
public class HttpMessageConverterComposite extends ApplicationObjectSupport
		implements InitializingBean {

	private HttpMessageConverter[] messageConverters;

	private AssemblyService<HttpMessageConverter> assemblyService = new DefaultAssemblyService<HttpMessageConverter>();

	public void setAssemblyService(
			AssemblyService<HttpMessageConverter> assemblyService) {
		this.assemblyService = assemblyService;
	}

	public HttpMessageConverter[] getMessageConverters() {
		return messageConverters;
	}

	@SuppressWarnings("rawtypes")
	public void afterPropertiesSet() throws Exception {
		assemblyService.setApplicationContext(getApplicationContext());
		List<HttpMessageConverter> converters = assemblyService
				.findParticipants(HttpMessageConverter.class);
		if (converters == null) {
			converters = new ArrayList<HttpMessageConverter>();
		}
		Map<String, HttpMessageConverterAdapter> converterAdapters = getApplicationContext()
				.getBeansOfType(HttpMessageConverterAdapter.class);
		if (!CollectionUtil.isEmpty(converterAdapters)) {
			for (HttpMessageConverterAdapter httpMessageConverterAdapter : converterAdapters
					.values()) {
				converters.add(httpMessageConverterAdapter
						.getMessageConverter());
			}
		}
		if (CollectionUtil.isEmpty(converters)) {
			messageConverters = new HttpMessageConverter[0];
		} else {
			messageConverters = converters.toArray(new HttpMessageConverter[0]);
		}
	}

}
