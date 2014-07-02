package org.tinygroup.application;

import org.tinygroup.context.Context;

/**
 * 应用上下文，用于在应用上下文之间传输数据
 * Created by luoguo on 2014/5/5.
 */
public interface ApplicationContext extends Context {
    Application getApplication();

    void setApplication(Application application);
}
