package org.tinygroup.aopcache;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.core.Ordered;
import org.tinygroup.aopcache.config.CacheAction;
/**
 * 缓存配置解析器
 * @author renhui
 *
 */
public interface CacheActionResolver extends Ordered {
    /**
     * 返回此方法上匹配的所有CacheAction
     * @param method
     * @return
     */
	public List<CacheAction> resolve(Method method);
	
}
