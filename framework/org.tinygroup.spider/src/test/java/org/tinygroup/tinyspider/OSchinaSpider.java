/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.tinyspider;

import org.tinygroup.htmlparser.node.HtmlNode;
import org.tinygroup.parser.filter.QuickNameFilter;
import org.tinygroup.tinyspider.impl.SpiderImpl;
import org.tinygroup.tinyspider.impl.WatcherImpl;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class OSchinaSpider {
    public static String outoutPath = "E:\\oschina\\";
    public static OutputStream outputStream;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        processCategory("377413","Tiny框架");
        processCategory("377414","算法感想");
        processCategory("451101","Tiny乱弹");
        processCategory("451799","Tiny示例");
//        processTopic("214018");
    }

    public static void processCategory(String categoryId,String categoryTitle) throws Exception {
            outputStream = new FileOutputStream(outoutPath+"cat_" + categoryId + ".page");
            outputStream.write(("#pageTitle(\"topic\" \"topic\")\n" +
                    "#title(\"Topics\" \""+categoryTitle+"\")\n").getBytes());
            Watcher watcher = new WatcherImpl();
            Spider spider = new SpiderImpl();
            watcher.addProcessor(new OsChinaCategoryProcessor(categoryTitle));
            QuickNameFilter<HtmlNode> nodeFilter = new QuickNameFilter<HtmlNode>();
            nodeFilter.setNodeName("li");
            nodeFilter.setIncludeAttribute("class", "Blog");
            watcher.setNodeFilter(nodeFilter);
            spider.addWatcher(watcher);
            spider.processUrl("http://my.oschina.net/tinyframework/blog?catalog=" + categoryId);
            outputStream.close();
    }

    public static void processTopic(String categoryTitle,String pageId,String title) throws Exception {
        Watcher watcher = new WatcherImpl();
        Spider spider = new SpiderImpl();
        watcher.addProcessor(new OsChinaTopicProcessor(categoryTitle,title));
        QuickNameFilter<HtmlNode> nodeFilter = new QuickNameFilter<HtmlNode>();
        nodeFilter.setNodeName("div");
        nodeFilter.setIncludeAttribute("class", "BlogContent");
        watcher.setNodeFilter(nodeFilter);
        spider.addWatcher(watcher);
        spider.processUrl("http://my.oschina.net/tinyframework/blog/" + pageId);
    }

}
