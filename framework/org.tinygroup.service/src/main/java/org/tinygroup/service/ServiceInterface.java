package org.tinygroup.service;

import org.tinygroup.context.Context;

/**
 * 服务接口
 * 通过实现此接口来编写服务
 * 通时有一个默认的约定：
 * 在这个接口的实现类中，必须有一个execute方法，execute方法用于完成服务的业务逻辑实现，其入参及返回值必须实现序列化接口
 */
public interface ServiceInterface {
    /**
     * 返回服务ID
     *
     * @return
     */
    String getServiceId();

    /**
     * 返回目录，多重目录用“.”分隔
     *
     * @return
     */
    String getCategory();

    /**
     * 返回结果在上下文中的Key值
     *
     * @return
     */
    String getResultKey();
    
//    Context execute(Context context);

}
