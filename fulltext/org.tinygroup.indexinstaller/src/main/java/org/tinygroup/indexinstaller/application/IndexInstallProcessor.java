package org.tinygroup.indexinstaller.application;

import java.util.List;

import org.tinygroup.application.AbstractApplicationProcessor;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.fulltext.FullText;
import org.tinygroup.fulltext.FullTextHelper;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.indexinstaller.IndexDataSource;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * 执行安装索引的操作
 * @author yancheng11334
 *
 */
public class IndexInstallProcessor extends AbstractApplicationProcessor{

	private XmlNode applicationConfig;
	private XmlNode componentConfig;
	
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexInstallProcessor.class);
	/**
	 * 索引数据源
	 */
	private List<IndexDataSource<XmlNode>> dataSources;

	public List<IndexDataSource<XmlNode>> getDataSources() {
		return dataSources;
	}

	public void setDataSources(List<IndexDataSource<XmlNode>> dataSources) {
		this.dataSources = dataSources;
	}

	public void start() {
		LOGGER.logMessage(LogLevel.INFO, "开始安装索引数据源...");
		XmlNode totalNode  = ConfigurationUtil.combineXmlNode(applicationConfig, componentConfig);
		List<XmlNode> sourceNodes = totalNode.getSubNodes("index-source");
		
		//实例化FullText
		FullText fullText = FullTextHelper.getFullText();
		
		//是否允许单节点异常
		boolean allowError = Boolean.parseBoolean(StringUtil.defaultIfEmpty(totalNode.getAttribute("allowError"), "false"));
		
		for(XmlNode node:sourceNodes){
			try{
				IndexDataSource<XmlNode> dataSource = getIndexDataSource(node.getAttribute("type"));
				dataSource.setFullText(fullText);
				dataSource.install(node);
			}catch(FullTextException e){
				if(allowError){
					//记录错误日志继续
                	LOGGER.logMessage(LogLevel.WARN, "安装索引节点发生异常:"+e.getMessage());
                	continue;
                }else{
                	//抛出异常
                	throw e;
                }
			}catch(Exception e){
                if(allowError){
                	//记录错误日志继续
                	LOGGER.logMessage(LogLevel.WARN, "安装索引节点发生异常:"+e.getMessage());
                	continue;
                }else{
                	//抛出异常
                	throw new FullTextException(e);
                }
			}
			
		}
		LOGGER.logMessage(LogLevel.INFO, "安装索引数据源结束!");
	}

	public void stop() {
		
	}
	
	private IndexDataSource<XmlNode> getIndexDataSource(String type){
		for(IndexDataSource<XmlNode> indexDataSource:dataSources){
			if(indexDataSource.getType().equals(type)){
			   return indexDataSource;
			}
		}
		throw new FullTextException(String.format("类型[%s]的索引数据源配置没有找到对应的IndexDataSource", type));
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
		this.applicationConfig = applicationConfig;
		this.componentConfig = componentConfig;
	}

	public XmlNode getApplicationConfig() {
		return applicationConfig;
	}

	public String getApplicationNodePath() {
		return "/application/index-installer";
	}

	public XmlNode getComponentConfig() {
		return componentConfig;
	}

	public String getComponentConfigPath() {
		return "indexinstaller.config.xml";
	}

	public int getOrder() {
		return 0;
	}

}
