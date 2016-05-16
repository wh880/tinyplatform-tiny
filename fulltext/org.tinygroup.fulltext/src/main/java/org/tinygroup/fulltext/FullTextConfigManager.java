package org.tinygroup.fulltext;

/**
 * 全局索引配置的管理器接口
 * @author yancheng11334
 *
 * @param <T>
 */
public interface FullTextConfigManager<T> {

	/**
	 * application.xml的全局配置参数名:<br>
	 * 参数值:全文检索的配置管理器bean名称
	 */
	public static final String FULLTEXT_CONFIG_MANAGER = "FULLTEXT_CONFIG_MANAGER";
	
	/**
	 * application.xml的全局配置参数名:<br>
	 * 参数值:全文检索的配置唯一ID
	 */
	public static final String FULLTEXT_CONFIG_ID = "FULLTEXT_CONFIG_ID";
	
	/**
	 * 添加单条配置
	 * @param config
	 */
	public void addFullTextConfig(T config);
	
	/**
	 * 删除单条配置
	 * @param config
	 */
	public void removeFullTextConfig(T config);
	
	/**
	 * 得到配置信息
	 * @param configId
	 * @return
	 */
	public T getFullTextConfig(String configId);
	
	/**
	 * 得到默认的配置信息
	 * @return
	 */
	public T getFullTextConfig();
}
