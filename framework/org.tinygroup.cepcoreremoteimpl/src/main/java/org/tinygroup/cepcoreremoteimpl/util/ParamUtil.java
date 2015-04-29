package org.tinygroup.cepcoreremoteimpl.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.xmlparser.node.XmlNode;

public class ParamUtil {
	private static final String PARAM_KEY = "param";
	private static Map<String, String> paramsMap = new HashMap<String, String>();
	//请求超时的参数key
	private static final String REQUEST_TIME_OUT_KEY = "request-time-out";
	//请求超时时间
	private static long requestTimeOut = 0;
	//默认请求超时时间
	private static long DEFAUT_REQUEST_TIME_OUT = 5000;
	public static long getRequestTimeOut() {
		if (requestTimeOut == 0) {
			if (paramsMap.containsKey(REQUEST_TIME_OUT_KEY)) {
				String time = paramsMap.get(REQUEST_TIME_OUT_KEY);
				requestTimeOut = Long.parseLong(time);
			} else {
				requestTimeOut = DEFAUT_REQUEST_TIME_OUT;
			}
		}

		return requestTimeOut;
	}
	public static void parseParam(XmlNode paramNode) {
		List<XmlNode> params = paramNode.getSubNodes(PARAM_KEY);
		for (XmlNode param : params) {
			paramsMap.put(param.getAttribute("name"),
					param.getAttribute("value"));
		}
	}
}
