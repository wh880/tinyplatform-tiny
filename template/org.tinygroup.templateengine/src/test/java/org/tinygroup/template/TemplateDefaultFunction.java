package org.tinygroup.template;

import java.io.StringWriter;

import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.StringResourceLoader;

/**
 * 模板引擎内置函数测试用例
 * @author yancheng11334
 *
 */
public class TemplateDefaultFunction {

	public static void main(String[] args) throws TemplateException {
		final TemplateEngine engine = new TemplateEngineDefault();
		TemplateContext context = new TemplateContextDefault();
		StringResourceLoader resourceLoader = new StringResourceLoader();
        engine.addResourceLoader(resourceLoader);
        
        StringWriter writer = null;
        Template template = null;
        
        writer = new StringWriter();
        template =resourceLoader.createTemplate("${rand()}");
        engine.renderTemplate(template, context, writer);
        System.out.println(writer.toString());
        
        writer = new StringWriter();
        template =resourceLoader.createTemplate("${random('int')}");
        engine.renderTemplate(template, context, writer);
        System.out.println(writer.toString());
        
        writer = new StringWriter();
        template =resourceLoader.createTemplate("${rand('long')}");
        engine.renderTemplate(template, context, writer);
        System.out.println(writer.toString());
        
        writer = new StringWriter();
        template =resourceLoader.createTemplate("${rand('uuid')}");
        engine.renderTemplate(template, context, writer);
        System.out.println(writer.toString());
        
        writer = new StringWriter();
        template =resourceLoader.createTemplate("${random('float')}");
        engine.renderTemplate(template, context, writer);
        System.out.println(writer.toString());
        
        writer = new StringWriter();
        template =resourceLoader.createTemplate("${rand('double')}");
        engine.renderTemplate(template, context, writer);
        System.out.println(writer.toString());
	}
}