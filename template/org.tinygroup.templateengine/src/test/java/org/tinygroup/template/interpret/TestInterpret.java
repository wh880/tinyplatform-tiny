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
package org.tinygroup.template.interpret;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.StringResourceLoader;

import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;

/**
 * Created by luoguo on 15/7/22.
 */
public class TestInterpret {
    public static void main(String[] args) throws TemplateException {
        TemplateContext context=new TemplateContextDefault();
        context.put("i",1);
        StringResourceLoader loader = new StringResourceLoader();
        TemplateEngineDefault engine =new TemplateEngineDefault();
        engine.addResourceLoader(loader);
        Template template = loader.createTemplate("#macro a()aaa${i}#end ${call('a')}");
        template.render(context, new OutputStreamWriter(System.out));
        System.out.flush();
    }
}
