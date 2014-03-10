package org.tinygroup.tinyspider.jokeji.sample3;

import org.tinygroup.htmlparser.node.HtmlNode;
import org.tinygroup.parser.filter.FastNameFilter;
import org.tinygroup.tinyspider.Processor;

import java.util.List;

/**
 * Created by luoguo on 14-3-1.
 */
public class NextPageProcessor implements Processor {
    public void process(String url, HtmlNode node) {
        FastNameFilter<HtmlNode> filter = new FastNameFilter<HtmlNode>(node);
        filter.setNodeName("a");
        List<HtmlNode> aList = filter.findNodeList();
        for (HtmlNode a : aList) {
            if(a.getAttribute("href").startsWith("list")){
                JokejiTest.processUrl("http://www.jokeji.cn/"+a.getAttribute("href"));
            }
        }
    }
}
