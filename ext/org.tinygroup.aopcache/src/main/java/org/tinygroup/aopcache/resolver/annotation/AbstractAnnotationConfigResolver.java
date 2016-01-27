package org.tinygroup.aopcache.resolver.annotation;

import org.springframework.beans.factory.InitializingBean;
import org.tinygroup.aopcache.AnnotationConfigResolver;
import org.tinygroup.aopcache.resolver.AnnotationCacheActionResolver;
import org.tinygroup.commons.tools.Assert;

public abstract class AbstractAnnotationConfigResolver implements
        AnnotationConfigResolver, InitializingBean {
    protected static final String SPLIT_KEY = ",";

    private AnnotationCacheActionResolver actionResolver;

    public void setActionResolver(AnnotationCacheActionResolver actionResolver) {
        this.actionResolver = actionResolver;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.assertNotNull(actionResolver,
                "AnnotationCacheActionResolver must be not null");
        actionResolver.addConfigResolver(this);
    }

}
