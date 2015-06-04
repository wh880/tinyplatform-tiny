package org.tinygroup.springmvc.coc.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodFilter;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UrlPathHelper;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.springmvc.coc.ConventionHelper;
import org.tinygroup.springmvc.coc.CustomHandlerMethodResolver;
import org.tinygroup.springmvc.coc.Qualifier;
import org.tinygroup.springmvc.util.WebUtil;

/**
 * 
 * @author kevin.luy
 * @since 2014年7月23日
 */
public abstract class AbstractConventionHandlerMethodResolver implements
                                                             CustomHandlerMethodResolver {
    private final Map<String, Method> implicitHandlerMethods = new HashMap<String, Method>();
    private Qualifier                 qualifier;
    private ConventionHelper          conventionHelper;
    private Class<?>                  handlerType;

    public void setQualifier(Qualifier qualifier) {
        this.qualifier = qualifier;
    }

    public Class<?> getHandlerType() {
        return handlerType;
    }

    public AbstractConventionHandlerMethodResolver(Class<?> handlerType,
                                                   ConventionHelper conventionHelper) {
        this.handlerType = handlerType;
        this.conventionHelper = conventionHelper;
    }

    public Collection<Method> getAllHandlerMethods() {
        return implicitHandlerMethods.values();
    }

    /**
     * 如果请求uri有后缀，需要忽略
     */
    public Method getHandlerMethod(HttpServletRequest request) {

        String requestUri = getUrlPathHelper().getLookupPathForRequest(request);
        Method mtd = this.getHandlerMethodInternal(request,
            WebUtil.getUriWithoutExtension(requestUri));
        if (mtd != null) {
            request
                .removeAttribute(RestfulConventionHandlerMethodResolver.RESTFUL_CONVENTION_VIEW_PATH);
        }
        return mtd;
    }
    
    private Method getHandlerMethodInternal(HttpServletRequest request, String noExtPath) {
        return getHandlerMethod(generateUriKey(request, noExtPath));
    }

    protected String generateUriKey(HttpServletRequest request, String lookupPath) {
        return lookupPath;
    }

    protected Method getHandlerMethod(String urlKey) {
        return implicitHandlerMethods.get(urlKey);
    }

    protected UrlPathHelper getUrlPathHelper() {
        return this.conventionHelper.getUrlPathHelper();
    }

    protected PathMatcher getPathMatcher() {
        return this.conventionHelper.getPathMatcher();
    }

    protected void registerHandlerMethod(String urlKey, Method handlerMethod) {
        implicitHandlerMethods.put(urlKey, handlerMethod);
    }


    public Set<String> resolve() {
        final Set<String> urlList = new HashSet<String>();// for this handler
        String className = handlerType.getName();
        // 没有特殊用途声明的方法
        final StringBuilder uri = new StringBuilder("/");
        int lastpos = className.indexOf("." + conventionHelper.getHandlerStyle());
        int startpos = className.indexOf("web.");
        startpos += 5;
        String path = StringUtils.EMPTY;
        if (startpos < lastpos) {
            path = StringUtils.substring(className, startpos, lastpos);
            path = path.replace(".", "/");
            uri.append(path).append("/");
        }
        // uri-prefix. => /namespace/
        String resourcesName = conventionHelper.getHandlerName(getHandlerType());
        uri.append(resourcesName);

        ReflectionUtils.doWithMethods(getHandlerType(), new ReflectionUtils.MethodCallback() {
            public void doWith(Method method) {
                if (!qualify(method)) {
                    return;
                }
                // method should be public.
                if (!Modifier.isPublic(method.getModifiers())) {
                    return;
                }
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    return;
                } else if (method.isAnnotationPresent(InitBinder.class)) {
                    return;
                } else if (method.isAnnotationPresent(ModelAttribute.class)) {
                    return;
                } else {
                    List<String> urls = doResolve(uri.toString(), method);
                    if (!CollectionUtil.isEmpty(urls)) {
                        for (String url : urls){
                            urlList.add(url);
                        }
                    }
                }
            }
        }, new MethodFilter() {
            // ==剔除object.class方法
            public boolean matches(Method method) {
                if (method.getDeclaringClass() == Object.class) {
                    return false;
                }
                return true;
            }

        });
        return urlList;
    }

    protected boolean qualify(Method method) {
        if (qualifier == null)
            return true;
        return qualifier.qualify(method);
    }

    protected abstract List<String> doResolve(String uriBase, Method method);

    public Set<String> getAllResolvedUrls() {
        return implicitHandlerMethods.keySet();
    }

    protected boolean isPathMatchInternal(String pattern, String lookupPath) {
        if (pattern.equals(lookupPath) || getPathMatcher().match(pattern, lookupPath)) {
            return true;
        }
        boolean hasSuffix = pattern.indexOf('.') != -1;
        if (!hasSuffix && getPathMatcher().match(pattern + ".*", lookupPath)) {
            return true;
        }
        boolean endsWithSlash = pattern.endsWith("/");
        if (!endsWithSlash && getPathMatcher().match(pattern + "/", lookupPath)) {
            return true;
        }
        return false;
    }
}
