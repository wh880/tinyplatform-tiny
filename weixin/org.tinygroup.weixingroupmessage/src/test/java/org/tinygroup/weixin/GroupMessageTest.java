package org.tinygroup.weixin;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.tinygroup.weixingroupmessage.DeleteGroupMessage;
import org.tinygroup.weixingroupmessage.ImageFilterMessage;
import org.tinygroup.weixingroupmessage.ImagePreviewMessage;
import org.tinygroup.weixingroupmessage.ImageUserMessage;
import org.tinygroup.weixingroupmessage.NewsFilterMessage;
import org.tinygroup.weixingroupmessage.NewsPreviewMessage;
import org.tinygroup.weixingroupmessage.NewsUserMessage;
import org.tinygroup.weixingroupmessage.QueryGroupMessage;
import org.tinygroup.weixingroupmessage.TextFilterMessage;
import org.tinygroup.weixingroupmessage.TextPreviewMessage;
import org.tinygroup.weixingroupmessage.TextUserMessage;
import org.tinygroup.weixingroupmessage.VideoPreviewMessage;
import org.tinygroup.weixingroupmessage.VoiceFilterMessage;
import org.tinygroup.weixingroupmessage.VoicePreviewMessage;
import org.tinygroup.weixingroupmessage.VoiceUserMessage;
import org.tinygroup.weixingroupmessage.item.FilterJsonItem;

/**
 * 群消息测试用例
 * @author yancheng11334
 *
 */
public class GroupMessageTest extends TestCase {

	/**
	 * 测试基于OpenId列表的文本消息
	 */
	public void testTextUserMessage(){
		TextUserMessage message = new TextUserMessage("群发文本消息");
		message.getOpenIds().add("openid1");
		message.getOpenIds().add("openid2");
		Assert.assertEquals("{\"msgtype\":\"text\",\"text\":{\"content\":\"群发文本消息\"},\"touser\":[\"openid1\",\"openid2\"]}", message.toString());
	}
	
	/**
	 * 测试基于OpenId列表的图片消息
	 */
	public void testImageUserMessage(){
		ImageUserMessage message = new ImageUserMessage("mLxl6paC7z2Tl-NJT64yzJve8T9c8u9K2x-Ai6Ujd4lIH9IBuF6-2r66mamn_gIT");
		message.getOpenIds().add("openid1");
		message.getOpenIds().add("openid2");
		//System.out.println(message.toString());
		Assert.assertEquals("{\"image\":{\"media_id\":\"mLxl6paC7z2Tl-NJT64yzJve8T9c8u9K2x-Ai6Ujd4lIH9IBuF6-2r66mamn_gIT\"},\"msgtype\":\"image\",\"touser\":[\"openid1\",\"openid2\"]}", message.toString());
	}
	
	/**
	 * 测试基于OpenId列表的语音消息
	 */
	public void testVoiceUserMessage(){
		VoiceUserMessage message = new VoiceUserMessage("mLxl6paC7z2Tl-NJT64yzJve8T9c8u9K2x-Ai6Ujd4lIH9IBuF6-2r66mamn_gIT");
		message.getOpenIds().add("openid1");
		message.getOpenIds().add("openid2");
		//System.out.println(message.toString());
		Assert.assertEquals("{\"msgtype\":\"voice\",\"touser\":[\"openid1\",\"openid2\"],\"voice\":{\"media_id\":\"mLxl6paC7z2Tl-NJT64yzJve8T9c8u9K2x-Ai6Ujd4lIH9IBuF6-2r66mamn_gIT\"}}", message.toString());
	}
	
	/**
	 * 测试基于OpenId列表的图文消息
	 */
	public void testNewsUserMessage(){
		NewsUserMessage message = new NewsUserMessage("123dsdajkasd231jhksad");
		message.getOpenIds().add("openid1");
		message.getOpenIds().add("openid2");
		//System.out.println(message.toString());
		Assert.assertEquals("{\"mpnews\":{\"media_id\":\"123dsdajkasd231jhksad\"},\"msgtype\":\"mpnews\",\"touser\":[\"openid1\",\"openid2\"]}", message.toString());
	}
	
	/**
	 * 测试基于分组的文本消息
	 */
	public void testTextFilterMessage(){
		TextFilterMessage message = new TextFilterMessage("群发文本消息");
		FilterJsonItem filter = new FilterJsonItem();
		filter.setGroupId("10");
		filter.setToAll(true);
		message.setFilterJsonItem(filter);
		
		Assert.assertEquals("{\"filter\":{\"group_id\":\"10\",\"is_to_all\":true},\"msgtype\":\"text\",\"text\":{\"content\":\"群发文本消息\"}}", message.toString());
	}
	
	/**
	 * 测试基于分组的图片消息
	 */
	public void testImageFilterMessage(){
		ImageFilterMessage message = new ImageFilterMessage("mLxl6paC7z2Tl-NJT64yzJve8T9c8u9K2x-Ai6Ujd4lIH9IBuF6-2r66mamn_gIT");
		FilterJsonItem filter = new FilterJsonItem();
		filter.setGroupId("10");
		filter.setToAll(true);
		message.setFilterJsonItem(filter);
		
		//System.out.println(message.toString());
		Assert.assertEquals("{\"filter\":{\"group_id\":\"10\",\"is_to_all\":true},\"image\":{\"media_id\":\"mLxl6paC7z2Tl-NJT64yzJve8T9c8u9K2x-Ai6Ujd4lIH9IBuF6-2r66mamn_gIT\"},\"msgtype\":\"image\"}", message.toString());
	}
	
