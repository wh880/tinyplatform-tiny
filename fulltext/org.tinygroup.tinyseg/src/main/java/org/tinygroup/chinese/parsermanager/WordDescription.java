package org.tinygroup.chinese.parsermanager;

import org.tinygroup.binarytree.AVLTree;
import org.tinygroup.chinese.Word;

/**
 * 单词的描述
 * 
 * @author luoguo
 * 
 */
public class WordDescription implements Comparable<WordDescription> {
	private Character character;
	private AVLTree<WordDescription> wordDescriptionAVLTree;
	private Word word = null;

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public AVLTree<WordDescription> getWordDescriptionAVLTree() {
		return wordDescriptionAVLTree;
	}

	public void setWordDescriptionAVLTree(
			AVLTree<WordDescription> wordDescriptionAVLTree) {
		this.wordDescriptionAVLTree = wordDescriptionAVLTree;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public WordDescription(char character) {
		this.character = character;
	}

	public int compareTo(WordDescription o) {
		return character.compareTo(o.character);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((character == null) ? 0 : character.hashCode());
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
		if (character == null) {
			if (other.character != null) {
				return false;
			}
		} else if (!character.equals(other.character)) {
			return false;
		}
		return true;
	}

}