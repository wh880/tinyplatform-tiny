package org.tinygroup.aopcache;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 缓存处理解析器
 * @author renhui
 *
 */
public interface CacheProcessResolver {

    public List<AopCacheProcessor> resolve(Method method);
	
}
