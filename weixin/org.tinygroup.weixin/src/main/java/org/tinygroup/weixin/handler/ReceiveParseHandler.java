package org.tinygroup.weixin.handler;

import java.io.IOException;
import java.io.InputStream;

import org.tinygroup.commons.io.StreamUtil;
import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.WeiXinHandlerMode;
import org.tinygroup.weixin.exception.WeiXinException;
import org.tinygroup.weixin.util.WeiXinParserUtil;

/**
 * 默认微信消息转换
 * @author yancheng11334
 *
 */
public class ReceiveParseHandler extends AbstractWeiXinHandler {

	public WeiXinHandlerMode getWeiXinHandlerMode() {
		return WeiXinHandlerMode.RECEIVE;
	}

	public <T> boolean isMatch(T message,WeiXinContext context) {
		return message instanceof String || message instanceof InputStream;
	}

	public <T> void process(T message, WeiXinContext context) {
		if(message instanceof String){
		   String content =(String) message;
		   parseString(content,context);
		}else if(message instanceof InputStream){
			InputStream inputStream =(InputStream) message;
			try {
				parseString(StreamUtil.readText(inputStream, "UTF-8", false),context);
			} catch (IOException e) {
				throw new WeiXinException(e);
			}
		}
		
	}
	
	private void parseString(String content, WeiXinContext context){
		context.setInput(WeiXinParserUtil.parse(content,context,WeiXinConvertMode.RECEIVE));
	}
	
	/**
	 * 优先级高
	 */
	public int getPriority() {
		return Integer.MIN_VALUE+100;
	}


}
