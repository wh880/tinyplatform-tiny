package org.tinygroup.httpvisitor.manager;

import org.tinygroup.httpvisitor.config.HttpConfigTemplate;

/**
 * HTTP配置模板管理器
 * @author yancheng11334
 *
 */
public interface HttpTemplateManager {

	public HttpConfigTemplate getHttpConfigTemplate(String templateId);
	
	public void addHttpConfigTemplate(HttpConfigTemplate template);
	
	public void removeHttpConfigTemplate(String templateId);
}
