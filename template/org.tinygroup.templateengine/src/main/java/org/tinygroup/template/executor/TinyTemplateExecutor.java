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
package org.tinygroup.template.executor;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.FileObjectResourceLoader;

/**
 * 用于直接对当前环境下的VM进行执行，并输出结果到控制台
 * Created by luoguo on 2014/6/7.
 */
public class TinyTemplateExecutor {
	private static String[] classPaths = {"src/main/resources","src/test/resources"};
	
    public static void main(String[] args) throws TemplateException {
        if (args.length == 0) {
            System.out.println("Usage:\n\tTinyTemplateExecutor templateFile [tempalteExtFileName] [layoutExtFileName] [componentExtFileName]");
            return;
        }
        //注意传入的参数，只能是“/”开头的相对路径
        String templateFile = null;
        if (args.length >= 1) {
            templateFile = args[0];
        }
        //模板文件扩展名不能写死，需要根据模板文件动态获取
        String templateExtFileName = StringUtil.defaultIfEmpty(getExtFileName(templateFile), "vm");
        if (args.length >= 2) {
            templateExtFileName = args[1];
        }
        String layoutExtFileName = "layout";
        if (args.length >= 3) {
            layoutExtFileName = args[2];
        }
        String componentExtFileName = "component";
        if (args.length >= 4) {
            componentExtFileName = args[3];
        }
        final TemplateEngine engine = new TemplateEngineDefault();
        //TODO 添加处理，把所有的ClassPath都加入到
        //for
        for(String classpath:classPaths){
        	FileObjectResourceLoader resourceLoader = new FileObjectResourceLoader(templateExtFileName, layoutExtFileName, componentExtFileName, classpath);
            engine.addResourceLoader(resourceLoader);
        }
        
        //end
        if (templateFile != null) {
            //如果只有一个，则只执行一个
            engine.renderTemplate(templateFile);
        }
    }
    
    protected static String getExtFileName(String path){
    	if(path==null){
    	   return null;
    	}
    	int lastIndexOfDot = path.lastIndexOf(".");
    	if(lastIndexOfDot == -1){
    	   return null;
    	}
    	return path.substring(lastIndexOfDot + 1);
    }
}
