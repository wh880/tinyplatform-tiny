package org.tinygroup.urlrestful.handler;

import java.util.Map;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.urlrestful.Context;
import org.tinygroup.urlrestful.UrlRestfulManager;
import org.tinygroup.weblayer.WebContext;
import org.tinygroup.weblayer.webcontext.parser.valueparser.ParameterParser;
import org.tinygroup.weblayer.webcontext.rewrite.RewriteSubstitutionContext;
import org.tinygroup.weblayer.webcontext.rewrite.RewriteSubstitutionHandler;

/**
 * restful风格字符串替换处理
 *
 * @author renhui
 */
public class RestfulStyleSubstitutionHandler implements
        RewriteSubstitutionHandler {

    private static final String Accept = "Accept";
    private static final String HTTP_METHOD_KEY = "X-HTTP-METHOD-OVERRIDE";

    private UrlRestfulManager urlRestfulManager;

    public void setUrlRestfulManager(UrlRestfulManager urlRestfulManager) {
        this.urlRestfulManager = urlRestfulManager;
    }

    /**
     * 先获取原来的请求路径与UrlRestful的配置进行匹配，把匹配的值放到上下文中，最后重新设置请求的路径。
     */
    public void postSubstitution(RewriteSubstitutionContext context) {
        String orignalPath = context.getPath();
        String httpMethod = getHttpMethod(context);
        String requestAccept = context.getParserWebContext().get(Accept);
        Context restfulContext = urlRestfulManager
                .getContext(orignalPath, httpMethod, requestAccept);
        if (restfulContext != null) {
            ParameterParser parameterParser = context.getParameters();
            setParameter(parameterParser, restfulContext.getVariableMap());
            context.setPath(restfulContext.getMappingUrl());
        }

    }

    private String getHttpMethod(RewriteSubstitutionContext context) {
        WebContext webContext = context.getParserWebContext();
        String httpMethod = webContext.get(HTTP_METHOD_KEY);
        if (StringUtil.isBlank(httpMethod)) {
            httpMethod = webContext.getRequest().getMethod();
        }
        return httpMethod;
    }

    private void setParameter(ParameterParser parameterParser,
                              Map<String, String> variableMap) {
        if (!CollectionUtil.isEmpty(variableMap)) {
            for (String key : variableMap.keySet()) {
                String value = variableMap.get(key);
                if (!StringUtil.isBlank(value)) {
                    String[] parameterValues = value.split(",");
                    if (parameterValues.length > 1) {
                        parameterParser.setObjects(key, parameterValues);
                    } else {
                        parameterParser.setObject(key, value);
                    }
                }
            }
        }
    }

}
