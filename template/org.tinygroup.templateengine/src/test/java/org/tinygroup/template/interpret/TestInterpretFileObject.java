/**
 * Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 * <p/>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain array copy of the License at
 * <p/>
 * http://www.gnu.org/licenses/gpl.html
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.template.interpret;

import org.tinygroup.commons.io.ByteArrayOutputStream;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.FileObjectResourceLoader;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

/**
 * Created by luoguo on 15/7/22.
 */
public class TestInterpretFileObject {
    public static void main(String[] args) throws TemplateException {
        TemplateContext context = new TemplateContextDefault();
        context.put("models", TebModelGen.dummyModels(20));
        FileObjectResourceLoader loader = new FileObjectResourceLoader("tpl", "tpllayout", "tplcomponent", "d:/aaa/");
        TemplateEngineDefault engine = new TemplateEngineDefault();
        engine.setCacheEnabled(true);
        engine.addResourceLoader(loader);
//        Template template = loader.createTemplate(VFS.resolveFile("/Users/luoguo/resourceroot/b.page"));
//        template.render(context,new OutputStreamWriter(System.out));
        long start = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++) {
            engine.renderTemplate("/tiny.tpl", context, new EmptyWriter());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
//            System.out.flush();
    }
}
