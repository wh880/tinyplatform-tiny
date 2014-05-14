package org.tinygroup.template.impl.expression;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateItem;

/**
 * Created by luoguo on 2014/5/12.
 */
public class DoubleTemplateItem implements TemplateItem<Double> {
    Double value;

    public DoubleTemplateItem(Double v) {
        value = v;
    }


    public Double execute(TemplateContext templateContext) {
        return value;
    }
}