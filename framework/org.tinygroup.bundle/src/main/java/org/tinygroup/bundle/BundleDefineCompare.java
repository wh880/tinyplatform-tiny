package org.tinygroup.bundle;


import org.tinygroup.bundle.config.BundleDefine;

/**
 * 服务比较接口，对仅版本不同的服务进行比较
 *
 * @author luoguo
 */
public interface BundleDefineCompare {
    /**
     * 比较两个服务的版本
     *
     * @param source
     * @param dest
     * @return 当两个服务不可比较时，抛出此异常
     */
    int compare(BundleDefine source, BundleDefine dest);

}
