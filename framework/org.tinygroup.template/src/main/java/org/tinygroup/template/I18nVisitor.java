package org.tinygroup.template;

import java.util.Locale;

/**
 * Created by luoguo on 2014/6/9.
 */
public interface I18nVisitor {
    /**
     * 获取当前位置
     * @param context
     * @return
     */
    Locale getLocale(TemplateContext context);

    /**
     * 返回国际化资源
     * @param context
     * @param key
     * @return
     */
    String getI18nMessage(TemplateContext context,String key);
}
