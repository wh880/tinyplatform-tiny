package org.tinygroup.template.impl;

import org.tinygroup.context.impl.ContextImpl;

import java.util.Map;

/**
 * Created by luoguo on 2014/6/6.
 */
public class TemplateContextImpl extends ContextImpl implements org.tinygroup.template.TemplateContext {
    public TemplateContextImpl(){

    }
    public TemplateContextImpl(Map dataMap){
        super(dataMap);
    }
}
