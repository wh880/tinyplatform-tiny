package org.tinygroup.aopcache;

import java.lang.reflect.Method;
import java.util.List;

import org.tinygroup.aopcache.config.AopCaches;
import org.tinygroup.aopcache.config.CacheAction;

/**
 * aop缓存配置管理对象
 * @author renhui
 *
 */
public interface AopCacheConfigManager {

	String XSTEAM_PACKAGE_NAME = "aopcache";

	void addAopCaches(AopCaches aopCaches);
	
	void removeAopCaches(AopCaches aopCaches);
	
	/**
	 * 根据方法获取其关联的缓存aop操作配置
	 * @param method
	 * @return
	 */
	List<CacheAction> getActionsWithMethod(Method method);
	
	
}
