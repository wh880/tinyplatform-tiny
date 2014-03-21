package org.tinygroup.bundle;

/**
 * 针对级别的操作接口
 *
 * @author luoguo
 */
public interface BundleLevelManager {
    /**
     * 初始化指定级别以上的版本
     *
     * @param level
     */
    void init(int level);

    /**
     * 组装指定级别以上的版本
     *
     * @param level
     */
    void assemble(int level);

    /**
     * 开始指定级别以上的版本
     *
     * @param level
     */
    void start(int level);

    /**
     * 暂停指定级别以下的版本
     *
     * @param level
     */
    void pause(int level);

    /**
     * 停止指定级别以下的版本
     *
     * @param level
     */
    void stop(int level);

    /**
     * 卸载指定级别以下的版本
     *
     * @param level
     */
    void disassemble(int level);

    /**
     * 销毁指定级别以下的版本
     *
     * @param level
     */
    void destroy(int level);

}
