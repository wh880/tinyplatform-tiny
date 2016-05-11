package org.tinygroup.lucene472.builder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.fulltext.FullTextConfigManager;
import org.tinygroup.fulltext.FullTextHelper;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.fulltext.field.Field;
import org.tinygroup.fulltext.field.StoreField;
import org.tinygroup.lucene472.LuceneConfigManager;
import org.tinygroup.lucene472.config.LuceneConfig;

/**
 * 构建Lucene的相关对象
 * 
 * @author yancheng11334
 * 
 */
public class LuceneBuilder {

	private IndexableFieldBuilder indexableFieldBuilder = new IndexableFieldBuilder();

	private LuceneConfigManager luceneConfigManager = null;

	private BeanContainer<?> beanContainer = null;

	public LuceneBuilder() {
		super();
		beanContainer = BeanContainerFactory.getBeanContainer(this.getClass()
				.getClassLoader());
	}

	public LuceneConfigManager getLuceneConfigManager() {
		if (luceneConfigManager == null) {
			luceneConfigManager = beanContainer.getBean(getBeanName());
		}
		return luceneConfigManager;
	}

	private String getBeanName() {
		// 先加载全局配置的配置参数
		String beanName = ConfigurationUtil
				.getConfigurationManager()
				.getConfiguration(FullTextConfigManager.FULLTEXT_CONFIG_MANAGER);
		return beanName == null ? LuceneConfigManager.DEFAULT_BEAN_NAME
				: beanName;
	}

	public void setLuceneConfigManager(LuceneConfigManager luceneConfigManager) {
		this.luceneConfigManager = luceneConfigManager;
	}
	
	public LuceneConfig buildConfig(){
		return getLuceneConfigManager().getFullTextConfig();
	}
	
	public String getPerfix(){
		return StringUtil.defaultIfEmpty(buildConfig().getHighLightPrefix(), "@LUCENE_PERFIX");
	}
	
	public String getSuffix(){
		return StringUtil.defaultIfEmpty(buildConfig().getHighLightSuffix(), "@LUCENE_SUFFIX");
	}
	
	/**
	 * 构建org.apache.lucene.document.Document对象
	 * 
	 * @param doc
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public org.apache.lucene.document.Document buildDocument(Document doc) {
		org.apache.lucene.document.Document document = new org.apache.lucene.document.Document();
		Iterator<Field> it = doc.iterator();
		LuceneConfig config = buildConfig();
		boolean tag = config.isFilterHtml();
		while (it.hasNext()) {
			Field field = it.next();
			if(field instanceof StoreField){
				document.add(indexableFieldBuilder.build((StoreField)field,tag));
			}else{
				throw new FullTextException(String.format("ID为[%s]的文档存在非StoreField的字段,该字段为[%s]", doc.getId().getValue(),field.getName()));
			}
			
		}
		return document;
	}

	/**
	 * 构建org.apache.lucene.store.Directory对象
	 * 
	 * @return
	 */
	public org.apache.lucene.store.Directory buildDirectory() {
		LuceneConfig config = buildConfig();
		try {
			return FSDirectory.open(new File(config.getDirectory()));
		} catch (IOException e) {
			throw new FullTextException(String.format(
					"创建索引目录[%s]的FSDirectory失败:", config.getDirectory()), e);
		}
	}

	/**
	 * 构建org.apache.lucene.index.IndexWriterConfig对象
	 * 
	 * @return
	 */
	public org.apache.lucene.index.IndexWriterConfig buildIndexWriterConfig() {
		LuceneConfig config = buildConfig();
		Version version = buildVersion(config);
		Analyzer analyzer = buildAnalyzer(config,version);
		return new org.apache.lucene.index.IndexWriterConfig(version, analyzer);
	}
	
	/**
	 * 构建搜索字段域
	 * @return
	 */
	public String[] buildQueryFields(){
		LuceneConfig config = buildConfig();
		List<String> fields = new ArrayList<String>();
		fields.add(FullTextHelper.getStoreId());
		fields.add(FullTextHelper.getStoreType());
		fields.add(FullTextHelper.getStoreTitle());
		fields.add(FullTextHelper.getStoreAbstract());
		
		if(!StringUtil.isEmpty(config.getSearchFields())){
		   if(config.getSearchFields().indexOf(",")>-1){
			   String[] ss = config.getSearchFields().trim().split(",");
			   for(String s:ss){
				  if(!fields.contains(s)){
					  fields.add(s);
				  }
			   }
		   }else{
			   String s = config.getSearchFields().trim();
			   if(!fields.contains(s)){
					  fields.add(s);
				}
		   }
		}
		
		return fields.toArray(new String[fields.size()]);
	}
	
	public Set<String> buildQuerySet(){
		Set<String> sets = new HashSet<String>();
		String[] fields = buildQueryFields();
		for(String field:fields){
			sets.add(field);
		}
		return sets;
	}
	/**
	 * 构建org.apache.lucene.queryparser.classic.QueryParser对象
	 * @param type
	 * @return
	 */
	public org.apache.lucene.queryparser.classic.QueryParser buildQueryParser(){
		LuceneConfig config = buildConfig();
		Version version = buildVersion(config);
		Analyzer analyzer = buildAnalyzer(config,version);
		
        String[] fields = buildQueryFields();
		return new org.apache.lucene.queryparser.classic.MultiFieldQueryParser(version,fields,analyzer);
	}
	
	/**
	 * 构建org.apache.lucene.search.highlight.Highlighter对象
	 * @return
	 */
	public org.apache.lucene.search.highlight.Highlighter buildHighlighter(Query query){
		String perfix = getPerfix();
		String suffix = getSuffix();
		SimpleHTMLFormatter formatter=new SimpleHTMLFormatter(perfix,suffix);
		Highlighter highlighter=new Highlighter(formatter, new QueryScorer(query));
		return highlighter;
	}
	
	public Version buildVersion(){
		return buildVersion(buildConfig());
	}
	
	@SuppressWarnings("deprecation")
	private Version buildVersion(LuceneConfig config){
		return StringUtil.isEmpty(config.getIndexVersion()) ? Version.LUCENE_CURRENT
				: Version.valueOf(config.getIndexVersion());
	}
	
	public Analyzer buildAnalyzer(){
		LuceneConfig config = buildConfig();
		Version version = buildVersion(config);
		return buildAnalyzer(config,version);
	}
	
	private Analyzer buildAnalyzer(LuceneConfig config,Version version){
		return StringUtil.isEmpty(config.getAnalyzerBeanName()) ? new StandardAnalyzer(version)
		: (Analyzer) beanContainer
		.getBean(config.getAnalyzerBeanName());
	}

}
