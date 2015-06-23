package org.tinygroup.uiengine.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Created by luoguo on 2015/6/20.
 */
@XStreamAlias("compatibility-resources")
public class CompatibilityResources {
	@XStreamImplicit
    private List<CompatibilityResource> compatibilityResources;

    public List<CompatibilityResource> getCompatibilityResources() {
        return compatibilityResources;
    }

    public void setCompatibilityResources(List<CompatibilityResource> compatibilityResources) {
        this.compatibilityResources = compatibilityResources;
    }
}
