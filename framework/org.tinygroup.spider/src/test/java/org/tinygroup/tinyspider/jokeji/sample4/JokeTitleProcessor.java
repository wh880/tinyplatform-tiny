package org.tinygroup.tinyspider.jokeji.sample4;

import org.tinygroup.htmlparser.node.HtmlNode;
import org.tinygroup.tinyspider.Processor;

/**
 * Created by luoguo on 14-3-3.
 */
public class JokeTitleProcessor implements Processor {

    public void process(String url, HtmlNode node) {
        System.out.println("=========================================");
        System.out.println(node.getParent().getContent());
    }
}
