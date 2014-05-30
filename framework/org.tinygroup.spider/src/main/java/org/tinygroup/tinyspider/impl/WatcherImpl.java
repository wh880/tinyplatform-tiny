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
package org.tinygroup.tinyspider.impl;

import org.tinygroup.htmlparser.node.HtmlNode;
import org.tinygroup.parser.NodeFilter;
import org.tinygroup.tinyspider.Processor;
import org.tinygroup.tinyspider.Watcher;

import java.util.ArrayList;
import java.util.List;

public class WatcherImpl implements Watcher {

    private NodeFilter<HtmlNode> nodeFilter;
    private List<Processor> processorList = new ArrayList<Processor>();
    private List<Watcher> subWatchers;

    public void setNodeFilter(NodeFilter<HtmlNode> nodeFilter) {
        this.nodeFilter = nodeFilter;
    }

    public Watcher nodeFilter(NodeFilter<HtmlNode> filter) {
        setNodeFilter(filter);
        return this;
    }

    public NodeFilter<HtmlNode> getNodeFilter() {
        return this.nodeFilter;
    }

    public void addProcessor(Processor processor) {
        processorList.add(processor);
    }

    public Watcher processor(Processor processor) {
        addProcessor(processor);
        return this;
    }

    public List<Processor> getProcessorList() {
        return this.processorList;
    }

    public Watcher setSubWatchers(List<Watcher> watchers) {
        this.subWatchers = watchers;
        return this;
    }

    public List<Watcher> getSubWatchers() {
        return subWatchers;
    }

    public Watcher addSubWatcher(Watcher watcher) {
        if (subWatchers == null) {
            subWatchers = new ArrayList<Watcher>();
        }
        subWatchers.add(watcher);
        return this;
    }

}
