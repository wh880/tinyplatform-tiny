package org.tinygroup.uiengine.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by luoguo on 2015/6/20.
 */
@XStreamAlias("compatibility-resource")
public class CompatibilityResource {
    @XStreamAlias("condition")
    @XStreamAsAttribute
    String condition;
    @XStreamAlias("css-resource")
    private String cssResource;
    @XStreamAlias("js-resource")
    private String jsResource;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCssResource() {
        return cssResource;
    }

    public void setCssResource(String cssResource) {
        this.cssResource = cssResource;
    }

    public String getJsResource() {
        return jsResource;
    }

    public void setJsResource(String jsResource) {
        this.jsResource = jsResource;
    }
}
