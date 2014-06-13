package org.tinygroup.template;

import org.tinygroup.template.impl.AbstractLayout;
import org.tinygroup.template.impl.AbstractTemplate;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.ClassLoaderResourceLoader;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by luoguo on 2014/6/13.
 */
public class LayoutTest {
    public static void main(String[] args) throws TemplateException {
        TemplateEngine engine=new TemplateEngineDefault();
        engine.putTemplateLoader(TemplateEngine.DEFAULT,new ClassLoaderResourceLoader("page","layout"));
        engine.getDefaultTemplateLoader().addLayout(new Layout1());
        engine.getDefaultTemplateLoader().addLayout(new Layout2());
        engine.getDefaultTemplateLoader().addTemplate(new Template1());
        engine.renderTemplate("/aaa/a.page");
    }
}

class Template1 extends AbstractTemplate {

    protected void renderTemplate(TemplateContext $context, OutputStream outputStream) throws IOException, TemplateException {
        getTemplateEngine().write(outputStream,"Hello");
    }


    public String getPath() {
        return "/aaa/a.page";
    }
}

class Layout1 extends AbstractLayout {

    protected void renderLayout(TemplateContext $context, OutputStream outputStream) throws IOException, TemplateException {
        getTemplateEngine().write(outputStream,"<b>");
        getTemplateEngine().write(outputStream,$context.get("pageContent").toString());
        getTemplateEngine().write(outputStream,"</b>");
    }


    public String getPath() {
        return "/aaa/a.layout";
    }
}

class Layout2 extends AbstractLayout {

    protected void renderLayout(TemplateContext $context, OutputStream outputStream) throws IOException, TemplateException {
        getTemplateEngine().write(outputStream,"<div>");
        getTemplateEngine().write(outputStream,$context.get("pageContent").toString());
        getTemplateEngine().write(outputStream,"</div>");
    }


    public String getPath() {
        return "/a.layout";
    }
}
