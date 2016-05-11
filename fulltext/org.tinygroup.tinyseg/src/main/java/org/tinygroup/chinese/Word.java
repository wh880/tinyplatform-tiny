package org.tinygroup.chinese;

/**
 * Created by luog on 15/4/13.
 */
public class Word implements Comparable<Word> {
	/**
	 * 单词
	 */
	private String word;
	/**
	 * 拼音列表，为每个单字的拼音索引号，1表示第1个
	 */
	private int[] spell;
	/**
	 * 词形
	 */
	private String[] partOfSpeech;
	/**
	 * 权重,权重值越高，表示越优先选取
	 */
	private int weight;
	/**
	 * 同义词列表
	 */
	private String[] synonyms;
	/**
	 * 反义词列表
	 */
	private String[] antonym;

	public Word() {

	}

	public String[] getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(String[] partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	public Word getReverseWord() {

		Word newWord = new Word(reverseString(word), this.weight);
		newWord.setAntonym(this.antonym);
		newWord.setSpell(this.spell);
		newWord.setSynonyms(this.synonyms);
		return newWord;
	}

	private String reverseString(String string) {
		StringBuffer stringBuffer = new StringBuffer(string);
		stringBuffer.reverse();
		return stringBuffer.toString();
	}

	public Word(String word) {
		this.word = word;
	}

	public Word(String word, int weight) {
		this.word = word;
		this.weight = weight;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int[] getSpell() {
		return spell;
	}

	public void setSpell(int[] spell) {
		this.spell = spell;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String[] getSynonyms() {
		return synonyms;
	}

	public void setSynonyms(String[] synonyms) {
		this.synonyms = synonyms;
	}

	public String[] getAntonym() {
		return antonym;
	}

	public void setAntonym(String[] antonym) {
		this.antonym = antonym;
	}

	public int compareTo(Word o) {
		return word.compareTo(o.word);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
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
		Word other = (Word) obj;
		if (word == null) {
			if (other.word != null) {
				return false;
			}
		} else if (!word.equals(other.word)) {
			return false;
		}
		return true;
	}

}
