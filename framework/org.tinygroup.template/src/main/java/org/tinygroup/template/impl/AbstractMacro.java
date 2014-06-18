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
package org.tinygroup.template.impl;

import org.tinygroup.template.*;

import java.io.IOException;
import java.io.Writer;

/**
 * 抽象宏
 * Created by luoguo on 2014/6/6.
 */
public abstract class AbstractMacro implements Macro {
    public AbstractMacro(String name) {
        this.name = name;
    }

    public AbstractMacro(String name, String[] parameterNames) {
        this(name);
        this.parameterNames = parameterNames.clone();
    }

    private String name;
    private String[] parameterNames;
    private TemplateEngine templateEngine;

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void setParameterNames(String[] parameterNames) {
        this.parameterNames = parameterNames.clone();
    }

    protected void init(String name, String[] parameterNames) {
        this.name = name;
        this.parameterNames = parameterNames.clone();
    }

    protected void write(Writer writer, Object object) throws IOException {
        writer.write(object.toString());
    }

    public void render(Template template, TemplateContext context, Writer writer) throws TemplateException {
        try {
            renderMacro(template, context, writer);
        } catch (IOException e) {
            throw new TemplateException(e);
        }
    }

    protected abstract void renderMacro(Template template, TemplateContext context, Writer writer) throws IOException, TemplateException;


    public String getName() {
        return name;
    }

    public String[] getParameterNames() {
        return parameterNames.clone();
    }


}
