package org.tinygroup.mmseg4j;

import java.io.Reader;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

/**
 * MMSegAnalyzer包装类，修正在lucene4.6以上报错的问题
 * @author yancheng11334
 *
 */
public class NewSimpleAnalyzer extends MMSegAnalyzer{

	public NewSimpleAnalyzer(){
		super();
	}
	
	public NewSimpleAnalyzer(String path) {
		super(path);
	}
	
	protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
		return new TokenStreamComponents(new NewMMSegTokenizer(newSeg(), reader));
	}
}
