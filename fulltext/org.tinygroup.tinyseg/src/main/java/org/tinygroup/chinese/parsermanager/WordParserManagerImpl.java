package org.tinygroup.chinese.parsermanager;

import java.util.List;

import org.tinygroup.binarytree.AVLTree;
import org.tinygroup.binarytree.impl.AVLTreeImpl;
import org.tinygroup.chinese.Character;
import org.tinygroup.chinese.ParserException;
import org.tinygroup.chinese.SentenceRank;
import org.tinygroup.chinese.Word;
import org.tinygroup.chinese.WordParserManager;
import org.tinygroup.chinese.WordParserType;

/**
 * Created by luog on 15/4/14.
 */
public class WordParserManagerImpl implements WordParserManager {
    private AVLTree<Character> characterAVLTree = new AVLTreeImpl<Character>();
    private AVLTree<WordDescription> wordAscAVLTree = new AVLTreeImpl<WordDescription>();
    private AVLTree<WordDescription> wordDescAVLTree = new AVLTreeImpl<WordDescription>();
    private AVLTree<String> stopWordAVLTree = new AVLTreeImpl<String>();
    //private SentenceRank sentenceRank;


    public void addStopWord(String word) {
        stopWordAVLTree.add(word);
    }

    public void addCharacter(Character character) {
        characterAVLTree.add(character);
    }

    public void addCharacter(char character) {
        characterAVLTree.add(new Character(character));
    }

    public void addStopWords(List<String> stopWordList) {
        for (String stopWord : stopWordList) {
            stopWordAVLTree.add(stopWord);
        }
    }

    public void addWord(Word word) {
        addWord(wordDescAVLTree, null, word, word.getReverseWord().getWord(), 0);
        addWord(wordAscAVLTree, null, word, word.getWord(), 0);
    }


    private void addWord(AVLTree<WordDescription> repository, WordDescription wordDesc, Word word, String stringWord, int index) {
        WordDescription wordDescription = null;
        if (index < stringWord.length()) {
            //如果词还有内容
            wordDescription = new WordDescription(stringWord.charAt(index));
        } else if (index == stringWord.length()) {
            //如果词已经处理完毕
            wordDescription = new WordDescription((char) 0);
            wordDescription.setWord(word);
        }else{
            return;
        }

        if (wordDesc == null) {
            //如果是第一个字，则到根上查找是否存在，如果不存在则添加
            WordDescription newWordDescription = repository.contains(wordDescription);
            if(newWordDescription==null){
                repository.add(wordDescription);
            }else{
                wordDescription=newWordDescription;
            }
        } else {
            //查看是否后续有字
            if (wordDesc.getWordDescriptionAVLTree() == null) {
                //如果后续没有字，则添加一个子树
                AVLTree<WordDescription> newWordDescTree = new AVLTreeImpl<WordDescription>();
                wordDesc.setWordDescriptionAVLTree(newWordDescTree);
                newWordDescTree.add(wordDescription);
            } else {
                //如果有字，则查找是否有这个字
                WordDescription newWordDescription = wordDesc.getWordDescriptionAVLTree().contains(
                        wordDescription);
                if(newWordDescription==null){
                    //如果不存在
                    wordDesc.getWordDescriptionAVLTree().add(wordDescription);
                }else{
                    if(newWordDescription.getCharacter()==0) {//如果是同样的词加入，则更新内容
                        newWordDescription.setWord(word);
                    }
                    wordDescription=newWordDescription;
                }
            }
        }

        addWord(repository, wordDescription, word, stringWord, index + 1);
    }

    public void addWordString(String word) {
        addWord(new Word(word));
    }

    public void addWordString(List<String> words) {
        for (String word : words) {
            addWordString(word);
        }
    }

    public void addWord(List<Word> words) {
        for (Word word : words) {
            addWord(word);
        }
    }

    public boolean isStopWord(String word) {
        return stopWordAVLTree.contains(word) != null;
    }

    public String[] getCharacterSpells(char c) {
        Character character = characterAVLTree.contains(new Character(c));
        if (character != null) {
            return character.getSpell();
        }
        return null;
    }

    public String getCharacterSpell(char character, int index) {
        String[] spells = getCharacterSpells(character);
        if (spells != null && spells.length >= index) {  	//因为spell是从1开始，而index是从0开始，所以需要-1
            return spells[index-1];
        }
        return null;
    }

    public String getCharacterSpell(char character) {
        return getCharacterSpell(character, 1);
    }

    public Character getCharacter(char character) throws ParserException {
        Character ch = characterAVLTree.contains(new Character(character));
        if (ch == null) {
            throw new ParserException("词库中不存在指定的字：" + character);
        } else {
            return ch;
        }
    }

    public String[] getWordSpell(String word) throws ParserException {
        Word w = getWord(word);
        String[] result = new String[word.length()];
        if (w != null) {
            int[] spell = w.getSpell();
            for (int i = 0; i < word.length(); i++) {
                if (spell != null) {
                    result[i] = getCharacterSpell(word.charAt(i), spell[i]);
                } else {
                    result[i] = getCharacterSpell(word.charAt(i));
                }
            }
        }
        return result;
    }

    public Word getWord(String word) throws ParserException {
        return getWord(word, 0);
    }

    private Word getWord(String word, int start) throws ParserException {
        char c = word.charAt(start);
        WordDescription find = new WordDescription(c);
        WordDescription endChar = new WordDescription('\0');
        WordDescription locate = wordAscAVLTree.contains(find);
        WordDescription wordDescription = null;
        int ct = 0;
        while (locate != null) {
            ct++;
            wordDescription = locate.getWordDescriptionAVLTree().contains(endChar);
            if (start + ct == word.length()) {
                break;
            } else {
                c = word.charAt(start + ct);
                find.setCharacter(c);
                locate = locate.getWordDescriptionAVLTree().contains(find);
            }
        }
        if (wordDescription != null) {
            return wordDescription.getWord();
        } else {
            throw new ParserException("词库中不存在词组：" + word);
        }
    }

    public WordDescription getWordDescription(char c, WordParserType parserType) {
        if (parserType == WordParserType.ASC) {
            return this.wordAscAVLTree.contains(new WordDescription(c));
        } else {
            return this.wordDescAVLTree.contains(new WordDescription(c));
        }
    }

    public String getWordSpellShort(String word) throws ParserException {
        String[] spell = getWordSpell(word);
        if (spell != null) {
            char[] result = new char[word.length()];
            for (int i = 0; i < spell.length; i++) {
                result[i] = spell[i].charAt(0);
            }
            return new String(result);
        }
        throw new ParserException("词库中找不到单词：" + word);
    }

    public void setSentenceRank(SentenceRank sentenceRank) {
        //this.sentenceRank=sentenceRank;
        sentenceRank.setWordParserManager(this);
    }

	
}
