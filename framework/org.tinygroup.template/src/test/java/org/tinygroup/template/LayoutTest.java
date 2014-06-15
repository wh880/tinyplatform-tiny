package org.tinygroup.template;

import org.tinygroup.template.impl.AbstractTemplate;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.ClassLoaderResourceLoader;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by luoguo on 2014/6/13.
 */
public class LayoutTest {
    public static void main(String[] args) throws TemplateException {
        TemplateEngine engine=new TemplateEngineDefault();
        ClassLoaderResourceLoader templateLoader = new ClassLoaderResourceLoader("page", "layout", "macro");
        engine.addTemplateLoader(templateLoader);
        templateLoader.addTemplate(new Layout1());
        templateLoader.addTemplate(new Layout2());
        templateLoader.addTemplate(new Template1());
        engine.renderTemplate("/aaa/a.page");
    }
}

class Template1 extends AbstractTemplate {
    @Override
    protected void renderContent(TemplateContext $context, Writer $writer) throws IOException, TemplateException {
        $writer.write("Hello");
    }

    @Override
    public String getPath() {
        return "/aaa/a.page";
    }
}

class Layout1 extends AbstractTemplate {
    @Override
    protected void renderContent(TemplateContext $context, Writer $writer) throws IOException, TemplateException {
        $writer.write("<b>");
        $writer.write($context.get("pageContent").toString());
        $writer.write("</b>");
    }

    @Override
    public String getPath() {
        return "/aaa/a.layout";
    }
}

class Layout2 extends AbstractTemplate {
    @Override
    protected void renderContent(TemplateContext $context, Writer $writer) throws IOException, TemplateException {
        $writer.write("<div>");
        $writer.write($context.get("pageContent").toString());
        $writer.write("</div>");
    }

    @Override
    public String getPath() {
        return "/a.layout";
    }
}
