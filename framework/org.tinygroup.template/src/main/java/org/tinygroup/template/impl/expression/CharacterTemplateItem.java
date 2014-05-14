package org.tinygroup.template.impl.expression;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateItem;

/**
 * Created by luoguo on 2014/5/12.
 */
public class CharacterTemplateItem implements TemplateItem<Character> {
    Character value;

    public CharacterTemplateItem(Character v) {
        value = v;
    }

    public CharacterTemplateItem(String v) {
        if (v.length() == 3) {
            value = v.charAt(1);
        }
    }


    public Character execute(TemplateContext templateContext) {
        return value;
    }
}