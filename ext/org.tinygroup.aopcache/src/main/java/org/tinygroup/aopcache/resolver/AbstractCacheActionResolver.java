package org.tinygroup.aopcache.resolver;

import org.springframework.beans.factory.InitializingBean;
import org.tinygroup.aopcache.CacheActionResolver;
import org.tinygroup.aopcache.interceptor.AopCacheInterceptor;
import org.tinygroup.commons.tools.Assert;

public abstract class AbstractCacheActionResolver implements CacheActionResolver,
        InitializingBean {

    private int order;
    private AopCacheInterceptor interceptor;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setInterceptor(AopCacheInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.assertNotNull(interceptor, "AopCacheInterceptor must not be null");
        interceptor.addResolver(this);
    }


}
