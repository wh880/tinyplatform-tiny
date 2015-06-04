package org.tinygroup.springmvc.coc.impl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.springmvc.coc.ConventionHelper;

public class RestfulConventionHandlerMethodResolver extends AbstractConventionHandlerMethodResolver {
    //    private static final Log   logger                       = LogFactory
    //                                                                .getLog(RestfulConventionHandlerMethodResolver.class);
    public static final String RESTFUL_CONVENTION_VIEW_PATH = RestfulConventionHandlerMethodResolver.class
            .getName() + "_VIEW_PATH";

    public RestfulConventionHandlerMethodResolver(Class<?> handlerType,
                                                  ConventionHelper conventionHelper) {
        super(handlerType, conventionHelper);
    }

    protected String generateUriKey(HttpServletRequest request, String lookupPath) {
        return new StringBuilder(request.getMethod()).append(" ").append(lookupPath).toString();
    }

    @Override
    public Method getHandlerMethod(HttpServletRequest request) {
        Method mtd = super.getHandlerMethod(request);
        if (mtd != null) {
            request.setAttribute(RESTFUL_CONVENTION_VIEW_PATH, StringUtils.lowerCase(mtd.getName())
                    .substring(2));// "doxx" skip "do"
        }
        return mtd;
    }

    protected Method getHandlerMethod(String urlKey) {
        Method mtd = super.getHandlerMethod(urlKey);
        if (mtd != null) {
            return mtd;
        }
        // 不能直接获取，尝试模式匹配（主要是RESTFUL的参数化路径情况）
        for (String key : super.getAllResolvedUrls()) {
            if (this.isPathMatchInternal(key, urlKey)) {
                return super.getHandlerMethod(key);
            }
        }
        return null;
    }

    protected List<String> doResolve(String uri, Method method) {
        String[] urls = this.doResolveInner(uri, method);
        @SuppressWarnings("unchecked")
        List<String> urlList = Collections.EMPTY_LIST;
        if (urls != null && urls.length > 0) {
            return Arrays.asList(urls);
        }
        return urlList;
    }

    private String[] doResolveInner(String uri, Method method) {
        String hmn = method.getName();
        String[] urls = null;
        if ("doIndex".equals(hmn)) {
            return this.registerUrls(HttpMethod.GET, method, uri);
        }
        if ("doNew".equals(hmn)) {
            String url = new StringBuilder(uri).append("/new").toString();
            return this.registerUrls(HttpMethod.GET, method, url);

        }
        if ("doCreate".equals(hmn)) {
            return this.registerUrls(HttpMethod.POST, method, uri);
        }
        if ("doShow".equals(hmn)) {
            String url = new StringBuilder(uri).append("/{id}").toString();
            return this.registerUrls(HttpMethod.GET, method, url);
        }
        if ("doEdit".equals(hmn)) {
            String url = new StringBuilder(uri).append("/{id}/edit").toString();
            return this.registerUrls(HttpMethod.GET, method, url);
        }
        if ("doUpdate".equals(hmn)) {
            String url = new StringBuilder(uri).append("/{id}").toString();
            return this.registerUrls(HttpMethod.PUT, method, url);
        }
        if ("doDestroy".equals(hmn)) {
            String url = new StringBuilder(uri).append("/{id}").toString();
            return this.registerUrls(HttpMethod.DELETE, method, url);
        }
        return urls;
    }

    private String[] registerUrls(HttpMethod httpMethod, Method handlerMethod, String url) {
        this.registerHandlerMethod(new StringBuilder(httpMethod.name()).append(" ").append(url)
                .toString(), handlerMethod);

        String _url = StringUtil.toLowerCaseWithUnderscores(url);
        if (!StringUtils.equals(url, _url)) {
            this.registerHandlerMethod(new StringBuilder(httpMethod.name()).append(" ")
                    .append(_url).toString(), handlerMethod);
            return new String[]{url, _url};
        } else {
            return new String[]{url};
        }
    }

}
