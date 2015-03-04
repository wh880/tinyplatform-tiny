package org.tinygroup.urlrestful.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.tinygroup.commons.tools.CollectionUtil;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 保存配置信息的对象
 * 
 * @author renhui
 * 
 */
@XStreamAlias("url-restful")
public class UrlRestful {
	@XStreamAsAttribute
	private String pattern;

	@XStreamImplicit
	private List<UrlMapping> urlMappings;

	private transient Map<String, List<UrlMapping>> method2Mapping;

	public UrlRestful(String pattern) {
		this.pattern = pattern;
	}

	private void addUrlMapping(UrlMapping urlMapping) {
		List<UrlMapping> mappings = method2Mapping.get(urlMapping
				.getHttpMethod());
		if (mappings == null) {
			mappings = new ArrayList<UrlMapping>();
			method2Mapping.put(urlMapping.getHttpMethod(), mappings);
		}
		mappings.add(urlMapping);
	}

	public String getPattern() {
		return pattern;
	}

	public List<UrlMapping> getUrlMappingsByMethod(String method) {
		if(method2Mapping==null){
			init();
		}
		return method2Mapping.get(method);
	}

	public List<UrlMapping> getUrlMappings() {
		if (urlMappings == null) {
			urlMappings = new ArrayList<UrlMapping>();
		}
		return urlMappings;
	}

	public void setUrlMappings(List<UrlMapping> urlMappings) {
		this.urlMappings = urlMappings;
	}

	@SuppressWarnings("unchecked")
	public void init(){
		method2Mapping=new CaseInsensitiveMap(); 
		if (!CollectionUtil.isEmpty(urlMappings)) {
			for (UrlMapping urlMapping : urlMappings) {
                   addUrlMapping(urlMapping);
			}
		}
	}
	
	
	@Override
	public int hashCode() {
		return pattern.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj) {
			return true;
		}
		if (obj instanceof UrlRestful) {
			UrlRestful other = (UrlRestful) obj;
			return other.pattern.equals(this.pattern);
		}
		return false;
	}

}
