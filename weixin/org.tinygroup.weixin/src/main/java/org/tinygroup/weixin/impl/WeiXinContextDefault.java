package org.tinygroup.weixin.impl;

import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinSession;

/**
 * 默认的微信上下文实现
 * @author yancheng11334
 *
 */
public class WeiXinContextDefault extends ContextImpl implements WeiXinContext{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5255507949527540557L;

	public <INPUT> INPUT getInput() {
		return get(DEFAULT_INPUT_NAME);
	}

	public <OUTPUT> OUTPUT getOutput() {
		return get(DEFAULT_OUTPUT_NAME);
	}

	public <INPUT> void setInput(INPUT input) {
		put(DEFAULT_INPUT_NAME,input);
	}

	public <OUTPUT> void setOutput(OUTPUT output) {
		put(DEFAULT_OUTPUT_NAME,output);
	}

	public WeiXinSession getWeiXinSession() {
		return get(WEIXIN_SESSION);
	}

}
