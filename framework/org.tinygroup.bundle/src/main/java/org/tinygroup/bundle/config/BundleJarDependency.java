package org.tinygroup.bundle.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 对外部Jar的依赖接口，这里只能声明公共包中的
 */
@XStreamAlias("jar-dependency")
public class BundleJarDependency {
    @XStreamAsAttribute
    @XStreamAlias("file-name")
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
