package org.tinygroup.template.impl;

import org.tinygroup.context.impl.ContextImpl;

import java.util.Map;

/**
 * Created by luoguo on 2014/6/6.
 */
public class TemplateContextDefault extends ContextImpl implements org.tinygroup.template.TemplateContext {
    public TemplateContextDefault(){

    }
    public TemplateContextDefault(Map dataMap){
        super(dataMap);
    }
}
