package org.tinygroup.weixin.util;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvert;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.JsonParser;
import org.tinygroup.weixin.convert.TextParser;
import org.tinygroup.weixin.convert.XmlParser;
import org.tinygroup.weixin.convert.json.AccessTokenConvert;
import org.tinygroup.weixin.convert.json.ErrorResultConvert;
import org.tinygroup.weixin.convert.json.JsApiTicketConvert;
import org.tinygroup.weixin.convert.json.OauthTokenResultConvert;
import org.tinygroup.weixin.convert.xml.ClickEventConvert;
import org.tinygroup.weixin.convert.xml.EncryptMessageConvert;
import org.tinygroup.weixin.convert.xml.ImageMessageConvert;
import org.tinygroup.weixin.convert.xml.LinkMessageConvert;
import org.tinygroup.weixin.convert.xml.LocationEventConvert;
import org.tinygroup.weixin.convert.xml.LocationMessageConvert;
import org.tinygroup.weixin.convert.xml.MessageSendFinishEventConvert;
import org.tinygroup.weixin.convert.xml.ScanAlertEventConvert;
import org.tinygroup.weixin.convert.xml.ScanEventConvert;
import org.tinygroup.weixin.convert.xml.ScanPushEventConvert;
import org.tinygroup.weixin.convert.xml.SelectLocationEventConvert;
import org.tinygroup.weixin.convert.xml.ShortVideoMessageConvert;
import org.tinygroup.weixin.convert.xml.SubscribeEventConvert;
import org.tinygroup.weixin.convert.xml.TextMessageConvert;
import org.tinygroup.weixin.convert.xml.UnSubscribeEventConvert;
import org.tinygroup.weixin.convert.xml.VideoMessageConvert;
import org.tinygroup.weixin.convert.xml.ViewEventConvert;
import org.tinygroup.weixin.convert.xml.VoiceMessageConvert;

/**
 * 微信信息解析工具
 * @author yancheng11334
 *
 */
public class WeiXinParserUtil {
	
	private static JsonParser jsonParser=new JsonParser();
	private static XmlParser  xmlParser = new XmlParser();
	private static TextParser  textParser = new TextParser();
	
	static{
		//注册推送消息
		xmlParser.addWeiXinConvert(new EncryptMessageConvert());
		xmlParser.addWeiXinConvert(new TextMessageConvert());
		xmlParser.addWeiXinConvert(new ImageMessageConvert());
		xmlParser.addWeiXinConvert(new VideoMessageConvert());
		xmlParser.addWeiXinConvert(new VoiceMessageConvert());
		xmlParser.addWeiXinConvert(new ShortVideoMessageConvert());
		xmlParser.addWeiXinConvert(new LocationMessageConvert());
		xmlParser.addWeiXinConvert(new LinkMessageConvert());
		
		//注册推送事件
		xmlParser.addWeiXinConvert(new SubscribeEventConvert());
		xmlParser.addWeiXinConvert(new UnSubscribeEventConvert());
		xmlParser.addWeiXinConvert(new ScanEventConvert());
		xmlParser.addWeiXinConvert(new LocationEventConvert());
		xmlParser.addWeiXinConvert(new ClickEventConvert());
		xmlParser.addWeiXinConvert(new ViewEventConvert());
		xmlParser.addWeiXinConvert(new MessageSendFinishEventConvert());
		xmlParser.addWeiXinConvert(new ScanAlertEventConvert());
		xmlParser.addWeiXinConvert(new ScanPushEventConvert());
		xmlParser.addWeiXinConvert(new SelectLocationEventConvert());
		
		//注册响应结果
		jsonParser.addWeiXinConvert(new OauthTokenResultConvert());
		jsonParser.addWeiXinConvert(new AccessTokenConvert());
		jsonParser.addWeiXinConvert(new JsApiTicketConvert());
		jsonParser.addWeiXinConvert(new ErrorResultConvert());
	}
	
	public static void addXmlConvert(WeiXinConvert convert){
		xmlParser.addWeiXinConvert(convert);
	}
	
    public static void removeXmlConvert(WeiXinConvert convert){
    	xmlParser.removeWeiXinConvert(convert);
	}
    
    public static void addJsonConvert(WeiXinConvert convert){
    	jsonParser.addWeiXinConvert(convert);
	}
	
    public static void removeJsonConvert(WeiXinConvert convert){
    	jsonParser.removeWeiXinConvert(convert);
	}
    
    public static void addTextConvert(WeiXinConvert convert){
    	textParser.addWeiXinConvert(convert);
	}
	
    public static void removeTextConvert(WeiXinConvert convert){
    	textParser.removeWeiXinConvert(convert);
	}
    
	/**
	 * 解析信息
	 * @param <T>
	 * @param content
	 * @return
	 */
	public static <T> T parse(String content,WeiXinContext context,WeiXinConvertMode mode){
		if(checkJson(content)){
		   return jsonParser.parse(content,context,mode);
		}else if(checkXml(content)){
		   return xmlParser.parse(content,context,mode);
		}else{
		   return textParser.parse(content,context,mode);
		}
		
	}
	
	/**
	 * 简单验证json
	 * @param s
	 * @return
	 */
	private static boolean checkJson(String s){
		if(s!=null){
		   s = s.trim();
		   return s.startsWith("{") && s.endsWith("}");
		}
		return false;
	}
	
	private static boolean checkXml(String s){
		if(s!=null){
		   s = s.trim();
		   return s.startsWith("<xml>") && s.endsWith("</xml>");
		}
	    return false;
	}
}
