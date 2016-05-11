package org.tiny.seg.impl;

import org.tiny.seg.PinYin;
import org.tiny.seg.exception.DictLoadRuntimeException;
import org.tinygroup.binarytree.AVLTree;
import org.tinygroup.binarytree.impl.AVLTreeImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class PinYinImpl implements PinYin {
	/**
	 * 用于存放字及其拼音
	 * 
	 * @author luoguo
	 * 
	 */

	private AVLTree<CharWithPinYin> tree = new AVLTreeImpl<CharWithPinYin>();

	public AVLTree<CharWithPinYin> getTree() {
		return tree;
	}

	public void setTree(AVLTree<CharWithPinYin> tree) {
		this.tree = tree;
	}

	public void loadPinFile(InputStream inputStream, String encode) {
		try {
			// 载入数据
			BufferedReader in = new BufferedReader(new InputStreamReader(
					inputStream, encode));
			String str;
			while ((str = in.readLine()) != null) {
				str = str.trim();
				if (str.length() > 0 && !str.startsWith("#")) {
					String[] s = str.split(" ");
					if (s.length == 2) {
						for (int i = 0; i < s[1].length(); i++) {
							addPinYin(s[1].charAt(i), s[0]);
						}
					}
				}
			}
			in.close();
		} catch (Exception e) {
			throw new DictLoadRuntimeException(e);
		}
	}

	private void addPinYin(char charAt, String string) {
		CharWithPinYin py = new CharWithPinYin(charAt);
		CharWithPinYin p = tree.contains(py);
		if (p != null) {
			py = p;
		} else {
			tree.add(py);
		}
		py.addPinYin(string);
	}

	public List<String> getPinYin(char c) {
		CharWithPinYin py = new CharWithPinYin(c);
		py = tree.contains(py);
		if (py != null) {
			return py.getPinyinVector();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<String>[] getPinYin(char[] c) {
		List<String>[] r = new ArrayList[c.length];
		for (int i = 0; i < c.length; i++) {
			r[i] = getPinYin(c[i]);
		}
		return r;
	}

	public List<String> getPinYin(String str) {
		List<String> r = new ArrayList<String>();
		for (int i = 0; i < str.length(); i++) {
			r.add(getPinYin(str.charAt(i)).get(0));
		}
		return r;
	}

	public List<String> getPinYin(String str, String pinyin) {
		if (pinyin == null || pinyin.length() == 0) {
			return getPinYin(str);
		}
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < str.length(); i++) {
			String nextChar = pinyin.substring(i, i + 1);
			result.add(getPinYin(str.charAt(i)).get(
					Integer.valueOf(nextChar) - 1));
		}
		return result;
	}
}

class CharWithPinYin implements Comparable<CharWithPinYin> {
	private Character c;
	private List<String> pinyinVector;

	public List<String> getPinyinVector() {
		return pinyinVector;
	}

	public void setPinyinVector(List<String> pinyinVector) {
		this.pinyinVector = pinyinVector;
	}

	public int compareTo(CharWithPinYin o) {
		return c.compareTo(o.c);
	}

	public void addPinYin(String string) {
		if (pinyinVector == null) {
			pinyinVector = new ArrayList<String>();
		}
		pinyinVector.add(string);
	}

	public CharWithPinYin(char c) {
		this.c = c;
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
		CharWithPinYin other = (CharWithPinYin) obj;
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