package org.tinygroup.template.impl.expression;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateItem;

/**
 * Created by luoguo on 2014/5/12.
 */
public class LongTemplateItem implements TemplateItem<Long> {
    Long value;

    public LongTemplateItem(Long v) {
        value = v;
    }


    public Long execute(TemplateContext templateContext) {
        return value;
    }
}