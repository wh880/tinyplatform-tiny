package org.tinygroup.httpvisitor.manager;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.httpvisitor.config.HttpConfigTemplate;

/**
 * 默认的HTTP参数模板管理器
 * @author yancheng11334
 *
 */
public class DefaultHttpTemplateManager implements HttpTemplateManager{
	
    private Map<String,HttpConfigTemplate> templateMaps = new HashMap<String,HttpConfigTemplate>();
    
	public HttpConfigTemplate getHttpConfigTemplate(String templateId) {
		return templateMaps.get(templateId);
	}

	public void addHttpConfigTemplate(HttpConfigTemplate template) {
		templateMaps.put(template.getTemplateId(), template);
	}

	public void removeHttpConfigTemplate(String templateId) {
		templateMaps.remove(templateId);
	}

}
