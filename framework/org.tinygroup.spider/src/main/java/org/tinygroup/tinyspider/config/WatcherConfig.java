package org.tinygroup.tinyspider.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * 监视器配置
 * Created by luoguo on 2014/5/29.
 */
@XStreamAlias("watcher")
public class WatcherConfig {
    /**
     * 过滤器
     */
    FilterConfig filter;
    /**
     * 处理器
     */
    List<ProcessorConfig> processors;

    public FilterConfig getFilter() {
        return filter;
    }

    public void setFilter(FilterConfig filter) {
        this.filter = filter;
    }

    public List<ProcessorConfig> getProcessors() {
        return processors;
    }

    public void setProcessors(List<ProcessorConfig> processors) {
        this.processors = processors;
    }
}
