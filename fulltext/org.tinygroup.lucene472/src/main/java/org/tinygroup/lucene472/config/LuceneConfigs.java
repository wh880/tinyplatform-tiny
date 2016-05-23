package org.tinygroup.lucene472.config;

import java.util.List;

import org.tinygroup.templateindex.config.BaseIndexConfig;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 全文检索配置集合
 * @author yancheng11334
 *
 */
@XStreamAlias("lucene-configs")
public class LuceneConfigs {

	@XStreamImplicit
	private List<LuceneConfig>  luceneConfigList;
	
	@XStreamImplicit
	private List<BaseIndexConfig> indexConfigList;

	public List<LuceneConfig> getLuceneConfigList() {
		return luceneConfigList;
	}

	public void setLuceneConfigList(List<LuceneConfig> luceneConfigList) {
		this.luceneConfigList = luceneConfigList;
	}

	public List<BaseIndexConfig> getIndexConfigList() {
		return indexConfigList;
	}

	public void setIndexConfigList(List<BaseIndexConfig> indexConfigList) {
		this.indexConfigList = indexConfigList;
	}

}
