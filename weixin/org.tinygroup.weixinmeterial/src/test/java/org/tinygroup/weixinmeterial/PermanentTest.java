package org.tinygroup.weixinmeterial;

import junit.framework.TestCase;

import org.tinygroup.weixinmeterial.message.DeletePermanentMeterial;
import org.tinygroup.weixinmeterial.message.EditPermanentNews;
import org.tinygroup.weixinmeterial.message.NewsItem;
import org.tinygroup.weixinmeterial.message.PermanentNewsMessage;

public class PermanentTest extends TestCase{

	public void testPermanentNewsMessage(){
		PermanentNewsMessage message = new PermanentNewsMessage();
		NewsItem newsItem = new NewsItem();
		newsItem.setShowCover("1");
		newsItem.setThumbMediaId("K1lU5S9zJ77eTPZBpV2i0MuYCoZWIe7ZdRCUI4JluZ8");
		newsItem.setContent("hello world");
		newsItem.setContentUrl("www.baidu.com");
		newsItem.setTitle("good");
		message.addNewsItem(newsItem);
		
		assertEquals("{\"articles\":[{\"author\":\"\",\"content\":\"hello world\",\"content_source_url\":\"www.baidu.com\",\"digest\":\"\",\"show_cover_pic\":\"1\",\"thumb_media_id\":\"K1lU5S9zJ77eTPZBpV2i0MuYCoZWIe7ZdRCUI4JluZ8\",\"title\":\"good\"}]}", message.toString());
	}
	
	public void testEditPermanentNews(){
		EditPermanentNews message = new EditPermanentNews();
		NewsItem newsItem = new NewsItem();
		newsItem.setShowCover("1");
		newsItem.setThumbMediaId("K1lU5S9zJ77eTPZBpV2i0MuYCoZWIe7ZdRCUI4JluZ8");
		newsItem.setContent("hello world");
		newsItem.setContentUrl("www.baidu.com");
		newsItem.setTitle("good");
		message.setNewsItem(newsItem);
		message.setIndex("0");
		message.setMediaId("5DTYiH_AWY4ZkU4ypN5dT1F0jDq_EPrB4DTnIqpjHIw");
		
		assertEquals("{\"articles\":{\"author\":\"\",\"content\":\"hello world\",\"content_source_url\":\"www.baidu.com\",\"digest\":\"\",\"show_cover_pic\":\"1\",\"thumb_media_id\":\"K1lU5S9zJ77eTPZBpV2i0MuYCoZWIe7ZdRCUI4JluZ8\",\"title\":\"good\"},\"index\":\"0\",\"media_id\":\"5DTYiH_AWY4ZkU4ypN5dT1F0jDq_EPrB4DTnIqpjHIw\"}",message.toString());
	}
	
	public void testDeletePermanentMeterial(){
		DeletePermanentMeterial message = new DeletePermanentMeterial("5DTYiH_AWY4ZkU4ypN5dTyZYd_QUcjpPsD3Er8QDLVU");
		assertEquals("{\"media_id\":\"5DTYiH_AWY4ZkU4ypN5dTyZYd_QUcjpPsD3Er8QDLVU\"}",message.toString());
	}
}
