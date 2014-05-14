package org.tinygroup.template;

/**
 * Created by luoguo on 2014/5/12.
 */
public class TemplateExpression implements TemplateItem<String> {
    String op;
    TemplateItem left, right;

    public TemplateExpression(String o, TemplateItem l, TemplateItem r) {
        op = o;
        left = l;
        right = r;
    }

    public String execute(TemplateContext templateContext) {
        return "(" + op + " " + left.execute(templateContext) + " " + right.execute(templateContext) + ")";
    }
}
