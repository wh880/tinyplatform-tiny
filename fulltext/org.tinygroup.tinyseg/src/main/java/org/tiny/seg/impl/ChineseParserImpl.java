package org.tiny.seg.impl;

import org.apache.commons.lang.CharUtils;
import org.tiny.seg.ChineseParser;
import org.tiny.seg.FoundEvent;
import org.tiny.seg.exception.DictLoadRuntimeException;
import org.tinygroup.binarytree.AVLTree;
import org.tinygroup.binarytree.impl.AVLTreeImpl;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO 增加多种情景词库加载方式，通过情景词库，可以提升匹配的正确率
//TODO 重构实现
/**
 * 中文分词
 * 
 * @author luoguo
 * 
 */
public class ChineseParserImpl implements ChineseParser {
	private static final int BOM = 65279;
	private static final int MODE_MAX = 1;
	private static final int MODE_MIN = -1;
	private static Pattern pattern = Pattern
			.compile("[a-z|A-Z]+|[0-9]+|[\u4e00-\u9fa5]+");
	
	private AVLTree<WordDescription> letterIndex = new AVLTreeImpl<WordDescription>();// 词库索引
	private FoundEvent findEvent = null;

	public ChineseParserImpl() {

	}
	
	public AVLTree<WordDescription> getLetterIndex() {
		return letterIndex;
	}

	public void setLetterIndex(AVLTree<WordDescription> letterIndex) {
		this.letterIndex = letterIndex;
	}



