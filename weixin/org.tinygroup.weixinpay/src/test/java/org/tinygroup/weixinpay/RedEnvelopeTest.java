package org.tinygroup.weixinpay;

import java.io.File;

import junit.framework.TestCase;

import org.tinygroup.commons.tools.FileUtil;
import org.tinygroup.tinytestutil.AbstractTestUtil;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.impl.WeiXinContextDefault;
import org.tinygroup.weixin.util.WeiXinParserUtil;
import org.tinygroup.weixinpay.result.CommonRedEnvelopeResult;
import org.tinygroup.weixinpay.result.GroupRedEnvelopeResult;
import org.tinygroup.weixinpay.result.QueryRedEnvelopeResult;

/**
 * 测试红包相关接口
 * @author yancheng11334
 *
 */
public class RedEnvelopeTest extends TestCase{

	protected void setUp() throws Exception {
		AbstractTestUtil.init(null, false);
	}
	
	/**
	 * 测试发送普通红包结果的解析
	 * @throws Exception
	 */
	public void testCommonRedEnvelopeResult() throws Exception{
		String xml = FileUtil.readFileContent(new File("src/test/resources/CommonRedEnvelopeResult.xml"), "utf-8");
		CommonRedEnvelopeResult result = parse(xml);
		assertEquals(100,result.getTotalAmount());
		assertEquals("1244859502",result.getMchId());
	}
	
	/**
	 * 测试发送裂变红包结果的解析
	 * @throws Exception
	 */
	public void testGroupRedEnvelopeResult() throws Exception{
		String xml = FileUtil.readFileContent(new File("src/test/resources/GroupRedEnvelopeResult.xml"), "utf-8");
		GroupRedEnvelopeResult result = parse(xml);
		assertEquals(300,result.getTotalAmount());
		assertEquals(3,result.getTotalNum());
		assertEquals("100493101201512182159997298",result.getOrderId());
	}
	
	/**
	 * 测试查询普通红包结果的解析
	 * @throws Exception
	 */
	public void testQueryCommonRedEnvelope() throws Exception{
		String xml = FileUtil.readFileContent(new File("src/test/resources/QueryCommonRedEnvelope.xml"), "utf-8");
		QueryRedEnvelopeResult result = parse(xml);
		assertNotNull(result.getRedEnvelopeInfoList());
		assertEquals("NORMAL",result.getRedEnvelopeType());
		assertEquals("API",result.getSendType());
		assertEquals("0010244084201601150422687235",result.getRedEnvelopeId());
		assertEquals(1,result.getRedEnvelopeInfoList().getIntoList().size());
	}
	
	/**
	 * 测试查询裂变红包结果的解析
	 * @throws Exception
	 */
	public void testQueryGroupRedEnvelope() throws Exception{
		String xml = FileUtil.readFileContent(new File("src/test/resources/QueryGroupRedEnvelope.xml"), "utf-8");
		QueryRedEnvelopeResult result = parse(xml);
		assertNotNull(result.getRedEnvelopeInfoList());
		assertEquals("GROUP",result.getRedEnvelopeType());
		assertEquals("API",result.getSendType());
		assertEquals("0010244084201601160427757215",result.getRedEnvelopeId());
		assertEquals(2,result.getRedEnvelopeInfoList().getIntoList().size());
	}
	
	private <T> T parse(String xml){
		return WeiXinParserUtil.parse(xml,new WeiXinContextDefault(),WeiXinConvertMode.SEND);
	}
}
