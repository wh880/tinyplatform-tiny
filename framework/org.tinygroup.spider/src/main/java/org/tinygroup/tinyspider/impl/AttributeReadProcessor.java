package org.tinygroup.tinyspider.impl;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.tinygroup.htmlparser.node.HtmlNode;
import org.tinygroup.tinyspider.Processor;

import java.util.Map;

/**
 * Created by luoguo on 2014/5/30.
 */
@XStreamAlias("attribute-read-processor")
public class AttributeReadProcessor implements Processor {
    @XStreamAsAttribute
    String name;
    @XStreamAsAttribute
    @XStreamAlias("attribute-name")
    String attributeName;

    public void process(String url, HtmlNode node, Map<String,Object> parameters) throws Exception {
        String attribute = node.getAttribute(attributeName);
        if (attribute != null) {
            parameters.put(name, attribute);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
