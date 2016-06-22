package org.tinygroup.httpvisitor.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.tinygroup.httpvisitor.Header;
import org.tinygroup.httpvisitor.struct.SimpleHeader;

/**
 * HTTP通讯的参数配置模板
 * @author yancheng11334
 *
 */
public class HttpConfigTemplate {
    
	private Map<String,Object>  clientConfigMaps = new HashMap<String,Object>();
	private Map<String,String>  headerConfigMaps = new HashMap<String,String>();
	private List<Header> headers;
	
	private String templateId;
	
	public HttpConfigTemplate(String id){
		this.templateId = id;
	}

	public String getTemplateId() {
		return templateId;
	}
	
	public Object getClientParamter(String name){
		return clientConfigMaps.get(name);
	}
	
	public Object getClientParamter(String name,Object defaultValue){
		return clientConfigMaps.containsKey(name)?clientConfigMaps.get(name):defaultValue;
	}
	
	public Set<String> getClientParamters(){
		return clientConfigMaps.keySet();
	}
	
	public void setClientParamter(String name,Object value){
		clientConfigMaps.put(name, value);
	}
	
	public String getHeaderParamter(String name){
		return headerConfigMaps.get(name);
	}
	
	public String getHeaderParamter(String name,String defaultValue){
		return headerConfigMaps.containsKey(name)?headerConfigMaps.get(name):defaultValue;
	}
	
	public List<Header> getHeaderParamters(){
		if(headers==null){
			headers = new ArrayList<Header>();
			for(Entry<String, String> entry:headerConfigMaps.entrySet()){
				headers.add(new SimpleHeader(entry.getKey(),entry.getValue()));
			}
		}
		return headers;
	}
	
	public void setHeaderParamter(String name,String value){
		headerConfigMaps.put(name, value);
	}

	public String toString() {
		return "HttpConfigTemplate [clientConfigMaps=" + clientConfigMaps
				+ ", headerConfigMaps=" + headerConfigMaps + ", templateId="
				+ templateId + "]";
	}
	
}
