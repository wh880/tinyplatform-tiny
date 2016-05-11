package org.tinygroup.chinese.single;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.tinygroup.binarytree.AVLTree;
import org.tinygroup.chinese.*;
import org.tinygroup.chinese.parsermanager.WordDescription;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by luog on 15/4/13.
 */
public class SingleWordReaderParser implements WordParser<SingleToken, Reader> {

	public static final char END_OF_WORD = (char) 0;
	public static final WordDescription ZERO_WORD_DESCRIPTION = new WordDescription(
			END_OF_WORD);
	private WordParserType wordParserType;
	private WordParserMode wordParserMode;
	protected int line = 0;
	protected LineNumberReader reader;
	protected int pos = 0;
	protected String content = null;
	private WordParserManager wordParserManager;
	private List<SingleToken> singleTokens = new ArrayList<SingleToken>();

	public void parse(WordParserManager wordParserManager, Reader reader,
			WordParserType wordParserType, WordParserMode wordParserMode) {
		this.wordParserType = wordParserType;
		this.wordParserMode = wordParserMode;
		this.wordParserManager = wordParserManager;
		if (reader instanceof LineNumberReader) {
			this.reader = (LineNumberReader) reader;
		} else {
			this.reader = new LineNumberReader(reader);
		}
	}

	public Collection<SingleToken> tokens() throws IOException {
		List<SingleToken> tokens = new ArrayList<SingleToken>();
		SingleToken token;
		while ((token = nextToken()) != null) {
			tokens.add(token);
		}
		return tokens;
	}
	
	private boolean hasNextToken(){
		if (content == null || wordParserType == WordParserType.ASC
				&& pos >= content.length()
				|| wordParserType == WordParserType.DESC && pos < 0) {
			return true;
		}
		return false;
	}

	public SingleToken nextToken() throws IOException {
		if (hasNextToken()) {
			if (!readLine()) {
				return null;
			}
		}
		char ch = content.charAt(pos);
		if (CharUtils.isAsciiAlphanumeric(ch)) {
			return nextAlphaNumericToken();
		} else if (!CharUtils.isAscii(ch)) {
			return nextChineseToken();
		} else {
			moveToNextPosition();
		}
		return null;
	}

	public List<SingleToken> nextSentenceTokens() throws IOException {
		if (hasNextToken()) {
			if (!readLine()) {
				return null;
			}
		}
		SingleWordStringParser singleWordStringParser = new SingleWordStringParser();
		singleWordStringParser.parse(wordParserManager, content,
				wordParserType, wordParserMode);
		List<SingleToken> result = new ArrayList<SingleToken>();
		result.addAll(singleWordStringParser.tokens());
		content = null;
		return result;
	}

	private void moveToNextPosition() {
		if (wordParserType == WordParserType.ASC) {
			pos++;
		} else {
			pos--;
		}
	}

	/**
	 * 读一行数据
	 * 
	 * @return
	 * @throws IOException
	 */
	private boolean readLine() throws IOException {
		content = reader.readLine();
		if (content != null) {
			line++;
			if (wordParserType == WordParserType.ASC) {
				pos = 0;
			} else {
				pos = content.length() - 1;
			}
			return true;
		}
		return false;
	}

	private SingleToken nextChineseToken() {
		if (wordParserMode == WordParserMode.ALL && singleTokens.size() > 0) {
			return singleTokens.remove(0);
		}
		int start = pos, end = pos;
		WordDescription wordDescription = wordParserManager.getWordDescription(
				readChar(), wordParserType);
		if (wordDescription != null) {
			char ch = readChar();
			while (ch != 0) {
				wordDescription = wordDescription.getWordDescriptionAVLTree()
						.contains(new WordDescription(ch));
				if (wordDescription == null) {// 如果后序没有找到，则跳出
					break;
				} else {
					AVLTree<WordDescription> wordDescriptionAVLTree = wordDescription
							.getWordDescriptionAVLTree();
					if (wordDescriptionAVLTree.contains(ZERO_WORD_DESCRIPTION) != null) {
						end = pos;
						if (wordParserMode == WordParserMode.MIN) {
							// 如果是最小优先,且出在已经是单词
							break;
						} else if (wordParserMode == WordParserMode.ALL) {
							singleTokens.add(getSingleToken(start,
									getWordString(start, end), TokenType.WORD));
						}
					}
				}
				ch = readChar();
			}
		} else {
			end = pos;
		}
		pos = end;
		if (wordParserMode == WordParserMode.ALL && singleTokens.size() > 0) {
			return singleTokens.remove(0);
		}
		if(start==end){ //说明碰到单字直接break的情况
			pos++;
			return getSingleToken(start, getWordString(start, pos), TokenType.WORD);
		}
		return getSingleToken(start, getWordString(start, end), TokenType.WORD);
	}

	private String getWordString(int start, int end) {
		String word;
		if (wordParserType == WordParserType.ASC) {
			word = content.substring(start, end);
		} else {
			word = content.substring(end + 1, start + 1);
		}
		return word;
	}

	private SingleToken getSingleToken(int start, String word,
			TokenType tokenType) {
		if (wordParserType == WordParserType.ASC) {
			return new SingleToken(word, tokenType, line, start, pos - 1);
		} else {
			return new SingleToken(word, tokenType, line, pos - 1, start);
		}
	}

	private char readChar() {
		char ch = 0;
		if (wordParserType == WordParserType.ASC && pos < content.length()) {
			ch = content.charAt(pos++);
		} else if (wordParserType == WordParserType.DESC && pos >= 0) {
			ch = content.charAt(pos--);
		}
		return ch;
	}

	private SingleToken nextAlphaNumericToken() {
		int start = pos, end = pos;
		char ch = readChar();
		while (CharUtils.isAsciiAlphanumeric(ch) || ch == '.' || ch == ',') {
			end = pos;
			ch = readChar();
			if (ch == "0".charAt(0)) {
				break;
			}
			
		}
		String word = getWordString(start, end);
		TokenType tokenType = TokenType.ALPHA;
		String maybeNumericString = word.replaceAll(",", "");
		if (NumberUtils.isNumber(maybeNumericString)) {
			tokenType = TokenType.NUMERIC;
			word = maybeNumericString;
		}
		return getSingleToken(start, word, tokenType);
	}
}
