package org.tinygroup.chineseanalyzer;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;

/**
 * 基于TinySeg的Lucene的分析器扩展
 * @author yancheng11334
 *
 */
public class ChineseAnalyzer extends Analyzer {

	
	protected TokenStreamComponents createComponents(String fieldName,
			Reader reader) {
		return new TokenStreamComponents(new ChineseTokenStream(reader));
	}

}
