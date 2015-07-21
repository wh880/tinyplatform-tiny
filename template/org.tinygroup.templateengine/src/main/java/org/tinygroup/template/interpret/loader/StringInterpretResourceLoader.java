/**
 * Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.template.interpret.loader;

import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.interpret.TemplateInterpretEngine;
import org.tinygroup.template.loader.AbstractResourceLoader;

import java.io.OutputStreamWriter;

/**
 * Created by luoguo on 2014/6/9.
 */
public class StringInterpretResourceLoader extends AbstractResourceLoader<String> {
    public StringInterpretResourceLoader() {
        super(null, null, null);
    }

    /**
     * 字符串内存型的，不支持根据路径载入
     *
     * @param path
     * @return
     * @throws TemplateException
     */

    protected Template loadTemplateItem(String path) throws TemplateException {
        return null;
    }


    protected Template loadLayout(String path) throws TemplateException {
        return null;
    }

    protected Template loadMacroLibrary(String path) throws TemplateException {
        return null;
    }

    public boolean isModified(String path) {
        return false;
    }

    /**
     * 字符串内存型的，不支持根据路径载入
     *
     * @param path
     * @param encode
     * @return
     */
    public String getResourceContent(String path, String encode) {
        return null;
    }

    public Template createTemplate(String stringTemplateMaterial) throws TemplateException {
        try {
            Template template = TemplateInterpretLoadUtil.loadComponent((TemplateInterpretEngine) getTemplateEngine(), getRandomPath(), stringTemplateMaterial);
            template.setTemplateEngine(getTemplateEngine());
            return template;
        } catch (Exception e) {
            throw new TemplateException(e);
        }
        //这里没有调用putTemplate是避免内存泄露
    }

    public Template createLayout(String templateMaterial) throws TemplateException {
        throw new TemplateException("Not supported.");
    }

    public Template createMacroLibrary(String templateMaterial) throws TemplateException {
        throw new TemplateException("Not supported.");
    }

    private String getRandomPath() {
        return "C" + System.nanoTime();
    }

    public static void main(String[] args) throws TemplateException {
        TemplateContext context=new TemplateContextDefault();
        StringInterpretResourceLoader loader = new StringInterpretResourceLoader();
        TemplateInterpretEngine engine =new TemplateInterpretEngine();
        engine.addResourceLoader(loader);
        Template template = loader.createTemplate("#macro abc()abc111#end #abc()");
        template.render(context,new OutputStreamWriter(System.out));
        System.out.flush();
    }
}
