/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.beancontainer;


public class BeanContainerFactory {
	private static BeanContainer<?> container;

	/**
	 * 请使用initBeanContainer方法
	 * @param beanClassName
	 */
	@Deprecated
	public static void setBeanContainer(String beanClassName) {
		if (container != null
				&& container.getClass().getName().equals(beanClassName)) {
			return;
		}
		try {
			container = (BeanContainer) Class.forName(beanClassName)
					.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("初始化beancontainer:"+beanClassName+"失败");
		}
	}

	public static void initBeanContainer(String beanClassName) {
		if (container != null
				&& !container.getClass().getName().equals(beanClassName)) {
			throw new RuntimeException("container已存在,且类型与" + beanClassName
					+ "不匹配,请勿重复执行初始化");
		}
		if(container!=null){
			return;
		}
		try {
			container = (BeanContainer) Class.forName(beanClassName)
					.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("初始化beancontainer:"+beanClassName+"失败");
		}
	}

	public static BeanContainer<?> getBeanContainer(ClassLoader loader) {
		if (loader == BeanContainerFactory.class.getClassLoader()) {
			return container;
		} else {
			return container.getSubBeanContainer(loader);
		}

	}

	public static void removeBeanContainer(ClassLoader loader) {
		container.removeSubBeanContainer(loader);

	}
	
	public static void destroy(){
		container = null;
	}
}
