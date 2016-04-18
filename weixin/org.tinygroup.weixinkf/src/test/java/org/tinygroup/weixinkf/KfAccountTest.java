package org.tinygroup.weixinkf;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.tinygroup.weixinkf.kfaccount.AddCustomerAccount;
import org.tinygroup.weixinkf.kfaccount.CustomerServiceAccount;
import org.tinygroup.weixinkf.kfaccount.EditCustomerAccount;
import org.tinygroup.weixinkf.kfmessage.ImageKfMessage;
import org.tinygroup.weixinkf.kfmessage.MusicKfMessage;
import org.tinygroup.weixinkf.kfmessage.NewsKfMessage;
import org.tinygroup.weixinkf.kfmessage.TextKfMessage;
import org.tinygroup.weixinkf.kfmessage.VideoKfMessage;
import org.tinygroup.weixinkf.kfmessage.VoiceKfMessage;
import org.tinygroup.weixinkf.kfmessage.item.ArticleItem;
import org.tinygroup.weixinkf.kfmessage.item.ImageJsonItem;
import org.tinygroup.weixinkf.kfmessage.item.MusicJsonItem;
import org.tinygroup.weixinkf.kfmessage.item.NewsJsonItem;
import org.tinygroup.weixinkf.kfmessage.item.TextJsonItem;
import org.tinygroup.weixinkf.kfmessage.item.VideoJsonItem;
import org.tinygroup.weixinkf.kfmessage.item.VoiceJsonItem;

public class KfAccountTest extends TestCase {

	/**
	 * 测试客服文本消息JSON串
	 */
	public void testTextReplyJson(){
		TextKfMessage json = new TextKfMessage();
		json.setToUser("oH7YcuDMbvNuwRMpWRu5BNOS21vU");
		TextJsonItem item = new TextJsonItem();
		item.setContent("12345");
		json.setTextJsonItem(item);
		Assert.assertEquals("{\"msgtype\":\"text\",\"text\":{\"content\":\"12345\"},\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\"}", json.toString());
		
		//设置客服信息
		CustomerServiceAccount account = new CustomerServiceAccount();
		account.setAccount("test1@Tiny_Framework");
		json.setKfAccount(account);
		Assert.assertEquals("{\"customservice\":{\"kf_account\":\"test1@Tiny_Framework\"},\"msgtype\":\"text\",\"text\":{\"content\":\"12345\"},\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\"}", json.toString());
	}
	
	/**
	 * 测试客服图片消息
	 */
	public void testImageReplyJson(){
		ImageKfMessage json = new ImageKfMessage();
		json.setToUser("oH7YcuDMbvNuwRMpWRu5BNOS21vU");
		ImageJsonItem item = new ImageJsonItem();
		item.setMediaId("is8SYTzv7JeYMBWru8t2Rgem5dVT52ECIvAX-Mh69uIHVxRZllPRAoX14fQrSxvW");
		json.setImageJsonItem(item);
		Assert.assertEquals("{\"image\":{\"media_id\":\"is8SYTzv7JeYMBWru8t2Rgem5dVT52ECIvAX-Mh69uIHVxRZllPRAoX14fQrSxvW\"},\"msgtype\":\"image\",\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\"}", json.toString());
		
		//设置客服信息
		CustomerServiceAccount account = new CustomerServiceAccount();
		account.setAccount("test2@Tiny_Framework");
		json.setKfAccount(account);
		Assert.assertEquals("{\"customservice\":{\"kf_account\":\"test2@Tiny_Framework\"},\"image\":{\"media_id\":\"is8SYTzv7JeYMBWru8t2Rgem5dVT52ECIvAX-Mh69uIHVxRZllPRAoX14fQrSxvW\"},\"msgtype\":\"image\",\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\"}", json.toString());
	}
	
	/**
	 * 测试客服语音消息
	 */
	public void testVoiceReplyJson(){
		VoiceKfMessage json = new VoiceKfMessage();
		json.setToUser("oH7YcuDMbvNuwRMpWRu5BNOS21vU");
		VoiceJsonItem item = new VoiceJsonItem();
		item.setMediaId("ZbRbFtmEDsJvHczP-0xVi5TviH1vyXjeniWkAhnZsY8HOWImBw_PLD3K9FJMbhGJ");
		json.setVoiceJsonItem(item);
	
		//Assert.assertEquals("",json.toString());
		Assert.assertEquals("{\"msgtype\":\"voice\",\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\",\"voice\":{\"media_id\":\"ZbRbFtmEDsJvHczP-0xVi5TviH1vyXjeniWkAhnZsY8HOWImBw_PLD3K9FJMbhGJ\"}}",json.toString());
		
		//设置客服信息
		CustomerServiceAccount account = new CustomerServiceAccount();
		account.setAccount("test2@Tiny_Framework");
		json.setKfAccount(account);
		Assert.assertEquals("{\"customservice\":{\"kf_account\":\"test2@Tiny_Framework\"},\"msgtype\":\"voice\",\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\",\"voice\":{\"media_id\":\"ZbRbFtmEDsJvHczP-0xVi5TviH1vyXjeniWkAhnZsY8HOWImBw_PLD3K9FJMbhGJ\"}}",json.toString());
	}
	
