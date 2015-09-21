package org.tinygroup.springmvc.support;

import org.springframework.http.converter.HttpMessageConverter;

/**
 * HttpMessageConverter接口的适配类
 * @author renhui
 *
 */
public class HttpMessageConverterAdapter {

	private HttpMessageConverter messageConverter;

	public HttpMessageConverter getMessageConverter() {
		return messageConverter;
	}

	public void setMessageConverter(HttpMessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}
	
}
