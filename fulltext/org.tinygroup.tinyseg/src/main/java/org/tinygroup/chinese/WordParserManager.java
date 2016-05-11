package org.tinygroup.chinese;


import org.tinygroup.chinese.parsermanager.WordDescription;

import java.util.List;

/**
 * 词库接口
 * Created by luog on 15/4/13.
 */
public interface WordParserManager {
    /**
     * 返回指定类型字对象的起始描述节点
     *
     * @param c
     * @param parserType
     * @return
     */
    WordDescription getWordDescription(char c, WordParserType parserType);

    /**
     * 添加停止词
     *
     * @param word
     */
    void addStopWord(String word);

    void addCharacter(Character character);

    void addCharacter(char character);

    /**
     * 添加一组停止词
     *
     * @param stopWordList
     */
    void addStopWords(List<String> stopWordList);

    void addWord(Word word);

    void addWordString(String word);

    void addWordString(List<String> words);

    void addWord(List<Word> words);

    Word getWord(String word) throws ParserException;

    /**
     * 返回是否是停止词
     *
     * @param word
     * @return
     */
    boolean isStopWord(String word);

    /**
     * 返回某字的拼音列表
     *
     * @param character
     * @return
     */
    String[] getCharacterSpells(char character);

    /**
     * 返回某字的指定位置的拼音
     *
     * @param character
     * @param index
     * @return
     */
    String getCharacterSpell(char character, int index);

    /**
     * 返回某字的第一个位置的拼音
     *
     * @param character
     * @return
     */
    String getCharacterSpell(char character);

    /**
     * 根据指定字条返回字符定义
     *
     * @param character
     * @return
     */
    Character getCharacter(char character) throws ParserException;

    /**
     * 返回指定单词的拼音
     *
     * @param word
     * @return
     */
    String[] getWordSpell(String word) throws ParserException;

  
    /**
     * 返回指定单词的拼音简写
     *
     * @param word
     * @return
     */
    String getWordSpellShort(String word) throws ParserException;

    void setSentenceRank(SentenceRank sentenceRank);
}
