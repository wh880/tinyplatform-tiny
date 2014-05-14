package org.tinygroup.template.impl.expression;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateItem;

/**
 * Created by luoguo on 2014/5/12.
 */
public class StringTemplateItem implements TemplateItem<String> {
    String value;

    public StringTemplateItem(String v) {
        value = v;
    }

    public String execute(TemplateContext templateContext) {
        return value;
    }
}