package org.tinygroup.chineseanalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.tiny.seg.ChineseParser;
import org.tinygroup.beancontainer.BeanContainerFactory;

/**
 * 基于TinySeg的Lucene的令牌流扩展
 * 
 * @author yancheng11334
 * 
 */
public class ChineseTokenStream extends Tokenizer {

	private ChineseParserWrapper wrapper = null;
	
	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
	private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);
	
	protected ChineseTokenStream(Reader input) {
		super(input);
		wrapper = new ChineseParserWrapper(input);
	}

	public void reset() throws IOException {
		super.reset();
		wrapper.reset();
	}

	public final boolean incrementToken() throws IOException {
		clearAttributes();
		ChineseWord word = wrapper.next();
		if(word!=null){
			termAtt.copyBuffer(word.getSource(), word.getSourceOffset(), word.getLength());
			offsetAtt.setOffset(word.getStartOffset(), word.getEndOffset());
			typeAtt.setType("word");
			return true;
		}else{
			end();
			return false;
		}
	}
	
	class ChineseParserWrapper {
		private BufferedReader reader = null;
		private String buffer = null;
		private ChineseParser parser = null;

		private int index =  0; 
		private int offset = 0;
		private Queue<ChineseWord> words =null;
		
		public ChineseParserWrapper(Reader input){
			reader = new BufferedReader(input);
			parser = BeanContainerFactory.getBeanContainer(
					this.getClass().getClassLoader()).getBean(
					ChineseParser.CHINESE_PARSER_BEAN_NAME);
		}
		
	    private void init() throws IOException{
	    	words = new LinkedList<ChineseWord>();
	    	while ((buffer = reader.readLine()) != null) {
				List<String> result = new ArrayList<String>();
				parser.segmentWordMax(buffer, result);
				int sourceOffset = 0;
				char[] source = buffer.toCharArray();
				for(String s:result){
					int length =s.toCharArray().length;
					ChineseWord word = new ChineseWord(source,sourceOffset,offset,length,index);
					words.add(word);
					index++;
					sourceOffset += length;
					offset += length;
				}
			}
	    }
		
		public void reset() throws IOException{
			if (reader.markSupported()) {
				reader.mark(0);
			}
			reader.reset();
			buffer = null;
			index = 0;
			offset = 0;
			words = null;
		}
		
		public ChineseWord next() throws IOException{
			if(words==null){
			   init();
			}
			return words.poll();
		}
	}
	
	class ChineseWord {
		
		public ChineseWord(char[] source, int sourceOffset, int startOffset,int length,
				int index) {
			super();
			this.source = source;
			this.sourceOffset = sourceOffset;
			this.startOffset = startOffset;
			this.length = length;
			this.index = index;
		}

		private char[] source;
		private int sourceOffset;
		private int startOffset;
		private int length;
		private int index;
		/**
		 * 返回词所在字符串
		 * @return
		 */
		public char[] getSource(){
			return source;
		}
		
		/**
		 * 返回词相对字符串的偏移量
		 * @return
		 */
		public int getSourceOffset(){
			return sourceOffset;
		}
		
		/**
		 * 获得词的长度
		 * @return
		 */
		public int getLength(){
			return length;
		}
		
		/**
		 * 获得词相对全文的起始位置
		 * @return
		 */
		public int getStartOffset() {
			return startOffset;
		}
		
		/**
		 * 获得词相对全文的结束位置
		 * @return
		 */
		public int getEndOffset() {
			return startOffset+getLength();
		}
		
		/**
		 * 得到索引号
		 * @return
		 */
		public int getIndex() {
			return index;
		}
	}

}
