package org.tinygroup.application;

/**
 * Created by luoguo on 2014/5/14.
 */
public abstract class AbstractApplicationProcessor implements ApplicationProcessor {
    private Application application;
    public void init(){

    }
    public void setApplication(Application application) {
        this.application = application;
    }
}
