package org.tinygroup.tinyspider.impl;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.tinygroup.htmlparser.node.HtmlNode;
import org.tinygroup.tinyspider.Processor;

import java.util.Map;

/**
 * Created by luoguo on 2014/5/30.
 */
@XStreamAlias("content-read-processor")

public class ContentReadProcessor implements Processor {
    @XStreamAsAttribute
    String name;

    public void process(String url, HtmlNode node, Map<String,Object> parameters) throws Exception {
        parameters.put(name, node.getPureText());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
