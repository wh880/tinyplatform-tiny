package org.tinygroup.threadgroup;

/**
 * 当发生异常时，回调
 * Created by luoguo on 14-2-24.
 */
public interface ExceptionCallBack {
    void callBack(Processor processor, Throwable throwable);
}
