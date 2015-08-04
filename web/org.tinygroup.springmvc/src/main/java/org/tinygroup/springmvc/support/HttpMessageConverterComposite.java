package org.tinygroup.springmvc.support;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.tinygroup.assembly.AssemblyService;
import org.tinygroup.assembly.DefaultAssemblyService;
import org.tinygroup.commons.tools.CollectionUtil;

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
		if (CollectionUtil.isEmpty(converters)) {
			messageConverters = new HttpMessageConverter[0];
		} else {
			messageConverters = converters.toArray(new HttpMessageConverter[0]);
		}
	}

}
