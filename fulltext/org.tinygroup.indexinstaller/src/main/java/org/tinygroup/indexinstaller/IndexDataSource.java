package org.tinygroup.indexinstaller;

import org.tinygroup.fulltext.FullText;


/**
 * 索引数据来源
 * @author yancheng11334
 *
 */
public interface IndexDataSource<Config>{
	
	String getType();
	
	/**
	 * 执行安装操作
	 * @param config
	 */
	void install(Config config);
	
	
	FullText getFullText();
	
	void setFullText(FullText fullText);
}
