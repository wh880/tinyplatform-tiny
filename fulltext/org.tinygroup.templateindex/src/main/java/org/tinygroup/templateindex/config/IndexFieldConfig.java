package org.tinygroup.templateindex.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 索引字段模板定义
 * 
 * @author yancheng11334
 * 
 */
@XStreamAlias("field")
public class IndexFieldConfig {

	@XStreamAlias("index-name")
	@XStreamAsAttribute
	private String indexName;

	@XStreamAlias("template-rule")
	private String templateRule;

	@XStreamAsAttribute
	private String stored;

	@XStreamAsAttribute
	private String indexed;

	@XStreamAsAttribute
	private String tokenized;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getTemplateRule() {
		return templateRule;
	}

	public void setTemplateRule(String templateRule) {
		this.templateRule = templateRule;
	}
    
	public String getStored() {
		return stored;
	}

	public void setStored(String stored) {
		this.stored = stored;
	}

	public String getIndexed() {
		return indexed;
	}

	public void setIndexed(String indexed) {
		this.indexed = indexed;
	}

	public String getTokenized() {
		return tokenized;
	}

	public void setTokenized(String tokenized) {
		this.tokenized = tokenized;
	}

	public String toString() {
		return "IndexFieldConfig [indexName=" + indexName + ", templateRule="
				+ templateRule + ", stored=" + stored + ", indexed=" + indexed
				+ ", tokenized=" + tokenized + "]";
	}
	
}
