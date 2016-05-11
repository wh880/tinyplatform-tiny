package org.tiny.seg;

import java.io.InputStream;
import java.util.List;

/**
 * 用于查找汉字的拼音
 * 
 * @author luoguo
 * 
 */
public interface PinYin {
	String PINYIN_BEAN_NAME = "pinYin";
	
	/**
	 * 
	 * @param inputStream
	 * @param encode
	 */
	void loadPinFile(InputStream inputStream,
			String encode);

	/**
	 * 返回单字的拼音
	 * 
	 * @param c
	 * @return
	 */
	List<String> getPinYin(char c);

	/**
	 * 返回字符数组的每个字的拼音
	 * 
	 * @param c
	 * @return 每个List对应一个字的多音子列表
	 */
	List<String>[] getPinYin(char[] c);

	/**
	 * 返回字符串的拼音，如果有多音字，取第一个为准
	 * 
	 * @param str
	 * @return
	 */
	List<String> getPinYin(String str);

	/**
	 * 返回词组的拼音
	 * 
	 * @param str
	 * @param pinyin
	 *            pinyin为str中每个字对应的拼音序号,从1开始
	 * @return
	 */
	List<String> getPinYin(String str, String pinyin);
}
