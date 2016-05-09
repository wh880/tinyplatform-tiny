package org.tinygroup.beancontext;

import java.util.Map;

import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.context.BaseContext;
import org.tinygroup.context.Context;
import org.tinygroup.context.util.ContextFactory;

public class BeanContextImpl implements Context{

	protected Context context;
	protected BeanContainer<?> beanContainer;

	public BeanContainer<?> getBeanContainer() {
		return beanContainer;
	}

	public void setBeanContainer(BeanContainer<?> beanContainer) {
		this.beanContainer = beanContainer;
	}

	public BeanContextImpl(Context context){
		this.context = context;
		this.beanContainer = BeanContainerFactory.getBeanContainer(getClass().getClassLoader());
	}
	
    public BeanContextImpl(){
		this(ContextFactory.getContext());
	}

	public <T> T put(String name, T object) {
		return context.put(name, object);
	}

	public boolean renameKey(String key, String newKey) {
		return context.renameKey(key, newKey);
	}

	public <T> T remove(String name) {
		return (T)context.remove(name);
	}

	public <T> T get(String name) {
		if(context.exist(name)){
		   return (T)context.get(name);
		}else{
		   //必须保存到上下文，否则每次返回不一定是同一个对象(scope可能是属性)
		   T t = (T)beanContainer.getBean(name);
		   context.put(name, t);
		   return t;
		}
	}

	public void putAll(Map<String, Object> map) {
		context.putAll(map);
	}

	public <T> T get(String name, T defaultValue) {
		return context.get(name, defaultValue);
	}

	public int size() {
		return context.size();
	}

	public boolean exist(String name) {
		return context.exist(name);
	}

	public BaseContext contain(String s) {
		return null;
	}


	public void clear() {
		context.clear();
	}

	public Map<String, Object> getItemMap() {
		return context.getItemMap();
	}

	public Context createSubContext(String contextName) {
		return context.createSubContext(contextName);
	}

	public <T> T remove(String contextName, String name) {
		return (T) context.remove(contextName, name);
	}

	public <T> T get(String contextName, String name) {
		return (T) context.get(contextName, name);
	}

	public <T> T put(String contextName, String name, T object) {
		return context.put(contextName, name, object);
	}

	public Context getParent() {
		return context.getParent();
	}

	public void setParent(Context parent) {
		context.setParent(parent);
	}

	public Context putSubContext(String contextName, Context context) {
		return context.putSubContext(contextName, context);
	}

	public Context removeSubContext(String contextName) {
		return context.removeSubContext(contextName);
	}

	public Context getSubContext(String contextName) {
		return context.getSubContext(contextName);
	}

	public void clearSubContext() {
		context.clearSubContext();
	}

	public Map<String, Context> getSubContextMap() {
		return context.getSubContextMap();
	}

	public <T> T getInSubContext(String contextName, String name) {
		return context.getInSubContext(contextName, name);
	}

	public Map<String, Object> getTotalItemMap() {
		return context.getTotalItemMap();
	}
}
