package org.tinygroup.weixin.impl;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.TemplateRender;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateRenderDefault;
import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinManager;
import org.tinygroup.weixin.common.UrlConfig;
import org.tinygroup.weixin.common.UrlConfigs;
import org.tinygroup.weixin.exception.WeiXinException;

public class WeiXinManagerDefault implements WeiXinManager {

	private Map<String, UrlConfig> urlConfigMap = new HashMap<String, UrlConfig>();
    private TemplateRender templateRender = new TemplateRenderDefault();

	public UrlConfig getUrl(String key) {
		return urlConfigMap.get(key);
	}

	public void addUrlConfig(UrlConfig urlConfig) {
		urlConfigMap.put(urlConfig.getKey(), urlConfig);
	}

	public void addUrlConfigs(UrlConfigs urlConfigs) {
		for (UrlConfig urlConfig : urlConfigs.getUrlConfigs()) {
			addUrlConfig(urlConfig);
		}
	}

	public void removeUrlConfig(UrlConfig urlConfig) {
		urlConfigMap.remove(urlConfig.getKey());
	}

	public void removeUrlConfigs(UrlConfigs urlConfigs) {
		for (UrlConfig urlConfig : urlConfigs.getUrlConfigs()) {
			removeUrlConfig(urlConfig);
		}
	}

	public String renderUrl(String key, WeiXinContext context) {
		UrlConfig urlConfig = getUrl(key);
		TemplateContext templateContext = new TemplateContextDefault();
		templateContext.setParent(context);

		Object input = context.getInput();
		// 默认策略：将对象以key值为名称放到上下文环境
		templateContext.put(urlConfig.getKey(), input);

		String result;
		try {
			result = templateRender.renderTemplateContent(urlConfig.getUrl(), templateContext);
		} catch (TemplateException e) {
			throw new WeiXinException(e);
		}finally{
			templateContext.setParent(null);
		}
		
		return result;

	}
}
