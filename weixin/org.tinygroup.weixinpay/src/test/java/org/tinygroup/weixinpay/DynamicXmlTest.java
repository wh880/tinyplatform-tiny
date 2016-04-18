package org.tinygroup.weixinpay;

import java.io.File;

import org.tinygroup.commons.tools.FileUtil;
import org.tinygroup.tinytestutil.AbstractTestUtil;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.impl.WeiXinContextDefault;
import org.tinygroup.weixin.util.WeiXinParserUtil;
import org.tinygroup.weixinpay.result.QueryRefundResult;

import junit.framework.TestCase;

/**
 * 动态XML报文解析
 * @author yancheng11334
 *
 */
public class DynamicXmlTest extends TestCase{

	protected void setUp() throws Exception {
		AbstractTestUtil.init(null, false);
	}
	
	public void testQueryRefundResult() throws Exception{
		String xml = FileUtil.readFileContent(new File("src/test/resources/QueryRefundResult.xml"), "utf-8");
		QueryRefundResult result = parse(xml);
		
		assertNotNull(result);
		assertEquals(1, result.getRefundNumber());
		assertEquals(1, result.getRefundResultList().size());
	}
	
	private <T> T parse(String xml){
		return WeiXinParserUtil.parse(xml,new WeiXinContextDefault(),WeiXinConvertMode.SEND);
	}
}
