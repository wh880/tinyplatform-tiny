package org.tinygroup.weixintool;

import java.io.File;

import junit.framework.TestCase;

import org.tinygroup.commons.tools.FileUtil;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.util.WeiXinParserUtil;
import org.tinygroup.weixintool.convert.json.ShortUrlResultConvert;
import org.tinygroup.weixintool.message.ShortUrl;
import org.tinygroup.weixintool.result.ShortUrlResult;

public class ShortUrlResultTest extends TestCase{
	
	/**
	 * 测试生成JSON报文
	 */
	public void testMessage(){
		ShortUrl shortUrl = new ShortUrl();
		shortUrl.setAction("long2short");
		shortUrl.setLongUrl("http://wap.koudaitong.com/v2/showcase/goods?alias=128wi9shh&spm=h56083&redirect_count=1");
		
		assertEquals("{\"action\":\"long2short\",\"long_url\":\"http://wap.koudaitong.com/v2/showcase/goods?alias=128wi9shh&spm=h56083&redirect_count=1\"}", shortUrl.toString());
	}
	
	/**
	 * 测试解析JSON报文
	 * @throws Exception
	 */
    public void testResult() throws Exception{
    	
    	String json = FileUtil.readFileContent(new File("src/test/resources/json.txt"), "utf-8");
		
		WeiXinParserUtil.addJsonConvert(new ShortUrlResultConvert());
		
		ShortUrlResult result = WeiXinParserUtil.parse(json, null , WeiXinConvertMode.SEND);
		assertNotNull(result);
		assertEquals("http://w.url.cn/s/AvCo6Ih", result.getShortUrl());
	}
}
