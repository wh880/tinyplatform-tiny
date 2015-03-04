package org.tinygroup.urlrestful.config;

import org.tinygroup.commons.tools.StringUtil;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


/**
 * @author renhui
 *
 */
@XStreamAlias("url-mapping")
public class UrlMapping {

	public static final String TEXT_HTML = "text/html";

	@XStreamAsAttribute
	@XStreamAlias("mapping-url")
	private String mappingUrl;
	@XStreamAsAttribute
	@XStreamAlias("http-method")
	private String httpMethod;
	@XStreamAsAttribute
	private String accept;

	public String getMappingUrl() {
		return mappingUrl;
	}

	public void setMappingUrl(String mappingUrl) {
		this.mappingUrl = mappingUrl;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getAccept() {
		if(StringUtil.isBlank(accept)){
			accept=TEXT_HTML;
		}
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}
	
}
