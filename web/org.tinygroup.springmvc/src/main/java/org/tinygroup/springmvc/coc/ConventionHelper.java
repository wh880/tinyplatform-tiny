package org.tinygroup.springmvc.coc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UrlPathHelper;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.springmvc.coc.impl.ConventionHandlerMethodResolver;

/**
 * 
 * @author renhui
 *
 */
public class ConventionHelper{
    public static final String CONVENTION_RESOURCE_NAME = ConventionHelper.class
            .getName()
            + "_RESOURCE_NAME";

    private PathMatcher pathMatcher = new AntPathMatcher();
    private UrlPathHelper urlPathHelper = new UrlPathHelper();
    private Map<Class<?>, CustomHandlerMethodResolver> conventionHandlerMethodResolverRegistry = new HashMap<Class<?>, CustomHandlerMethodResolver>();
    private final Set<String> CONVENTIONAL_URLS = new HashSet<String>();
    private ConventionComponentIdentifier conventionComponentIdentifier;

    public void setConventionComponentIdentifier(ConventionComponentIdentifier conventionComponentIdentifier) {
        this.conventionComponentIdentifier = conventionComponentIdentifier;
    }

    protected CustomHandlerMethodResolver newCustomHandlerMethodResolver(Class<?> handlerType) {
        return new ConventionHandlerMethodResolver(handlerType, this);
    }

    public boolean isConventional(String url) {
        return CONVENTIONAL_URLS.contains(url);
    }

    public PathMatcher getPathMatcher() {
        return pathMatcher;
    }

    public UrlPathHelper getUrlPathHelper() {
        return urlPathHelper;
    }

    public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
        this.urlPathHelper = urlPathHelper;
    }

    public void setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public boolean isHandler(Object obj) {
        return conventionComponentIdentifier.isComponent(obj.getClass().getName());
    }


    // with no @requestMapping typelevel
    public String[] determineUrl(Object handler) {
        // already check. return null;
        CustomHandlerMethodResolver conventionHandlerMethodResolver = conventionHandlerMethodResolverRegistry
                .get(handler.getClass());
        if (conventionHandlerMethodResolver != null) {
            return null;
        }

        conventionHandlerMethodResolver = newCustomHandlerMethodResolver(handler.getClass());
        Set<String> urlSet = conventionHandlerMethodResolver.resolve();
        if (!CollectionUtil.isEmpty(urlSet)) {
            // set to cache
            conventionHandlerMethodResolverRegistry.put(handler.getClass(),
                    conventionHandlerMethodResolver);

            CONVENTIONAL_URLS.addAll(urlSet);
            String[] urls = new String[urlSet.size()];
            urlSet.toArray(urls);
            return urls;
        }
        return null;
    }

    public CustomHandlerMethodResolver getConventionHandlerMethodResolver(Class<?> handlerType) {
        return conventionHandlerMethodResolverRegistry.get(handlerType);
    }

    public String getHandlerName(Class<?> handlerType) {
        String sn = handlerType.getSimpleName();
        sn = StringUtils.uncapitalize(sn);
        int idx = sn.indexOf(StringUtils.capitalize(getHandlerStyle()));
        return sn.substring(0, idx);
    }

    public String getHandlerName(Object handler) {
        return getHandlerName(handler.getClass());
    }


    public String getHandlerStyle() {
        return "controller";
    }

}
