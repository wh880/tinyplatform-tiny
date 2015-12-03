package org.tinygroup.aopcache;

import org.aopalliance.intercept.MethodInvocation;
import org.tinygroup.aopcache.base.CacheMetadata;

/**
 * aop缓存处理接口
 * 
 * @author renhui
 *
 */
public interface AopCacheProcessor{

	/**
	 * 前置处理操作
	 * 
	 * @param metadata
	 * @param invocation
	 * @return true:可以执行下一个处理器,false:认为流程已经执行完毕，调用
	 */
	public boolean preProcess(CacheMetadata metadata,
			MethodInvocation invocation);

	/**
	 * @param metadata
	 * @param invocation
	 *            后置处理操作
	 * @param result 执行结果
	 */
	public void postProcess(CacheMetadata metadata, MethodInvocation invocation, Object result);

	/**
	 * @param metadata
	 * @param invocation
	 *            流程结束操作,方法返回值作为AopCacheInterceptor。invoke(MethodInvocation
	 *            invocation)方法的返回值
	 * @return
	 */
	public Object endProcessor(CacheMetadata metadata,
			MethodInvocation invocation);

}
