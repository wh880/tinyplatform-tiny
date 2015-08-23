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
package org.tinygroup.template.function;

import org.tinygroup.template.Macro;
import org.tinygroup.template.Template;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.impl.TemplateContextDefault;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoguo on 2014/6/9.
 */
public class CallMacroFunction extends AbstractTemplateFunction {

    public CallMacroFunction() {
        super("call,callMacro");
    }

    public Object execute(Template template, TemplateContext context, Object... parameters) throws TemplateException {
        String macroName = parameters[0].toString();
        Macro macro = getTemplateEngine().findMacro(macroName, template, context);
        TemplateContext newTemplateContext = null;
        newTemplateContext = new TemplateContextDefault();
        newTemplateContext.setParent(context);
        List paraList = new ArrayList();
        newTemplateContext.put(parameters[0] + "ParameterList", paraList);
        for (int i = 1; i < parameters.length; i++) {
            paraList.add(parameters[i]);
        }
        for (int i = 0; i < macro.getParameterNames().size(); i++) {
            if (i + 1 >= parameters.length) {
                continue;
            }
            newTemplateContext.put(macro.getParameterNames().get(i), parameters[i + 1]);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        macro.render(template, context, newTemplateContext, byteArrayOutputStream);
        try {
            return new String(byteArrayOutputStream.toByteArray(), template.getTemplateEngine().getEncode());
        } catch (UnsupportedEncodingException e) {
            throw new TemplateException(e);
        }
    }
}

