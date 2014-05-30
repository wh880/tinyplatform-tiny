package org.tinygroup.tinyspider.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.util.List;

/**
 * 处理配置
 * Created by luoguo on 2014/5/29.
 */
@XStreamAlias("processor")
public class ProcessorConfig {
    @XStreamAlias("init-parameters")
    List<Item> initParameters;
    /**
     * 处理类类型
     */
    @XStreamAsAttribute
    @XStreamAlias("beanName")
    String beanName;
    @XStreamAsAttribute
    String type;
    /**
     * 继续进行下一级查找
     */
    @XStreamAlias("spider")
    SpiderConfig spider;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public SpiderConfig getSpider() {
        return spider;
    }

    public void setSpider(SpiderConfig spider) {
        this.spider = spider;
    }

    public List<Item> getInitParameters() {
        return initParameters;
    }

    public void setInitParameters(List<Item> initParameters) {
        this.initParameters = initParameters;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
