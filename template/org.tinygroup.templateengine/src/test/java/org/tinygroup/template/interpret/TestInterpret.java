/**
 * Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 * <p/>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.StringResourceLoader;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by luoguo on 15/7/22.
 */
public class TestInterpret {
    public static void main(String[] args) throws TemplateException {
        TemplateContext context = new TemplateContextDefault();
        context.put("i", 1);
        StringResourceLoader loader = new StringResourceLoader();
        TemplateEngineDefault engine = new TemplateEngineDefault();
        engine.addResourceLoader(loader);
        Template template = loader.createTemplate("#set(abc=1) ${\"abc\"}HelloWorld#for(i:[1..20])${i}#end HelloWorld");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            template.render(context, new EmptyOutputStream());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.flush();
    }

}

class EmptyOutputStream extends OutputStream {

    @Override
    public void write(int b) throws IOException {
    }

    @Override
    public void write(byte[] b) throws IOException {
    }
}