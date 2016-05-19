package org.tinygroup.templateindex;

import java.util.List;

import org.tinygroup.fulltext.document.Document;

/**
 * 具体全文检索配置的执行器
 * @author yancheng11334
 *
 * @param <Config>
 */
public interface TemplateIndexOperator<Config> {

	List<Document> createDocuments(Config config) throws Exception ;
}
