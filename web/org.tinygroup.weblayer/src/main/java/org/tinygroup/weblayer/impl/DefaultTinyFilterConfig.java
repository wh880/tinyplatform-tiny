package org.tinygroup.weblayer.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.weblayer.TinyFilterConfig;
import org.tinygroup.weblayer.config.FilterMapping;
import org.tinygroup.weblayer.config.TinyFilterConfigInfo;

/**
 * 默认实现
 * 
 * @author renhui
 * 
 */
public class DefaultTinyFilterConfig extends AbstractBasicTinyConfig implements TinyFilterConfig {

	// 存放映射正则表达式列表
	private List<Pattern> patterns = new ArrayList<Pattern>();
	// 存放正则表达式的字符串格式
	private List<String> patternStrs = new ArrayList<String>();
	
	
	public DefaultTinyFilterConfig(TinyFilterConfigInfo config){
		setFilterConfig(config);
	}

	public void setFilterConfig(TinyFilterConfigInfo config) {
		clear();
		this.parameterMap = config.getParameterMap();
		List<FilterMapping> filterMappings = config.getFilterMappings();
		for (FilterMapping filterMapping : filterMappings) {
			String urlPattern = filterMapping.getUrlPattern();
			if (!patternStrs.contains(urlPattern)) {
				patterns.add(Pattern.compile(urlPattern));
				patternStrs.add(urlPattern);
			}
			logger.logMessage(LogLevel.DEBUG, "<{}>的url-pattern:'{}'",
					this.configName, urlPattern);
		}
	}


	private void clear() {
		parameterMap.clear();
		patternStrs.clear();
		patterns.clear();
	}

	public boolean isMatch(String url) {
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(url);
			if (matcher.matches()) {
				logger.logMessage(LogLevel.DEBUG,
						"请求路径：<{}>,匹配的tiny-filter:<{}>", url, configName);
				return true;
			}
		}
		return false;
	}

}
