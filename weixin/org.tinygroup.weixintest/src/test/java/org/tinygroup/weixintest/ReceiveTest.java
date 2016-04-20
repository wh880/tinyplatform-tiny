package org.tinygroup.weixintest;

import java.io.File;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.commons.tools.FileUtil;
import org.tinygroup.tinytestutil.AbstractTestUtil;
import org.tinygroup.weixinhttp.WeiXinHttpConnector;

/**
 * 测试演示工程的推送消息
 * @author yancheng11334
 *
 */
public class ReceiveTest {

	private static WeiXinHttpConnector weiXinHttpConnector;
	static{
		AbstractTestUtil.init(null, false);
		weiXinHttpConnector = BeanContainerFactory.getBeanContainer(KfMessageTest.class.getClassLoader()).getBean(WeiXinHttpConnector.DEFAULT_BEAN_NAME);
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String url ="http://127.0.0.1:8080/org.tinygroup.weixinservice/service.do";
		String xml = FileUtil.readFileContent(new File("src/test/resources/TextMessage.xml"), "utf-8");
		String result= weiXinHttpConnector.postUrl(url, xml,null);
		Assert.assertNotNull(result);
	}
         
}
