package org.tinygroup.template.impl;

import org.tinygroup.template.Macro;

/**
 * 抽象宏
 * Created by luoguo on 2014/6/6.
 */
public abstract class AbstractMacro implements Macro {
    private String name;
    private String[] parameterNames;

    public void setName(String name) {
        this.name = name;
    }

    public void setParameterNames(String[] parameterNames) {
        this.parameterNames = parameterNames;
    }

    public AbstractMacro(String name, String[] parameterNames) {
        this.name = name;
        this.parameterNames = parameterNames;
    }

    public String getName() {
        return name;
    }

    public String[] getParameterNames() {
        return parameterNames;
    }

}
