package org.tinygroup.template;

/**
 * Created by luoguo on 2014/6/4.
 */
public class TemplateException extends Exception {
    public TemplateException(String msg) {
        super(msg);
    }

    public TemplateException(Exception e) {
        super(e);
    }
}
