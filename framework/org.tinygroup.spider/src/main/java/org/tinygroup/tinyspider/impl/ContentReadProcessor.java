package org.tinygroup.tinyspider.impl;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.tinygroup.context.Context;
import org.tinygroup.htmlparser.node.HtmlNode;
import org.tinygroup.tinyspider.Processor;

/**
 * Created by luoguo on 2014/5/30.
 */
@XStreamAlias("content-read-processor")

public class ContentReadProcessor implements Processor {
    @XStreamAsAttribute
    String name;

    public void process(String url, HtmlNode node, Context context) throws Exception {
        context.put(name, node.getPureText());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
