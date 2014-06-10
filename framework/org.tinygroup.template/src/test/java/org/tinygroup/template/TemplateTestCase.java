package org.tinygroup.template;

import org.tinygroup.template.function.AbstractBindTemplateFunction;
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
    static  class  StringBoldFunction extends AbstractBindTemplateFunction {

        public StringBoldFunction() {
            super("bold","java.lang.String");
        }

        @Override
        public Object execute(Object... parameters) throws TemplateException {
            String obj= (String) parameters[0];
            return "<b>"+obj+"</b>";
        }
    }
    public static void main(String[] args) throws TemplateException {
        final TemplateEngine engine = new TemplateEngineDefault();
        engine.addTemplateFunction(new StringBoldFunction());
        engine.setI18nVistor(new I18nvi());
        Template template = engine.getDefaultTemplateLoader().createTemplate("${'abc'.bold()}");
        template.render();
        template = engine.getDefaultTemplateLoader().createTemplate("${'abc'.equals('a')}");
        template.render();
        template = engine.getDefaultTemplateLoader().createTemplate("${fmt('add%sinfo',3)}");
        template.render();
    }
}
