/*
 * Alipay.com Inc. Copyright (c) 2004-2105 All Rights Reserved.
 */
package org.tinygroup.springmvc.coc;

import java.util.List;

/**
 * 约定组件定义接口
 * @author renhui
 *
 */
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
