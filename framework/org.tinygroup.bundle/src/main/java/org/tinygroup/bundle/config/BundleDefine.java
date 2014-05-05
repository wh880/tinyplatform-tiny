package org.tinygroup.bundle.config;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 这里的Bundle，取其杂物箱之意
 * BundleDefine，则是杂物箱定义的意思，它以静态的方式定义了杂物箱的名字，依赖其它的杂物箱及公共文件
 * Created by luoguo on 2014/5/4.
 */
@XStreamAlias("bundle")
public class BundleDefine {
    @XStreamAsAttribute
    private String name;
    @XStreamAsAttribute
    private String title;
    private String dependencies;
    @XStreamAlias("common-jars")
    private String commonJars;
    @XStreamAlias("short-description")
    private String shortDescription;
    @XStreamAlias("long-description")
    private String longDescription;
    @XStreamAlias("bundle-activator")
    private String bundleActivator;
    private transient List<String> dependencyByList = new ArrayList<String>();
    private transient boolean isHealth = false;
    private transient String[] dependencyArray = null;


	public String getBundleActivator() {
		return bundleActivator;
	}

	public void setBundleActivator(String bundleActivator) {
		this.bundleActivator = bundleActivator;
	}

	public boolean isHealth() {
        return isHealth;
    }

    public void setHealth(boolean isHealth) {
        this.isHealth = isHealth;
    }

    public boolean isDependency(String name) {
        for (String key : getDependencyArray()) {
            if (key.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String[] getDependencyArray() {
        if (dependencyArray == null) {
            if (dependencies == null || dependencies.trim().length() == 0) {
                dependencyArray = new String[0];
            } else {
                dependencyArray = dependencies.split(",");
            }
        }
        return dependencyArray;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDependencies() {
        return dependencies;
    }

    public void setDependencies(String dependencies) {
        this.dependencies = dependencies;
    }

    public String getCommonJars() {
        return commonJars;
    }

    public void setCommonJars(String commonJars) {
        this.commonJars = commonJars;
    }
}
