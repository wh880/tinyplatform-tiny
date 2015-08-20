package org.tinygroup.sessionstore;

import org.tinygroup.cache.Cache;
import org.tinygroup.weblayer.webcontext.session.SessionStore;

/**
 * 把session内容存放在缓存中
 * @author renhui
 *
 */
public interface CacheStore extends SessionStore {
       public void setCache(Cache cache);	
}
