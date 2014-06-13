package org.tinygroup.template;

import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.FileObjectResourceLoader;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Boilit
 * @see
 */
public final class TinyTemplate {
    public static void main(String[] args) throws TemplateException {
        TemplateEngine engine = new TemplateEngineDefault();
        TemplateContext context = new TemplateContextDefault();
        context.put("outputEncoding", "GBK");
        context.put("items", StockModel.dummyItems());
        engine.putTemplateLoader(TemplateEngine.DEFAULT, new FileObjectResourceLoader("html", null, "D:\\git\\ebm\\src\\main\\resources\\templates"));
        long start = System.currentTimeMillis();
        OutputStream outputStream=new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        };
        for (int i = 0; i < 100000; i++) {
            engine.renderTemplate("/tiny.html", context, outputStream);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
