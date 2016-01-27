package org.tinygroup.aopcache.processor;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;
import org.tinygroup.aopcache.AopCacheProcessor;
import org.tinygroup.aopcache.base.CacheMetadata;
import org.tinygroup.aopcache.interceptor.AopCacheInterceptor;
import org.tinygroup.cache.Cache;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

/**
 * 抽象的缓存aop处理
 *
 * @author renhui
 */
public abstract class AbstractAopCacheProcessor implements AopCacheProcessor, InitializingBean {

    protected static final String SPLIT_KEY = ",";
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractAopCacheProcessor.class);
    private AopCacheInterceptor interceptor;

    public void setInterceptor(AopCacheInterceptor interceptor) {
        this.interceptor = interceptor;
    }


    public void afterPropertiesSet() throws Exception {
        Assert.assertNotNull(interceptor, "AopCacheInterceptor must not be null");
        interceptor.addProcessor(this);
    }

    public boolean preProcess(CacheMetadata metadata,
                              MethodInvocation invocation) {
        checkMetadata(metadata);
        return doPreProcess(metadata, invocation);
    }


    protected boolean doPreProcess(CacheMetadata metadata,
                                   MethodInvocation invocation) {
        return true;
    }


    protected void checkMetadata(CacheMetadata metadata) {
    }


    public void postProcess(CacheMetadata metadata, MethodInvocation invocation, Object result) {

    }


    public Object endProcessor(CacheMetadata metadata,
                               MethodInvocation invocation) {
        return null;
    }

    public Cache getAopCache() {
        return interceptor.getCache();
    }
}
