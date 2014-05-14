package org.tinygroup.application.impl;

import org.tinygroup.application.Application;
import org.tinygroup.application.ApplicationContext;
import org.tinygroup.context.impl.ContextImpl;

/**
 * 默认的应用环境实现类
 * Created by luoguo on 2014/5/5.
 */
public class ApplicationContextDefault extends ContextImpl implements ApplicationContext {
    public ApplicationContextDefault() {

    }

    public ApplicationContextDefault(Application application) {
        this.application = application;
    }

    private Application application;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
