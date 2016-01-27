package org.tinygroup.aopcache.base;

import org.tinygroup.aopcache.AopCacheProcessor;

/**
 * @author renhui
 */
public class AopCacheHolder {

    private AopCacheProcessor processor;

    private CacheMetadata metadata;


    public AopCacheHolder() {
        super();
    }

    public AopCacheHolder(AopCacheProcessor processor, CacheMetadata metadata) {
        super();
        this.processor = processor;
        this.metadata = metadata;
    }

    public CacheMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(CacheMetadata metadata) {
        this.metadata = metadata;
    }

    public AopCacheProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(AopCacheProcessor processor) {
        this.processor = processor;
    }


}
