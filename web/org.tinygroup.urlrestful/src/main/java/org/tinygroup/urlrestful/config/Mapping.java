package org.tinygroup.urlrestful.config;

import org.tinygroup.commons.tools.StringUtil;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


/**
 * @author renhui
 *
 */
@XStreamAlias("mapping")
public class Mapping {

	public static final String TEXT_HTML = "text/html";

	@XStreamAsAttribute
	@XStreamAlias("url")
	private String url;
	@XStreamAsAttribute
	@XStreamAlias("method")
	private String method;
	@XStreamAsAttribute
	private String accept;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
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
