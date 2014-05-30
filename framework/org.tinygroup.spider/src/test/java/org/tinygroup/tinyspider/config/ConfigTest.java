package org.tinygroup.tinyspider.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoguo on 2014/5/30.
 */
public class ConfigTest {
    private static SpiderConfig topicSpider;

    public static void main(String[] args) {
        SpiderConfig spiderConfig = new SpiderConfig();
        spiderConfig.setUrl("http://my.oschina.net/tinyframework/blog?catalog=451799");
        List<WatcherConfig> watchers = new ArrayList<WatcherConfig>();
        spiderConfig.setWatchers(watchers);

        WatcherConfig watcher = new WatcherConfig();
        watchers.add(watcher);

        FilterConfig filter = new FilterConfig();
        watcher.setFilter(filter);
        filter.setNodeName("li");
        List<Item> attributeWithValue = new ArrayList<Item>();
        Item item = new Item();
        item.setName("classs");
        item.setValue("Blog");
        attributeWithValue.add(item);
        filter.setExcludeAttributeWithValues(attributeWithValue);

        ProcessorConfig processor = new ProcessorConfig();
        List<ProcessorConfig> processors=new ArrayList<ProcessorConfig>();
        processors.add(processor);
        watcher.setProcessors(processors);
        processor.setSpider(getTopicSpider());
    }

    public static SpiderConfig getTopicSpider() {
        SpiderConfig spiderConfig=new SpiderConfig();
        spiderConfig.setUrl("http://my.oschina.net/tinyframework/blog?catalog=451799");
        List<WatcherConfig> watchers=new ArrayList<WatcherConfig>();
        spiderConfig.setWatchers(watchers);

        WatcherConfig watcher=new WatcherConfig();
        watchers.add(watcher);

        FilterConfig filter=new FilterConfig();
        watcher.setFilter(filter);
        filter.setNodeName("li");
        List<Item> attributeWithValue=new ArrayList<Item>();
        Item item=new Item();
        item.setName("classs");
        item.setValue("Blog");
        attributeWithValue.add(item);
        filter.setExcludeAttributeWithValues(attributeWithValue);

        ProcessorConfig processor=new ProcessorConfig();
        List<ProcessorConfig> processors=new ArrayList<ProcessorConfig>();
        processors.add(processor);
        watcher.setProcessors(processors);
        //TODO 添加处理
        return spiderConfig;
    }
}
