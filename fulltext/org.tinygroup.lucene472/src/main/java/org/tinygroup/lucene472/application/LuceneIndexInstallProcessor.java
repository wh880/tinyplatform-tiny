package org.tinygroup.lucene472.application;

import java.util.List;

import org.tinygroup.application.AbstractApplicationProcessor;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.lucene472.LuceneConfigManager;
import org.tinygroup.lucene472.LuceneIndexOperator;
import org.tinygroup.templateindex.TemplateIndexOperator;
import org.tinygroup.templateindex.config.BaseIndexConfig;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * Lucene索引应用安装处理器
 * 
 * @author yancheng11334
 * 
 */
public class LuceneIndexInstallProcessor extends AbstractApplicationProcessor {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LuceneIndexInstallProcessor.class);

	private LuceneConfigManager luceneConfigManager;
	private LuceneIndexOperator luceneIndexOperator;

	public LuceneConfigManager getLuceneConfigManager() {
		return luceneConfigManager;
	}

	public void setLuceneConfigManager(LuceneConfigManager luceneConfigManager) {
		this.luceneConfigManager = luceneConfigManager;
	}

	public LuceneIndexOperator getLuceneIndexOperator() {
		return luceneIndexOperator;
	}

	public void setLuceneIndexOperator(LuceneIndexOperator luceneIndexOperator) {
		this.luceneIndexOperator = luceneIndexOperator;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void start() {
		LOGGER.logMessage(LogLevel.INFO, "LuceneIndexInstallProcessor开始安装索引...");
		List<BaseIndexConfig> indexConfigList = luceneConfigManager
				.getIndexConfigList();
		try {
			if (indexConfigList != null) {
				for (BaseIndexConfig config : indexConfigList) {
					LOGGER.logMessage(LogLevel.INFO,"{0}开始执行安装数据源...",config.getBeanName());
					TemplateIndexOperator operator = config
							.getTemplateIndexOperator();
					List<Document> documents = operator.createDocuments(config);
					int size = documents==null?0:documents.size();
					luceneIndexOperator.createIndex(documents);
					LOGGER.logMessage(LogLevel.INFO,"{0}执行安装数据源结束,安装{1}篇文档",config.getBeanName(),size);
				}
			}
		} catch (Exception e) {
			LOGGER.errorMessage("处理索引数据源发生异常", e);
		}

		LOGGER.logMessage(LogLevel.INFO, "LuceneIndexInstallProcessor安装索引完成!");
	}

	public void stop() {

	}

	public String getApplicationNodePath() {
		return null;
	}

	public String getComponentConfigPath() {
		return null;
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {

	}

	public XmlNode getComponentConfig() {
		return null;
	}

	public XmlNode getApplicationConfig() {
		return null;
	}

	public int getOrder() {
		return 0;
	}

}
