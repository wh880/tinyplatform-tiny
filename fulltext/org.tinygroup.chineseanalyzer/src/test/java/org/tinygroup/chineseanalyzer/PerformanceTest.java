package org.tinygroup.chineseanalyzer;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tinygroup.tinyrunner.Runner;

/**
 * 采用相同词库，对比测试
 * @author yancheng11334
 *
 */
public class PerformanceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		Runner.init("application.xml", null);
		String content="在中央环境保护督察组看来，造成当下河北省环境问题突出的首要原因各级党委政府和有关部门对环境保护工作的不重视。根据督察谈话中各级领导干部的反映，2013年至2015年7月间，原省委主要领导对环境保护工作不是真重视，没有真抓。2013年至2015年省级财政配套大气污染防治专项资金仅占中央财政拨款的15.5%；河北省发展改革委等有关责任部门在压钢减煤、散煤治理、油品质控等方面监督检查流于形式；一些地方重发展、轻保护现象较为普遍，一些基层党委政府及有关部门环保懒政、惰政情况较为突出。";
		
		int n = 10000;
		long time= System.currentTimeMillis();
		for(int i=0;i<n;i++){
			dealToken(content);
		}
		time =System.currentTimeMillis()-time;
		System.out.println(String.format("total:%d ms,n:%d", time,n));
		
	}
	
	private static void dealToken(String content) throws IOException{
		ChineseTokenStream token = new ChineseTokenStream(
				new StringReader(content));
		try{
			while (token.incrementToken()) {
				token.getAttribute(CharTermAttribute.class);
				//System.out.println();
			}
		}finally{
			token.close();
		}
	}

}
