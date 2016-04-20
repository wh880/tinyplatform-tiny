package org.tinygroup.weixin.handler;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.WeiXinHandlerMode;
import org.tinygroup.weixin.util.WeiXinParserUtil;

/**
 * 默认请求微信的结果处理
 * @author yancheng11334
 *
 */
public class SendOutputParseHandler extends AbstractWeiXinHandler {

	public SendOutputParseHandler(){
		setPriority(Integer.MIN_VALUE+100);
	}
	public WeiXinHandlerMode getWeiXinHandlerMode() {
		return WeiXinHandlerMode.SEND_OUPUT;
	}

	public <T> boolean isMatch(T message,WeiXinContext context) {
		return message instanceof String;
	}

	public <T> void process(T message, WeiXinContext context) {
		String content =(String) message;
		//LOGGER.logMessage(LogLevel.INFO, "xml="+message);
		context.setOutput(WeiXinParserUtil.parse(content,context,WeiXinConvertMode.SEND));
	}
}
