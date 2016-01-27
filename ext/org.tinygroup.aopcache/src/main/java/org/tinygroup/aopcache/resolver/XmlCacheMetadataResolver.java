package org.tinygroup.aopcache.resolver;

import org.tinygroup.aopcache.AopCacheConfigManager;
import org.tinygroup.aopcache.config.CacheAction;

import java.lang.reflect.Method;
import java.util.List;

/**
 * xml方式缓存配置解析器
 * Created by renhui on 2015/9/24.
 */
public class XmlCacheMetadataResolver extends AbstractCacheActionResolver {

    private AopCacheConfigManager manager;

    public void setManager(AopCacheConfigManager manager) {
        this.manager = manager;
    }

    public List<CacheAction> resolve(Method method) {
        return manager.getActionsWithMethod(method);
    }
}
