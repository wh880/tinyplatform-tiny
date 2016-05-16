package org.tinygroup.lucene472.manage;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.fulltext.FullTextConfigManager;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.lucene472.LuceneConfigManager;
import org.tinygroup.lucene472.config.LuceneConfig;
import org.tinygroup.lucene472.config.LuceneConfigs;

/**
 * Lucene配置管理器的具体实现
 * 
 * @author yancheng11334
 * 
 */
public class LuceneConfigManagerImpl implements LuceneConfigManager {

	private Map<String, LuceneConfig> configMaps = new HashMap<String, LuceneConfig>();

	public void addLuceneConfigs(LuceneConfigs configs) {
		if (configs != null && configs.getLuceneConfigList() != null) {
			for (LuceneConfig config : configs.getLuceneConfigList()) {
				addFullTextConfig(config);
			}
		}
	}

	public void removeLuceneConfigs(LuceneConfigs configs) {
		if (configs != null && configs.getLuceneConfigList() != null) {
			for (LuceneConfig config : configs.getLuceneConfigList()) {
				removeFullTextConfig(config);
			}
		}
	}

	public void addFullTextConfig(LuceneConfig config) {
		if (config != null) {
			configMaps.put(config.getId(), config);
		}
	}

	public void removeFullTextConfig(LuceneConfig config) {
		if (config != null) {
			configMaps.remove(config.getId());
		}
	}

	public LuceneConfig getFullTextConfig(String configId) {
		return configId == null ? null : configMaps.get(configId);
	}

	public LuceneConfig getFullTextConfig() {
		String configId = ConfigurationUtil.getConfigurationManager()
				.getConfiguration(FullTextConfigManager.FULLTEXT_CONFIG_ID);
		if(StringUtil.isEmpty(configId)){
		   throw new FullTextException(String.format("读取默认的全文检索配置项失败:configId为空!请检查全局配置参数%s是否设置.", FullTextConfigManager.FULLTEXT_CONFIG_ID));
		}
		LuceneConfig config = getFullTextConfig(configId);
		if(config==null){
		   throw new FullTextException(String.format("读取默认的全文检索配置项失败:configId=%s,配置项为空!请检查*.luceneconfig.xml是否配置该条配置项",configId));
		}
		return config;
	}

}
