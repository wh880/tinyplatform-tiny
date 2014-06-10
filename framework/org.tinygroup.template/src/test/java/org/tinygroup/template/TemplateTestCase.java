package org.tinygroup.template;

import org.tinygroup.template.impl.TemplateEngineDefault;

/**
 * Created by luoguo on 2014/6/7.
 */
public class TemplateTestCase {
    static class I18nvi implements I18nVistor{

        @Override
        public String getI18nMessage(String key) {
        return key.toUpperCase();
    }
}
    public static void main(String[] args) throws TemplateException {
        final TemplateEngine engine = new TemplateEngineDefault();
        engine.setI18nVistor(new I18nvi());
        System.out.println(engine.executeFunction("format", null, "this is %s %s", 1, 2));
        Template template = engine.getDefaultTemplateLoader().createTemplate("$${abc}$${abc.def.gh}");
        template.render();
    }
}
