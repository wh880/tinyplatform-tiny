package org.tinygroup.httpvisitor.config;

import java.util.Map;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;

/**
 * 基于HTTP通讯配置的上下文
 * 
 * @author yancheng11334
 *
 */
public class HttpConfigTemplateContext extends ContextImpl implements Context{

	/**
	 * 
	 */
	private static final long serialVersionUID = 869870712419826376L;
	private HttpConfigTemplate template;

	public HttpConfigTemplateContext(HttpConfigTemplate httpConfigTemplate){
		this.template = httpConfigTemplate;
	}
	
	@SuppressWarnings("rawtypes")
	public HttpConfigTemplateContext(Map map,HttpConfigTemplate httpConfigTemplate){
		super(map);
		this.template = httpConfigTemplate;
	}
	
	public HttpConfigTemplate getTemplate() {
		return template;
	}
	
	public boolean exist(String name) {
		boolean tag = super.exist(name);
		return tag?true:existInTemplate(name);
	}
	
	private boolean existInTemplate(String name){
		return template.getClientParamter(name)!=null || template.getHeaderParamter(name)!=null;
	}
	
	/**
	 * 查询元素顺序:context的itemMap最优先查询;其次是查询template的client元素;最后查询template的header元素
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		T t = super.get(name);
		return t!=null?t:(T)getFromTemplate(name);
	}
	
	private Object getFromTemplate(String name){
	    Object object = template.getClientParamter(name);
		return object!=null?object:template.getHeaderParamter(name);
	}

}
