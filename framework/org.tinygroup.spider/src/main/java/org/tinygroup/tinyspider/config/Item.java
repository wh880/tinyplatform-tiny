package org.tinygroup.tinyspider.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * KeyValue配置
 * Created by luoguo on 2014/5/29.
 */

@XStreamAlias("item")
public class Item {
    @XStreamAsAttribute
    String name;
    @XStreamAsAttribute
    String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
