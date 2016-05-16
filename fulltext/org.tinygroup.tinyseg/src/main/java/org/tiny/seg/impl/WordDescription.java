package org.tiny.seg.impl;

import org.tinygroup.binarytree.AVLTree;
import org.tinygroup.binarytree.impl.AVLTreeImpl;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * 单词的描述
 * 
 * @author luoguo
 * 
 */
public class WordDescription implements Comparable<WordDescription> {
	private AVLTree<String> partOfSpeech = null;// 词性
	private String pinyin = null;// 拼音
	private int weighing = 0;// 权重
	private AVLTree<String> thesaurus = null;// 同义词
	private AVLTree<String> antonym = null;// 反义词
	private Character c;
	private AVLTree<WordDescription> word;

	public void setWord(AVLTree<WordDescription> word) {
		this.word = word;
	}

	public String getPinyin() {
		return pinyin;
	}

	public AVLTree<WordDescription> getWord() {
		return word;
	}

	public AVLTree<String> getPartOfSpeech() {
		return partOfSpeech;
	}

	public int getWeighing() {
		return weighing;
	}

	public AVLTree<String> getThesaurus() {
		return thesaurus;
	}

	public AVLTree<String> getAntonym() {
		return antonym;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public WordDescription(char c) {
		this.c = c;
	}

	public void setChar(char c) {
		this.c = c;
	}

	public void setProperties(XmlNode node) {
		if (node != null) {
			pinyin = node.getAttribute("pinyin");
			String str = node.getAttribute("partOfSpeech");
			if (str != null) {
				partOfSpeech = new AVLTreeImpl<String>();
				partOfSpeech.add(str.trim().split(","));
			}
			str = node.getAttribute("antonym");
			if (str != null) {
				antonym = new AVLTreeImpl<String>();
				antonym.add(str.trim().split(","));
			}
			str = node.getAttribute("thesaurus");
			if (str != null) {
				thesaurus = new AVLTreeImpl<String>();
				thesaurus.add(str.trim().split(","));
			}
			str = node.getAttribute("weighing");
			if (str != null && str.trim().length() > 0) {
				weighing = Integer.parseInt(str);
			}
		}
	}

	public int compareTo(WordDescription o) {
		return c.compareTo(o.c);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c == null) ? 0 : c.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		WordDescription other = (WordDescription) obj;
		if (c == null) {
			if (other.c != null) {
				return false;
			}
		} else if (!c.equals(other.c)) {
			return false;
		}
		return true;
	}

}