package org.tinygroup.template;

import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.FileObjectTemplateLoader;

import java.io.OutputStreamWriter;

/**
 * @author Boilit
 * @see
 */
public final class TinyTemplate {
    public static void main(String[] args) throws TemplateException {
        TemplateEngine engine = new TemplateEngineDefault();
        TemplateContext context=new TemplateContextDefault();
        context.put("outputEncoding","GBK");
        context.put("items", StockModel.dummyItems());
        engine.addTemplateLoader(new FileObjectTemplateLoader("jetSample", "D:\\git\\ebm\\src\\main\\resources\\templates"));
        engine.renderTemplate("/tiny.html",context,new OutputStreamWriter(System.out));
    }

}
