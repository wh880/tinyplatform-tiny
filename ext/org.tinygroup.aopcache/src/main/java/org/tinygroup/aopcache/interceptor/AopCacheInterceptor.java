package org.tinygroup.aopcache.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.ParameterNameDiscoverer;
import org.tinygroup.aopcache.AopCacheExecutionChain;
import org.tinygroup.aopcache.AopCacheProcessor;
import org.tinygroup.aopcache.CacheActionResolver;
import org.tinygroup.aopcache.base.AopCacheHolder;
import org.tinygroup.aopcache.base.CacheMetadata;
import org.tinygroup.aopcache.config.CacheAction;
import org.tinygroup.aopcache.exception.AopCacheException;
import org.tinygroup.aopcache.util.TemplateUtil;
import org.tinygroup.cache.Cache;
import org.tinygroup.commons.tools.CollectionUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * aop缓存拦截器,是功能的入口
 *
 * @author renhui
 */
public class AopCacheInterceptor implements MethodInterceptor {

    private List<CacheActionResolver> resolvers = new ArrayList<CacheActionResolver>();

    private Map<Class<? extends AopCacheProcessor>, AopCacheProcessor> processorMap = new HashMap<Class<? extends AopCacheProcessor>, AopCacheProcessor>();

    private Cache cache;

    private ParameterNameDiscoverer parameterNameDiscoverer;

    public ParameterNameDiscoverer getParameterNameDiscoverer() {
        return parameterNameDiscoverer;
    }

    public void setParameterNameDiscoverer(
            ParameterNameDiscoverer parameterNameDiscoverer) {
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public void setResolvers(List<CacheActionResolver> resolvers) {
        this.resolvers = resolvers;
    }

    public void setProcessorMap(Map<String, AopCacheProcessor> processorMap) {
        if (!CollectionUtil.isEmpty(processorMap)) {
            for (String processorType : processorMap.keySet()) {
                try {
                    Class<?> clazz = Class.forName(processorType);
                    if (!AopCacheProcessor.class.isAssignableFrom(clazz)) {
                        throw new AopCacheException(String.format(
                                "类型：%s，不是AopCacheProcessor类型", processorType));
                    }
                    this.processorMap.put(
                            (Class<? extends AopCacheProcessor>) clazz,
                            processorMap.get(processorType));
                } catch (ClassNotFoundException e) {
                    throw new AopCacheException(e);
                }
            }
        }
    }

    public void addResolver(CacheActionResolver resolver) {
        resolvers.add(resolver);
    }

    public void addProcessor(AopCacheProcessor aopCacheProcessor) {
        processorMap.put(aopCacheProcessor.getClass(), aopCacheProcessor);
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object target = invocation.getThis();
        if (AopUtils.isToStringMethod(method)) {
            // Allow for differentiating between the proxy and the raw Connection.
            StringBuffer buf = new StringBuffer("proxy for cache");
            if (target != null) {
                buf.append("[").append(target.toString()).append("]");
            }
            return buf.toString();
        }
        List<CacheAction> actions = resolveMetadata(method);
        if (actions == null || actions.isEmpty()) {
            return invocation.proceed();
        }
        TemplateUtil.createTemplateRender(parameterNameDiscoverer);
        // 获取处理器类与处理器类需要的配置信息
        AopCacheExecutionChain chain = createChain(actions);
        AopCacheHolder[] cacheHolders = chain.getAopCacheProcessors();
        Object result = null;
        if (cacheHolders != null) {// 前置处理
            for (int processorIndex = 0; processorIndex < cacheHolders.length; processorIndex++) {
                AopCacheHolder cacheHolder = cacheHolders[processorIndex];
                AopCacheProcessor processor = cacheHolder.getProcessor();
                CacheMetadata metadata = cacheHolder.getMetadata();
                if (!processor.preProcess(metadata, invocation)) {
                    result = endProcessor(processorIndex, cacheHolders,
                            invocation);
                    return result;
                }
            }
        }
        result = invocation.proceed();
        if (cacheHolders != null) {// 后置处理
            for (int i = cacheHolders.length - 1; i >= 0; i--) {
                AopCacheHolder cacheHolder = cacheHolders[i];
                cacheHolder.getProcessor().postProcess(
                        cacheHolder.getMetadata(), invocation, result);
            }
        }
        return result;
    }

    private Object endProcessor(int processorIndex,
                                AopCacheHolder[] cacheHolders, MethodInvocation invocation) {
        if (cacheHolders != null) {
            AopCacheHolder cacheHolder = cacheHolders[processorIndex];
            return cacheHolder.getProcessor().endProcessor(cacheHolder.getMetadata(),
                    invocation);
        }
        return null;
    }

    private AopCacheExecutionChain createChain(List<CacheAction> actions) {
        AopCacheExecutionChain chain = new AopCacheExecutionChain();
        for (CacheAction cacheAction : actions) {
            AopCacheProcessor processor = processorMap.get(cacheAction
                    .bindAopProcessType());
            if (processor == null) {
                throw new AopCacheException(String.format(
                        "未注册aop缓存操作类型：%s对应的aop缓存处理器", cacheAction
                                .bindAopProcessType().getName()));
            }
            CacheMetadata metadata = cacheAction.createMetadata();
            chain.addAopCacheProcessor(new AopCacheHolder(processor, metadata));
        }
        return chain;
    }

    private List<CacheAction> resolveMetadata(Method method) {
        for (CacheActionResolver resolver : resolvers) {
            List<CacheAction> actions = resolver.resolve(method);
            if (!CollectionUtil.isEmpty(actions)) {
                return actions;
            }
        }
        return null;
    }

}
