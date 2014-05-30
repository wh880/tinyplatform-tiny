package org.tinygroup.tinyspider.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.util.List;

/**
 * 爬虫配置
 * Created by luoguo on 2014/5/29.
 */
@XStreamAlias("spider")
public class SpiderConfig {
    @XStreamAlias("bean-name")
    @XStreamAsAttribute
    String beanName;
    @XStreamAsAttribute
    String url;
    @XStreamAlias("init-parameters")
    List<Item> initParameters;
    List<WatcherConfig> watchers;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Item> getInitParameters() {
        return initParameters;
    }

    public void setInitParameters(List<Item> initParameters) {
        this.initParameters = initParameters;
    }

    public List<WatcherConfig> getWatchers() {
        return watchers;
    }

    public void setWatchers(List<WatcherConfig> watchers) {
        this.watchers = watchers;
    }
}
