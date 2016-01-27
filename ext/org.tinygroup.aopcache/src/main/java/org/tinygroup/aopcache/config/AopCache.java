package org.tinygroup.aopcache.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("aop-cache")
public class AopCache {

    @XStreamAlias("class-name")
    @XStreamAsAttribute
    private String className;

    @XStreamImplicit
    private List<MethodConfig> methodConfigs;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<MethodConfig> getMethodConfigs() {
        if (methodConfigs == null) {
            methodConfigs = new ArrayList<MethodConfig>();
        }
        return methodConfigs;
    }

    public void setMethodConfigs(List<MethodConfig> methodConfigs) {
        this.methodConfigs = methodConfigs;
    }

}
