package org.tinygroup.aopcache.impl;

import org.tinygroup.aopcache.AopCacheConfigManager;
import org.tinygroup.aopcache.base.MethodDescription;
import org.tinygroup.aopcache.config.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * aop缓存配置管理器
 *
 * @author renhui
 */
public class AopCacheConfigManagerImpl implements AopCacheConfigManager {

    private Map<MethodDescription, CacheActions> methodActionMap = new HashMap<MethodDescription, CacheActions>();

    public void addAopCaches(AopCaches aopCaches) {
        List<AopCache> cacheConfigs = aopCaches.getCacheConfigs();
        for (AopCache aopCache : cacheConfigs) {
            List<MethodConfig> methodConfigs = aopCache.getMethodConfigs();
            for (MethodConfig methodConfig : methodConfigs) {
                MethodDescription description = new MethodDescription();
                description.setClassName(aopCache.getClassName());
                description.setMethodName(methodConfig.getMethodName());
                List<ParameterType> paramTypes = methodConfig.getParamTypes();
                StringBuilder typeBuilder = new StringBuilder();
                for (int i = 0; i < paramTypes.size(); i++) {
                    typeBuilder.append(paramTypes.get(i).getType());
                    if (i < paramTypes.size() - 1) {
                        typeBuilder.append(";");
                    }
                }
                description.setParameterTypes(typeBuilder.toString());
                methodActionMap
                        .put(description, methodConfig.getCacheActions());
            }
        }
    }

    public void removeAopCaches(AopCaches aopCaches) {
        List<AopCache> cacheConfigs = aopCaches.getCacheConfigs();
        for (AopCache aopCache : cacheConfigs) {
            List<MethodConfig> methodConfigs = aopCache.getMethodConfigs();
            for (MethodConfig methodConfig : methodConfigs) {
                MethodDescription description = new MethodDescription();
                description.setClassName(aopCache.getClassName());
                description.setMethodName(methodConfig.getMethodName());
                List<ParameterType> paramTypes = methodConfig.getParamTypes();
                StringBuilder typeBuilder = new StringBuilder();
                for (int i = 0; i < paramTypes.size(); i++) {
                    typeBuilder.append(paramTypes.get(i).getType());
                    if (i < paramTypes.size() - 1) {
                        typeBuilder.append(";");
                    }
                }
                description.setParameterTypes(typeBuilder.toString());
                methodActionMap.remove(description);
            }
        }

    }

    public List<CacheAction> getActionsWithMethod(Method method) {
        MethodDescription description = MethodDescription
                .createMethodDescription(method);
        CacheActions cacheActions = methodActionMap.get(description);
        if (cacheActions != null) {
            return cacheActions.getActions();
        }
        return null;
    }

}
