package org.tinygroup.indexinstaller.impl;

import org.tinygroup.fulltext.FullText;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

/**
 * 抽象索引数据源基类
 * @author yancheng11334
 *
 * @param <Config>
 */
public abstract class AbstractIndexDataSource {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractIndexDataSource.class);
	
	private FullText fullText;
	
	public FullText getFullText() {
		return fullText;
	}

	public void setFullText(FullText fullText) {
		this.fullText = fullText;
	}

}
