package org.tinygroup.template;

import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.ClassLoaderResourceLoader;

import java.io.File;
import java.net.URL;

/**
 * Created by luoguo on 2014/6/7.
 */
public class TemplateTestCase1 {

    public static void main(String[] args) throws Exception {
        URL[] urls = {new File("C:\\Users\\luoguo\\AppData\\Local\\Temp\\ttl").toURI().toURL()};
        final TemplateEngine engine = new TemplateEngineDefault();
        engine.addTemplateLoader(new ClassLoaderResourceLoader("jetx", null, null, urls));
        engine.renderTemplate("template/jet/constant-number.jetx");
    }
}
