package org.tinygroup.template.loader;

import org.tinygroup.template.Template;
import org.tinygroup.template.interpret.TemplateFromContext;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.interpret.TemplateInterpreter;
import org.tinygroup.template.interpret.context.ImportProcessor;
import org.tinygroup.template.interpret.context.MacroDefineProcessor;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

/**
 * 载入组件文件,组件文件需要识别宏与import指令
 * Created by luog on 15/7/20.
 */
public final class TemplateLoadUtil {
    static TemplateInterpreter interpreter = new TemplateInterpreter();

    static {
        interpreter.addContextProcessor(new MacroDefineProcessor());
        interpreter.addContextProcessor(new ImportProcessor());
    }

    public static Template loadComponent(TemplateEngineDefault engine, String path, String content) throws Exception {
        TinyTemplateParser.TemplateContext tree = interpreter.parserTemplateTree(path, content);
        TemplateFromContext template = new TemplateFromContext(interpreter, path, tree);
        interpreter.interpretTree(engine, template, tree, null, null, null);
        return template;
    }
}
