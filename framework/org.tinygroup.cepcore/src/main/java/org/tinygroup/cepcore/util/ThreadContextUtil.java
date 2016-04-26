package org.tinygroup.cepcore.util;

import org.tinygroup.context.Context;
import org.tinygroup.context.util.ContextFactory;

public class ThreadContextUtil {
	private final static ThreadLocal<Context> context = new ThreadLocal<Context>();
	public final static String THREAD_CONTEXT_KEY = "tiny_sys_service_thread_context";

	/**
	 * 获取当前上下文context
	 * @return
	 */
	private static Context getCurrentThreadContext() {
		Context currentcontext = context.get();
		if (currentcontext == null) {
			currentcontext = ContextFactory.getContext();
			context.set(currentcontext);
		}
		return currentcontext;
	}

	public static Object get(String name) {
		return getCurrentThreadContext().get(name);
	}

	public static void put(String name, Object value) {
		getCurrentThreadContext().put(name, value);
	}

	public static Object remove(String name) {
		return getCurrentThreadContext().remove(name);
	}

	public static void putCurrentThreadContextIntoContext(Context mainContext) {
		Context currentContext = getCurrentThreadContext();
		mainContext.put(THREAD_CONTEXT_KEY, currentContext);
//		mainContext.putSubContext(THREAD_CONTEXT_KEY, currentContext);
	}
	public static void parseCurrentThreadContext(Context mainContext){
		if(!mainContext.exist(THREAD_CONTEXT_KEY)){
			return ;
		}
		Context currentContext = mainContext.remove(THREAD_CONTEXT_KEY);
//		Context currentContext = mainContext.getSubContext(THREAD_CONTEXT_KEY);
		if(currentContext!=null){
			context.set(currentContext);
		}
	}
}