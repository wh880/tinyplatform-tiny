package org.tinygroup.template.rumtime;

import java.util.HashMap;

/**
 * Created by luoguo on 2014/6/8.
 */
public class TemplateMap extends HashMap {
    public TemplateMap() {

    }

    public TemplateMap putItem(Object key, Object value) {
        put(key, value);
        return this;
    }
}