	/**
	 * 测试客服视频消息
	 */
	public void testVideoReplyJson(){
		VideoKfMessage json = new VideoKfMessage();
		json.setToUser("oH7YcuDMbvNuwRMpWRu5BNOS21vU");
		VideoJsonItem item = new VideoJsonItem();
		item.setMediaId("uV3yPCjsqL9FEMivzEBtYJl9qbgOWZtaRjEbDn0yp07kFFnGUJgwWaZV73_OYJSq");
		item.setThumbMediaId("G8RoT3ahJnu8cmS3pK_AC1-5RkMwFT6sgtIHPtYPa-SOU_ERCIeUCnrvDTfA2QcI");
		item.setTitle("世界真奇妙");
		json.setVideoJsonItem(item);
		
		
		Assert.assertEquals("{\"msgtype\":\"video\",\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\",\"video\":{\"media_id\":\"uV3yPCjsqL9FEMivzEBtYJl9qbgOWZtaRjEbDn0yp07kFFnGUJgwWaZV73_OYJSq\",\"thumb_media_id\":\"G8RoT3ahJnu8cmS3pK_AC1-5RkMwFT6sgtIHPtYPa-SOU_ERCIeUCnrvDTfA2QcI\",\"title\":\"世界真奇妙\"}}",json.toString());
		
		//设置客服信息
		CustomerServiceAccount account = new CustomerServiceAccount();
		account.setAccount("test2@Tiny_Framework");
		json.setKfAccount(account);
		Assert.assertEquals("{\"customservice\":{\"kf_account\":\"test2@Tiny_Framework\"},\"msgtype\":\"video\",\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\",\"video\":{\"media_id\":\"uV3yPCjsqL9FEMivzEBtYJl9qbgOWZtaRjEbDn0yp07kFFnGUJgwWaZV73_OYJSq\",\"thumb_media_id\":\"G8RoT3ahJnu8cmS3pK_AC1-5RkMwFT6sgtIHPtYPa-SOU_ERCIeUCnrvDTfA2QcI\",\"title\":\"世界真奇妙\"}}",json.toString());
		
	}
	
	/**
	 * 测试客服音乐消息
	 */
	public void testMusicReplyJson(){
		
		MusicKfMessage json = new MusicKfMessage();
		json.setToUser("oH7YcuDMbvNuwRMpWRu5BNOS21vU");
		MusicJsonItem item = new MusicJsonItem();
		json.setMusicJsonItem(item);
		item.setTitle("hello baby");
		item.setThumbMediaId("THUMB_MEDIA_ID");
		item.setMusicUrl("http://weixincourse-weixincourse.stor.sinaapp.com/mysongs.mp3");
		
		
		Assert.assertEquals("{\"msgtype\":\"music\",\"music\":{\"musicurl\":\"http://weixincourse-weixincourse.stor.sinaapp.com/mysongs.mp3\",\"thumb_media_id\":\"THUMB_MEDIA_ID\",\"title\":\"hello baby\"},\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\"}",json.toString());
		
		//设置客服信息
		CustomerServiceAccount account = new CustomerServiceAccount();
		account.setAccount("test2@Tiny_Framework");
		json.setKfAccount(account);
		
		Assert.assertEquals("{\"customservice\":{\"kf_account\":\"test2@Tiny_Framework\"},\"msgtype\":\"music\",\"music\":{\"musicurl\":\"http://weixincourse-weixincourse.stor.sinaapp.com/mysongs.mp3\",\"thumb_media_id\":\"THUMB_MEDIA_ID\",\"title\":\"hello baby\"},\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\"}",json.toString());
	}
	
	/**
	 * 测试客服图文消息
	 */
	public void testNewsReplyJson(){
		NewsKfMessage json = new NewsKfMessage();
		json.setToUser("oH7YcuDMbvNuwRMpWRu5BNOS21vU");
		NewsJsonItem item = new NewsJsonItem();
		ArticleItem a1 = new ArticleItem();
		ArticleItem a2 = new ArticleItem();
		
		a1.setTitle("new1");
		a1.setUrl("URL1");
		a2.setTitle("new2");
		a2.setUrl("URL2");
		
		item.getArticleList().add(a1);
		item.getArticleList().add(a2);
		json.setNewsJsonItem(item);
		
		Assert.assertEquals("{\"msgtype\":\"news\",\"news\":{\"articles\":[{\"title\":\"new1\",\"url\":\"URL1\"},{\"title\":\"new2\",\"url\":\"URL2\"}]},\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\"}",json.toString());
		
		//设置客服信息
		CustomerServiceAccount account = new CustomerServiceAccount();
		account.setAccount("test2@Tiny_Framework");
		json.setKfAccount(account);
		Assert.assertEquals("{\"customservice\":{\"kf_account\":\"test2@Tiny_Framework\"},\"msgtype\":\"news\",\"news\":{\"articles\":[{\"title\":\"new1\",\"url\":\"URL1\"},{\"title\":\"new2\",\"url\":\"URL2\"}]},\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\"}",json.toString());
	}
	
	/**
	 * 测试客服管理的客服操作对象
	 */
	public void testAddCustomerAccount(){
		AddCustomerAccount account = new AddCustomerAccount("test1@test","客服1","96e79218965eb72c92a549dd5a330112");
		Assert.assertEquals("{\"kf_account\":\"test1@test\",\"nickname\":\"客服1\",\"password\":\"96e79218965eb72c92a549dd5a330112\"}",account.toString());
	}
	
	/**
	 * 测试客服管理的客服操作对象
	 */
	public void testEditCustomerAccount(){
		EditCustomerAccount account = new EditCustomerAccount("test1@test","客服1","96e79218965eb72c92a549dd5a330112");
		Assert.assertEquals("{\"kf_account\":\"test1@test\",\"nickname\":\"客服1\",\"password\":\"96e79218965eb72c92a549dd5a330112\"}",account.toString());
	}
}
