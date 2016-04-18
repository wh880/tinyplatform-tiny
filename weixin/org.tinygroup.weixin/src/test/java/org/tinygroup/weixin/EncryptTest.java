package org.tinygroup.weixin;

import org.tinygroup.weixin.common.Client;
import org.tinygroup.weixin.util.WeiXinSignatureUtil;

/**
 * 消息的加解密
 * @author yancheng11334
 *
 */
public class EncryptTest {

	/**
	 * 测试消息加解密<br>
	 * 异常java.security.InvalidKeyException:illegal Key Size的解决方案:<br>
	 * 1. 在官方网站下载JCE无限制权限策略文件<br>
	 * 2. 下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt<br>
	 * 3. 如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件<br>
	 * 4. 如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件<br>
	 * JCE官网路径：<br>
	 * JAVA6: http://www.oracle.com/technetwork/java/embedded/embedded-se/downloads/jce-6-download-429243.html <br>
	 * JAVA7: http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html <br>
	 * JAVA8: http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html <br>
	 * @param args
	 */
	public static void main(String[] args) {

		encryptMessage();
		
		decryptMessage();
	}
	
	private  static void encryptMessage(){
		Client client = new Client();
		client.setAppId("wxb11529c136998cb6");
		client.setEncodeAseKey("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG");
		client.setToken("pamtest");
		
		String timestamp = "1409304348";
		String nonce = "xxxxxx";
		
		String sourceXml = "<xml><ToUserName><![CDATA[oia2TjjewbmiOUlr6X-1crbLOvLw]]></ToUserName><FromUserName><![CDATA[gh_7f083739789a]]></FromUserName><CreateTime>1407743423</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[eYJ1MbwPRJtOvIEabaxHs7TX2D-HV71s79GUxqdUkjm6Gs2Ed1KF3ulAOA9H1xG0]]></MediaId><Title><![CDATA[testCallBackReplyVideo]]></Title><Description><![CDATA[testCallBackReplyVideo]]></Description></Video></xml>";
		String xml = WeiXinSignatureUtil.encryptMessage(sourceXml, timestamp, nonce, client);
		System.out.println(xml);
		
	}
	
	private  static  void decryptMessage(){
		Client client = new Client();
		
		String timestamp = "1456887028";
		String nonce = "1726081073";
		client.setAppId("wx325952ab42939270");
		client.setEncodeAseKey("GzcpsksMgUGExSjZs21eci5BCFsxYcX07FsV0NQxgc0");
		client.setToken("tinyframework");
		
		String xml = "<xml><ToUserName><![CDATA[gh_1cb492569c55]]></ToUserName><Encrypt><![CDATA[6eofvVM5fy/WRoO+bqy3NJVO3+2i5rFQAX4hF0fUs2sNjwgWboVp8EkOx0ekE1t5hFRTve2G04L8i9COBRcugrpjgsm5yAzeQe+3+dAbuskoAa31hAap6QHu1UX9r2SmvTKWKwTldRD+fliEghiy4LUipSymroNFfSKL5LbV4idPjcICCqxZ5AAisJF2T5p8bCcILY9SgRdJPGRIj1IIUDvQLLao35RktPfbYqXKk0AelSM8O4zg9YlAAowR32hbCZUfORAcMvXVwshgdPQEb6te8BBfS3N2whHtI0jrJllPVQ2RPKFONfKkbX8hXhuIcn9bCHmiKG85C4X1Txxm8rqV/xBBpgraNGsiBzPh1YYdO+VRQAddL1m4+ke2/jN7sIwhJB4XNWkcIb3Pa16b9vwrknvpTNKSdSFlGEllpIhbhB3zd1TXGILTWe9KaMIWjAImnxhe0eiVwjIEAiGshA==]]></Encrypt></xml>";
		String targetXml = WeiXinSignatureUtil.decryptMessage(xml, timestamp, nonce, client);
		System.out.println(targetXml);
	}

}
