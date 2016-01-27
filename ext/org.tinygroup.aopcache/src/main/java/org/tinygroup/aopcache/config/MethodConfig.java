package org.tinygroup.aopcache.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("method-config")
public class MethodConfig {

    @XStreamAlias("method-name")
    @XStreamAsAttribute
    private String methodName;

    @XStreamImplicit
    private List<ParameterType> paramTypes;
    @XStreamAlias("cache-actions")
    private CacheActions cacheActions;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<ParameterType> getParamTypes() {
        if (paramTypes == null) {
            paramTypes = new ArrayList<ParameterType>();
        }
        return paramTypes;
    }

    public void setParamTypes(List<ParameterType> paramTypes) {
        this.paramTypes = paramTypes;
    }

    public CacheActions getCacheActions() {
        return cacheActions;
    }

    public void setCacheActions(CacheActions cacheActions) {
        this.cacheActions = cacheActions;
    }

}
