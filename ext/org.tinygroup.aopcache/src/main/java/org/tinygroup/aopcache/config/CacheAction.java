package org.tinygroup.aopcache.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.tinygroup.aopcache.AopCacheProcessor;
import org.tinygroup.aopcache.base.CacheMetadata;

@XStreamAlias("cache-action")
public abstract class CacheAction {

    @XStreamAsAttribute
    private String group;
    @XStreamAsAttribute
    private String keys;
    @XStreamAsAttribute
    @XStreamAlias("parameter-names")
    private String parameterNames;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * 配置关联的aop缓存处理器
     *
     * @return
     */
    public abstract Class<? extends AopCacheProcessor> bindAopProcessType();

    public CacheMetadata createMetadata() {
        CacheMetadata metadata = new CacheMetadata();
        metadata.setGroup(group);
        return metadata;
    }

}
