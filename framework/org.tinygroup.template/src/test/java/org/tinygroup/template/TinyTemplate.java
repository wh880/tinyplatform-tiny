package org.tinygroup.template;

import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.FileObjectResourceLoader;

import java.io.IOException;
import java.io.Writer;

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
        FileObjectResourceLoader html = new FileObjectResourceLoader("html", null, "D:\\git\\ebm\\src\\main\\resources\\templates");
        html.setCheckModified(false);
        engine.putTemplateLoader(TemplateEngine.DEFAULT, html);
        engine.getTemplate("/tiny.html");
        long start = System.currentTimeMillis();
        Writer writer = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {

            }

            @Override
            public void flush() throws IOException {

            }

            @Override
            public void close() throws IOException {

            }
        };
        for (int i = 0; i < 200000; i++) {
            engine.renderTemplate("/tiny.html", context, writer);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