	public  void loadDict(InputStream inputStream,
			String encode) {
		try {
			XmlStringParser parser = new XmlStringParser();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					inputStream, encode));
			String str;
			while ((str = in.readLine()) != null) {
				boolean notBomChar = !(str.length() == 1 && (int) str.charAt(0) == BOM);
				boolean validLine = str.length() > 0 && !str.startsWith("#");
				if (notBomChar && validLine) {
					addWord(parser.parse(str).getRoot());
				}
			}
			in.close();
		} catch (Exception e) {
			throw new DictLoadRuntimeException(e);
		}
	}

	private  void addWord(XmlNode node) {
		addWord(null, node.getAttribute("word"), 0, node);
	}

	/**
	 * 添加一个字
	 * 
	 * @param wordNode
	 */
	private  void addWord(WordDescription workdesc, String word,
			int index, XmlNode wordNode) {
		char c = 0;
		if (index < word.length()) {
			c = word.charAt(index);
		} else if (index == word.length()) {
			c = 0;
		} else {
			return;
		}
		WordDescription wordDescription = new WordDescription(c);
		if (c == 0) {
			// 添加词组相关信息
			wordDescription.setProperties(wordNode);
		}
		WordDescription newWordDescription = null;
		if (workdesc == null) {
			newWordDescription = letterIndex.contains(wordDescription);
		} else {
			if (workdesc.getWord() == null) {
				AVLTree<WordDescription> newWord = new AVLTreeImpl<WordDescription>();
				workdesc.setWord(newWord);
			} else {
				newWordDescription = workdesc.getWord().contains(
						wordDescription);
			}
		}
		if (newWordDescription != null) {
			wordDescription = newWordDescription;
		} else {
			if (workdesc == null) {
				letterIndex.add(wordDescription);
			} else {
				workdesc.getWord().add(wordDescription);
			}
		}
		addWord(wordDescription, word, index + 1, wordNode);
	}

	public void segmentWordMax(String content, List<String> result) {
		segmentWord(content, result, MODE_MAX);
	}

	private void segmentWord(String content, List<String> result, int mode) {
		Matcher matcher = pattern.matcher(content);
		int start = 0;
		while (matcher.find(start)) {
			String str = matcher.group();
			char c = str.charAt(0);
			if (CharUtils.isAsciiAlphanumeric(c)) {
				addToken(str, result);
			} else {
				// 分解中文
				int s = parseWord(str, 0, result, mode);
				while (s < str.length()) {
					s = parseWord(str, s, result, mode);
				}
			}
			start = matcher.end();
		}
	}

	private int parseWord(String str, int s, List<String> result, int mode) {
		if (mode == MODE_MAX) {
			return parseWordMax(str, s, result);
		} else {
			return parseWordMin(str, s, result);
		}
	}

	public void segmentWordMin(String content, List<String> result) {
		segmentWord(content, result, MODE_MIN);
	}

	private int parseWord(String str, int s, Map<String, Integer> ret, int mode) {
		if (mode == MODE_MAX) {
			return parseWordMax(str, s, ret);
		} else {
			return parseWordMin(str, s, ret);
		}
	}

	public void segmentWordMax(String content, Map<String, Integer> ret,
			int mode) {
		Matcher matcher = pattern.matcher(content);
		int start = 0;
		while (matcher.find(start)) {
			String str = matcher.group();
			char c = str.charAt(0);
			if (CharUtils.isAsciiAlphanumeric(c)) {
				addToken(str, ret);
			} else {
				// 分解中文
				int s = parseWord(str, 0, ret, mode);
				while (s < str.length()) {
					s = parseWord(str, s, ret, mode);
				}
			}
			start = matcher.end();
		}
	}

	public void segmentWordMax(String content, Map<String, Integer> ret) {
		segmentWordMax(content, ret, MODE_MAX);
	}

	public void segmentWordMin(String content, Map<String, Integer> ret) {
		segmentWordMax(content, ret, MODE_MIN);
	}

	/**
	 * 给找到的标记添加记数
	 * 
	 * @param token
	 * @param ret
	 */
	private void addTokenToMap(String token, Map<String, Integer> ret) {
		if (findEvent != null) {
			findEvent.process(token);
		}
		Integer i = ret.get(token);
		if (i == null) {
			i = 1;
		} else {
			i++;
		}
		ret.put(token, i);
	}

	@SuppressWarnings("unchecked")
	private <T> void addToken(String token, T ret) {
		if (ret instanceof Map) {
			Map<String, Integer> map = (Map<String, Integer>) ret;
			addTokenToMap(token, map);
		} else if (ret instanceof List) {
			List<String> list = (List<String>) ret;
			addTokenToList(token, list);
		}

	}

	private void addTokenToList(String token, List<String> ret) {
		if (findEvent != null) {
			findEvent.process(token);
		}

		ret.add(token);
	}

	/**
	 * 分词 <br>
	 * a.实现了顺序分词<br>
	 * b.实现了如果一次分解有多个词出现，者增加到结果列表中<br>
	 * c.姓名识别
	 * 
	 * @param str
	 *            要分的句子
	 * @param start
	 *            开始的位置
	 * @param ret
	 *            结果存放
	 * @return 结束的位置
	 */
	private <T> int parseWordMax(String str, int start, T ret) {
		char c = str.charAt(start);
		int end = start + 1;
		WordDescription find = new WordDescription(c);
		WordDescription endChar = new WordDescription('\0');
		WordDescription locate = letterIndex.contains(find);
		int ct = 0;

		while (locate != null) {
			ct++;
			WordDescription kk = locate.getWord().contains(endChar);
			if (kk != null) {
				// 增加动态检查，如果其它的方案，比当前方案更优，则取新方案
				if (end - start >= 2) {
					for (int index = start + 1; index <= end; index++) {
						WordDescription wd = findOneWord(str, index);
						if (wd != null && wd.getWeighing() > kk.getWeighing()) {// 如果没有找到词且找到的词比前一个大
							parseWordMax(str.substring(start, index), 0, ret);// 前面的字再进行处理
							return index;
						}
					}
				}
				end = start + ct;
			}
			if (start + ct == str.length()) {
				break;
			} else {
				c = str.charAt(start + ct);
				find.setChar(c);
				locate = locate.getWord().contains(find);
			}
		}
		// 如果是单字
		addToken(str.substring(start, end), ret);
		return end;
	}

	private <T> int parseWordMin(String str, int start, T ret) {
		char c = str.charAt(start);
		int end = start + 1;
		WordDescription find = new WordDescription(c);
		WordDescription endChar = new WordDescription('\0');
		WordDescription locate = letterIndex.contains(find);
		int ct = 0;

		while (locate != null) {
			ct++;
			WordDescription kk = locate.getWord().contains(endChar);
			if (kk != null) {
				// 增加动态检查，如果其它的方案，比当前方案更优，则取新方案
				if (end - start >= 2) {
					for (int index = start + 1; index <= end; index++) {
						WordDescription wd = findOneWord(str, index);
						if (wd != null && wd.getWeighing() > kk.getWeighing()) {// 如果没有找到词且找到的词比前一个大
							parseWordMin(str.substring(start, index), 0, ret);// 前面的字再进行处理
							return index;
						}
					}
				}
				end = start + ct;
				addToken(str.substring(start, end), ret);
				return end;
			}
			if (start + ct == str.length()) {
				break;
			} else {
				c = str.charAt(start + ct);
				find.setChar(c);
				locate = locate.getWord().contains(find);
			}
		}
		// 如果是单字
		if (end - start == 1) {
			addToken(str.substring(start, end), ret);
		}
		return end;
	}

	/**
	 * 只查找一个词
	 * 
	 * @param str
	 * @param start
	 * @return
	 */
	private WordDescription findOneWord(String str, int start) {
		char c = str.charAt(start);
		WordDescription find = new WordDescription(c);
		WordDescription endChar = new WordDescription('\0');
		WordDescription locate = letterIndex.contains(find);
		WordDescription kk = null;
		int ct = 0;
		while (locate != null) {
			ct++;
			kk = locate.getWord().contains(endChar);
			if (start + ct == str.length()) {
				break;
			} else {
				c = str.charAt(start + ct);
				find.setChar(c);
				locate = locate.getWord().contains(find);
			}
		}
		return kk;
	}

	public void setFoundEvent(FoundEvent event) {
		this.findEvent = event;

	}

}
