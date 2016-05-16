package org.tinygroup.chinese.fileProcessor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tiny.seg.exception.DictLoadRuntimeException;
import org.tinygroup.chinese.Character;
import org.tinygroup.chinese.Word;
import org.tinygroup.chinese.WordParserManager;
import org.tinygroup.chinese.parsermanager.WordParserManagerImpl;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;

public class ChineseContainer {

	private static final int BOM = 65279;
	private static Map<String, List<Word>> wordMap = new HashMap<String, List<Word>>();
//	private static PinYin pinYin = new PinYinImpl();
	private static List<Character> characterList = new ArrayList<Character>();
	private static Map<java.lang.Character, List<String>> pinyinMap = new HashMap<java.lang.Character, List<String>>();
	private static Map<String, WordParserManager> wordManagerMap = new HashMap<String, WordParserManager>();
	private static final String PRE_SCENE = "TINY_WORD_SCENE_";
	private static final String TYPE_STRING = "string";
	private static final String TYPE_REGEX = "regex";
	private static final String DEFAULT_ALL = "DEFAULT_ALL";

//	public static void loadDict(FileObject fileObject, String encode) {
//		pinYin.loadPinFile(fileObject.getInputStream(), encode);
//	}

	public static void loadDict(List<FileObject> fileObjects, String encode) {
		for (FileObject f : fileObjects) {
			loadPinYinFile(f.getInputStream(), encode);
		}
		initCharacter();
	}

	private static void loadPinYinFile(InputStream inputStream, String encode) {
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

	private static void addPinYin(char charAt, String string) {
		if (pinyinMap.containsKey(charAt)) {
			pinyinMap.get(charAt).add(string);
			return;
		}
		List<String> list = new ArrayList<String>();
		list.add(string);
		pinyinMap.put(charAt, list);

	}
	
	private static void initCharacter(){
		for (java.lang.Character ch : pinyinMap.keySet()) {
			Character c = new Character(ch);
			List<String> spell = pinyinMap.get(ch);
			String[] sepllArray = new String[spell.size()];
			for(int i = 0 ; i < spell.size() ; i ++ ){
				sepllArray[i] = spell.get(i);
			}
			c.setSpell(sepllArray);
			characterList.add(c);
		}
	}

	public static void loadWord(FileObject fileObject, String encode) {
		try {
			InputStream inputStream = fileObject.getInputStream();
			String absolutePath = fileObject.getAbsolutePath();
			List<Word> wordList = wordMap.get(absolutePath);
			if (wordList == null) {
				wordList = new ArrayList<Word>();
				wordMap.put(absolutePath, wordList);
			}
			XmlStringParser parser = new XmlStringParser();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					inputStream, encode));
			String str;
			while ((str = in.readLine()) != null) {
				boolean notBomChar = !(str.length() == 1 && (int) str.charAt(0) == BOM);
				boolean validLine = str.length() > 0 && !str.startsWith("#");
				if (notBomChar && validLine) {
					addWord(wordList, parser.parse(str).getRoot());
				}
			}
			in.close();
		} catch (Exception e) {
			throw new DictLoadRuntimeException(e);
		}
	}

	private static void addWord(List<Word> list, XmlNode root) {
		if (root == null) {
			return;
		}
		Word word = new Word();
		setProperties(root, word);
		list.add(word);
	}

	private static void setProperties(XmlNode node, Word word) {
		if (node != null) {
			String wordString = node.getAttribute("word");
			word.setWord(wordString);

			String str = node.getAttribute("partOfSpeech");
			if (!StringUtil.isBlank(str)) {
				word.setPartOfSpeech(str.trim().split(","));
			}
			str = node.getAttribute("pinyin");
			if (!StringUtil.isBlank(str)) {
				int length = str.trim().length();
				int[] array = new int[length];
				for (int i = 0; i < length; i++) {
					array[i] = Integer.parseInt(str.substring(i, i + 1));
				}
				word.setSpell(array);
			}
			str = node.getAttribute("antonym");
			if (!StringUtil.isBlank(str)) {
				word.setAntonym(str.trim().split(","));
			}
			str = node.getAttribute("thesaurus");
			if (!StringUtil.isBlank(str)) {
				word.setSynonyms(str.trim().split(","));
			}
			str = node.getAttribute("weighing");
			if (!StringUtil.isBlank(str)) {
				word.setWeight(Integer.parseInt(str));
			}
		}
	}

	public static WordParserManager getWordParserManager(String names) {
		if (wordManagerMap.containsKey(names)) {
			return wordManagerMap.get(names);
		}
		if (StringUtil.isBlank(names)) {
			return getFullManager();
		}
		return getManagerByNames(names);
	}

	private static WordParserManager getManagerByNames(String names) {
		WordParserManager manager = createWordParserManager();
		String[] nameArray = names.split(",");
		for (String name : nameArray) {
			String fullName = name + ChineseWordFileProcessor.EXT_FILE_NAME;
			if (!wordMap.containsKey(fullName)) {
				throw new RuntimeException("词库文件" + fullName + "不存在");
			}
			manager.addWord(wordMap.get(fullName));

		}
		wordManagerMap.put(names, manager);
		return manager;
	}

	private static WordParserManager getFullManager() {
		if (!wordManagerMap.containsKey("")) {
			WordParserManager manager = createWordParserManager();
			for (String name : wordMap.keySet()) {
				manager.addWord(wordMap.get(name));
			}
			wordManagerMap.put("", manager);
		}
		return wordManagerMap.get("");
	}

	public static void RegScene(String scene, String wordNames, String type) {
		WordParserManager manager = null;
		if (StringUtil.isBlank(wordNames)) { // 如果字符串为空，则添加所有
			manager = getFullManager();
		} else if (TYPE_STRING.equals(type)) {// 字符串类型
			manager = getWordParserManager(wordNames);
		} else if (TYPE_REGEX.equals(type)) {// 正则表达式类型
			manager = getManagerByRegex(wordNames);
		} else {
			throw new RuntimeException("类型" + type + "错误，不存在该类型");
		}
		wordManagerMap.put(getSceneKey(scene), manager);
	}

	private static WordParserManager getManagerByRegex(String wordNames) {
		WordParserManager manager = createWordParserManager();
		Pattern p = Pattern.compile(wordNames);
		for (String name : wordMap.keySet()) {
			Matcher matcher = p.matcher(name);
			if (matcher.find()) {
				manager.addWord(wordMap.get(name));
			}
		}
		return manager;
	}

	private static String getSceneKey(String scene) {
		if (StringUtil.isBlank(scene)) {
			return PRE_SCENE + DEFAULT_ALL;
		}
		return PRE_SCENE + scene;
	}

	private static WordParserManager createWordParserManager() {
		WordParserManager manager = new WordParserManagerImpl();
		addPinYin(manager);
		return manager;
	}

	private static void addPinYin(WordParserManager manager) {
		for(Character c : characterList){
			manager.addCharacter(c);
		}
	}
}
