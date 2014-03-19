package org.tinygroup.bundle;

/**
 * 针对所有的插件的操作接口
 *
 * @author luoguo
 */
public interface BundleBatchManager {
    /**
     * 初始化所有Bundle
     */
    void init();

    /**
     * 组装所有Bundle
     */
    void assemble();

    /**
     * 启动所有Bundle
     */
    void start();

    /**
     * 暂停所有Bundle
     */
    void pause();

    /**
     * 停止所有Bundle
     */
    void stop();

    /**
     * 拆解所有Bundle
     */
    void disassemble();

    /**
     * 销毁
     */
    void destroy();

}
