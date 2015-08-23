package org.tinygroup.template;

import java.io.OutputStream;
import java.io.StringWriter;

import org.tinygroup.commons.io.ByteArrayOutputStream;
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
        
        OutputStream outputStream = null;
        Template template = null;
        
        outputStream = new ByteArrayOutputStream();
        template =resourceLoader.createTemplate("${rand()}");
        engine.renderTemplate(template, context, outputStream);
        System.out.println(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        template =resourceLoader.createTemplate("${random('int')}");
        engine.renderTemplate(template, context, outputStream);
        System.out.println(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        template =resourceLoader.createTemplate("${rand('long')}");
        engine.renderTemplate(template, context, outputStream);
        System.out.println(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        template =resourceLoader.createTemplate("${rand('uuid')}");
        engine.renderTemplate(template, context, outputStream);
        System.out.println(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        template =resourceLoader.createTemplate("${random('float')}");
        engine.renderTemplate(template, context, outputStream);
        System.out.println(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        template =resourceLoader.createTemplate("${rand('double')}");
        engine.renderTemplate(template, context, outputStream);
        System.out.println(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        template =resourceLoader.createTemplate("${toInt('87')}");
        engine.renderTemplate(template, context, outputStream);
        System.out.println(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        template =resourceLoader.createTemplate("${toLong('1230000000')}");
        engine.renderTemplate(template, context, outputStream);
        System.out.println(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        template =resourceLoader.createTemplate("${toBool('true')}");
        engine.renderTemplate(template, context, outputStream);
        System.out.println(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        template =resourceLoader.createTemplate("${toFloat('-9.01')}");
        engine.renderTemplate(template, context, outputStream);
        System.out.println(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        template =resourceLoader.createTemplate("${toDouble('1.234567890123')}");
        engine.renderTemplate(template, context, outputStream);
        System.out.println(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        template =resourceLoader.createTemplate("${formatDate(now())}");
        engine.renderTemplate(template, context, outputStream);
        System.out.println(outputStream.toString());
        
        outputStream = new ByteArrayOutputStream();
        template =resourceLoader.createTemplate("${formatDate(now(),'yyyy年MM月dd日 HH:mm:ss')}");
        engine.renderTemplate(template, context, outputStream);
        System.out.println(outputStream.toString());
	}
	
}
