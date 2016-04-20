package org.tinygroup.weixinhttpclient451;

import junit.framework.Assert;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.tinytestutil.AbstractTestUtil;
import org.tinygroup.weixin.WeiXinConnector;
import org.tinygroup.weixin.result.AccessToken;

public class ConnectTest {

	private static WeiXinConnector weiXinConnector;
	static{
		AbstractTestUtil.init(null, false);
		weiXinConnector = BeanContainerFactory.getBeanContainer(ConnectTest.class.getClassLoader()).getBean("weiXinConnector");
	}
	
	public static void main(String[] args){
		AccessToken token =weiXinConnector.getAccessToken();
		Assert.assertNotNull(token);
	}
}
