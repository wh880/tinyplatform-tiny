package org.tiny.seg;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 中文分词<br>
 * 
 * @author luoguo
 * 
 */
public interface ChineseParser {
	
	String CHINESE_PARSER_BEAN_NAME="chineseParser";

	/**
	 * 对内容进行分词，最大优先，此方法由于用了统计，因此效率较低
	 * 
	 * @param content
	 * @return 分的词及个数的键值对
	 */
	void segmentWordMax(String content, Map<String, Integer> result);

	/**
	 * 对内容进行分词，最小优先，此方法由于用了统计，因此效率较低
	 * @param content
	 * @param result 分的词及个数的键值对
	 */
	void segmentWordMin(String content, Map<String, Integer> result);

	/**
	 * 对内容进行分词，最大优先
	 * @param content
	 * @param result
	 */
	void segmentWordMax(String content, List<String> result);

	/**
	 * 对内容进行分词，最小优先
	 * @param content
	 * @param result
	 */
	void segmentWordMin(String content, List<String> result);

	/**
	 * 设置找词时的事件
	 * 
	 * @param event
	 */

	void setFoundEvent(FoundEvent event);

	/**
	 * 加载词库
	 * @param inputStream
	 * @param encode
	 */
	void loadDict(InputStream inputStream,
			String encode);
}