	/**
	 * 测试基于分组的语音消息
	 */
	public void testVoiceFilterMessage(){
		VoiceFilterMessage message = new VoiceFilterMessage("mLxl6paC7z2Tl-NJT64yzJve8T9c8u9K2x-Ai6Ujd4lIH9IBuF6-2r66mamn_gIT");
		FilterJsonItem filter = new FilterJsonItem();
		filter.setGroupId("10");
		filter.setToAll(true);
		message.setFilterJsonItem(filter);
		
		//System.out.println(message.toString());
		Assert.assertEquals("{\"filter\":{\"group_id\":\"10\",\"is_to_all\":true},\"msgtype\":\"voice\",\"voice\":{\"media_id\":\"mLxl6paC7z2Tl-NJT64yzJve8T9c8u9K2x-Ai6Ujd4lIH9IBuF6-2r66mamn_gIT\"}}", message.toString());
	}
	
	/**
	 * 测试基于分组的图文消息
	 */
	public void testNewsFilterMessage(){
		NewsFilterMessage message = new NewsFilterMessage("123dsdajkasd231jhksad");
		FilterJsonItem filter = new FilterJsonItem();
		filter.setGroupId("10");
		filter.setToAll(true);
		message.setFilterJsonItem(filter);
		
		Assert.assertEquals("{\"filter\":{\"group_id\":\"10\",\"is_to_all\":true},\"mpnews\":{\"media_id\":\"123dsdajkasd231jhksad\"},\"msgtype\":\"mpnews\"}", message.toString());
	}
	
	/**
	 * 测试删除群发消息
	 */
	public void testDeleteGroupMessage(){
		DeleteGroupMessage message = new DeleteGroupMessage("2547661204");
		Assert.assertEquals("{\"media_id\":\"2547661204\"}",message.toString());
	}
	
	/**
	 * 测试查询消息状态
	 */
	public void testQueryGroupMessage(){
		QueryGroupMessage message = new QueryGroupMessage("2547661222");
		Assert.assertEquals("{\"media_id\":\"2547661222\"}",message.toString());
	}
	
	/**
	 * 测试图片预览消息
	 */
	public void testImagePreviewMessage(){
		ImagePreviewMessage message = new ImagePreviewMessage("oH7YcuDMbvNuwRMpWRu5BNOS21vU","K1lU5S9zJ77eTPZBpV2i0KRfb64Sfs3N6SvmLNr1ffI");
		Assert.assertEquals("{\"image\":{\"media_id\":\"K1lU5S9zJ77eTPZBpV2i0KRfb64Sfs3N6SvmLNr1ffI\"},\"msgtype\":\"image\",\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\"}",message.toString());
	}
	
	/**
	 * 测试图文预览消息
	 */
	public void testNewsPreviewMessage(){
		NewsPreviewMessage message = new NewsPreviewMessage("oH7YcuDMbvNuwRMpWRu5BNOS21vU","5DTYiH_AWY4ZkU4ypN5dT1F0jDq_EPrB4DTnIqpjHIw");
		Assert.assertEquals("{\"mpnews\":{\"media_id\":\"5DTYiH_AWY4ZkU4ypN5dT1F0jDq_EPrB4DTnIqpjHIw\"},\"msgtype\":\"mpnews\",\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\"}",message.toString());
	}
	
	/**
	 * 测试文本预览消息
	 */
	public void testTextPreviewMessage(){
		TextPreviewMessage message = new TextPreviewMessage("oH7YcuDMbvNuwRMpWRu5BNOS21vU","hello world");
		Assert.assertEquals("{\"msgtype\":\"text\",\"text\":{\"content\":\"hello world\"},\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\"}",message.toString());
	}
	
	/**
	 * 测试语音预览消息
	 */
	public void testVoicePreviewMessage(){
		VoicePreviewMessage message = new VoicePreviewMessage("oH7YcuDMbvNuwRMpWRu5BNOS21vU","K1lU5S9zJ77eTPZBpV2i0F6aGLnIVEvRHRqbuxtUNIY");
		Assert.assertEquals("{\"msgtype\":\"voice\",\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\",\"voice\":{\"media_id\":\"K1lU5S9zJ77eTPZBpV2i0F6aGLnIVEvRHRqbuxtUNIY\"}}",message.toString());
	}
	
	/**
	 * 测试视频预览消息
	 */
	public void testVideoPreviewMessage(){
		VideoPreviewMessage message = new VideoPreviewMessage("oH7YcuDMbvNuwRMpWRu5BNOS21vU","K1lU5S9zJ77eTPZBpV2i0NPMm3g7f06xQ1QibDPFyao");
		Assert.assertEquals("{\"mpvideo\":{\"media_id\":\"K1lU5S9zJ77eTPZBpV2i0NPMm3g7f06xQ1QibDPFyao\"},\"msgtype\":\"mpvideo\",\"touser\":\"oH7YcuDMbvNuwRMpWRu5BNOS21vU\"}",message.toString());
	}
}
