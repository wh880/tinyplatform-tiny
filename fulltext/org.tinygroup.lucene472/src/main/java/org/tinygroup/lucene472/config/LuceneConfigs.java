package org.tinygroup.lucene472.config;

import java.util.List;

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

	public List<LuceneConfig> getLuceneConfigList() {
		return luceneConfigList;
	}

	public void setLuceneConfigList(List<LuceneConfig> luceneConfigList) {
		this.luceneConfigList = luceneConfigList;
	}
	
	
}
