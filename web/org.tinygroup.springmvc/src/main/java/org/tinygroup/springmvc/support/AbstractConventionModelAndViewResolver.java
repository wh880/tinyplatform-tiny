package org.tinygroup.springmvc.support;

import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.util.UrlPathHelper;
import org.tinygroup.springmvc.coc.ConventionHelper;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 普通约定的mv方案
 * 
 */
public class AbstractConventionModelAndViewResolver implements ModelAndViewResolver {

    private UrlPathHelper urlPathHelper;

    public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
        this.urlPathHelper = urlPathHelper;
    }

    public ModelAndView resolveModelAndView(Method handlerMethod, Class handlerType,
                                            Object returnValue, ExtendedModelMap implicitModel,
                                            NativeWebRequest webRequest) {
        String viewName = null;
        if (!(returnValue instanceof String)) {
            return ModelAndViewResolver.UNRESOLVED;
        }
        viewName = returnValue.toString();

        if (isNotProcessable(webRequest, viewName)) {
            return ModelAndViewResolver.UNRESOLVED;
        }
        // 如果是约定，才要补充完整的约定路径
        if (!isConventionProcessed(webRequest)) {
            return ModelAndViewResolver.UNRESOLVED;
        }

        if (viewName.startsWith("/")) {
            viewName = viewName.substring(1);
        }
        // /users=>users/index.vm , /users/xx(.yy)=> users/index.vm
        String path = urlPathHelper.getLookupPathForRequest(webRequest
            .getNativeRequest(HttpServletRequest.class));
        path = getViewPath(webRequest, path, viewName);
        return new ModelAndView(path).addAllObjects(implicitModel);

    }

    protected String getViewPath(NativeWebRequest webRequest, String requestUri, String viewName) {
        int pos = requestUri.lastIndexOf('/');
        return requestUri.toLowerCase().substring(0, pos + 1) + viewName;
    }

    protected boolean isNotProcessable(NativeWebRequest webRequest, String viewName) {
        return viewName.startsWith(UrlBasedViewResolver.FORWARD_URL_PREFIX)
               || viewName.startsWith(UrlBasedViewResolver.REDIRECT_URL_PREFIX);
    }

    protected boolean isConventionProcessed(NativeWebRequest webRequest) {
        Object obj = webRequest.getAttribute(ConventionHelper.CONVENTION_RESOURCE_NAME,
            RequestAttributes.SCOPE_REQUEST);
        return obj == null ? false : true;
    }

}
