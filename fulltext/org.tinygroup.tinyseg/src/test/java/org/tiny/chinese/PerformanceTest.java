package org.tiny.chinese;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.tiny.seg.ChineseParser;
import org.tiny.seg.impl.ChineseParserImpl;

/**
 * 采用相同词库，对比测试
 * @author yancheng11334
 *
 */
public class PerformanceTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		String content = "在中央环境保护督察组看来，造成当下河北省环境问题突出的首要原因各级党委政府和有关部门对环境保护工作的不重视。根据督察谈话中各级领导干部的反映，2013年至2015年7月间，原省委主要领导对环境保护工作不是真重视，没有真抓。2013年至2015年省级财政配套大气污染防治专项资金仅占中央财政拨款的15.5%；河北省发展改革委等有关责任部门在压钢减煤、散煤治理、油品质控等方面监督检查流于形式；一些地方重发展、轻保护现象较为普遍，一些基层党委政府及有关部门环保懒政、惰政情况较为突出。";
		ChineseParser cpi = new ChineseParserImpl();
		FileInputStream f = new FileInputStream(new File("src/test/resources/20160503.worddic"));
		cpi.loadDict(f, "utf-8");
		
		int n = 10000;
		long time = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			dealToken(content,cpi);
		}
		
		time = System.currentTimeMillis() - time;
		System.out.println(String.format("total:%d ms,n:%d", time, n));
	}
	
	private static void dealToken(String content,ChineseParser cpi){
		List<String> result = new ArrayList<String>();
		cpi.segmentWordMax(content, result);
		for(String s:result){
			//System.out.println(s);
		}
	}

}
