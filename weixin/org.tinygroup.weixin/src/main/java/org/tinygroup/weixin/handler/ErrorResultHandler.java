package org.tinygroup.weixin.handler;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinHandlerMode;
import org.tinygroup.weixin.result.ErrorResult;

/**
 * 错误结果处理器
 * @author yancheng11334
 *
 */
public class ErrorResultHandler extends AbstractWeiXinHandler{

	public WeiXinHandlerMode getWeiXinHandlerMode() {
		return WeiXinHandlerMode.SEND_OUPUT;
	}

	public <T> boolean isMatch(T message, WeiXinContext context) {
		return message instanceof ErrorResult;
	}

	public <T> void process(T message, WeiXinContext context) {
		ErrorResult result = (ErrorResult) message;
		LOGGER.logMessage(LogLevel.ERROR, "errcode:{0},errmsg:{1},input:{2}", result.getErrCode(),result.getErrMsg(),context.getInput());
	}

}
