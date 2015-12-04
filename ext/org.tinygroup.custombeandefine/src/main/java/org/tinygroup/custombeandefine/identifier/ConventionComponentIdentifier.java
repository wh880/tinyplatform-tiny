package org.tinygroup.custombeandefine.identifier;

import java.util.List;

public interface ConventionComponentIdentifier {
    /**
     * 约定组件定义的包路径正则，匹配类的包路径正则
     * @return
     */
    List<String> getPackagePatterns();
     
    /**
     * 该类是否是约定组件
     * @param className
     * @return
     */
    boolean isComponent(String className);
}
