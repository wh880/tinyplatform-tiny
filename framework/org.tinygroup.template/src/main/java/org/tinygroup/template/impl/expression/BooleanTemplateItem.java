package org.tinygroup.template.impl.expression;

import org.tinygroup.template.TemplateItem;
import org.tinygroup.template.TemplateContext;

/**
 * Created by luoguo on 2014/5/12.
 */
public class BooleanTemplateItem implements TemplateItem<Boolean> {
    Boolean value;

    public BooleanTemplateItem(Boolean v) {
        value = v;
    }


    public Boolean execute(TemplateContext templateContext) {
        return value;
    }
}