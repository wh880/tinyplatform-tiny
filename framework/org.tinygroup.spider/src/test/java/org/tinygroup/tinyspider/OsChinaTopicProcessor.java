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

import org.tinygroup.commons.file.IOUtils;
import org.tinygroup.context.Context;
import org.tinygroup.htmlparser.node.HtmlNode;

import java.io.FileOutputStream;

public class OsChinaTopicProcessor implements Processor {
    String title;
    String categoryTitle;
    public OsChinaTopicProcessor(String categoryTitle,String title){
        this.title=title;
        this.categoryTitle=categoryTitle;
    }
    public void process(String url, HtmlNode node, Context context) throws Exception{
        String fileName=OSchinaSpider.outoutPath+url.substring(url.lastIndexOf('/')+1)+".page";
            IOUtils.writeToOutputStream(new FileOutputStream(fileName), "#pageTitle(\"topic\" \"topic\")\n#title(\""+categoryTitle+"\" \""+title+"\")\n#[["+node.toString()+"]]#","UTF-8");
    }

}
