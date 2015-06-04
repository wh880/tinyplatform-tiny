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
package org.tinygroup.beanwrapper;

import java.beans.PropertyEditor;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.springutil.SpringBeanContainer;

public class BeanWrapperHolder {
	private static BeanWrapperHolder holder;

	private BeanWrapperHolder() {
	}

	public static BeanWrapperHolder getInstance() {
		if (holder == null) {
			holder = new BeanWrapperHolder();
		}
		return holder;
	}

	private void registerPropertyEditor(AbstractBeanFactory beanFactory,
			PropertyEditorRegistry registry) {
		Set propertyEditorRegistrars = beanFactory
				.getPropertyEditorRegistrars();
		if (!CollectionUtil.isEmpty(propertyEditorRegistrars)) {
			for (Iterator it = propertyEditorRegistrars.iterator(); it
					.hasNext();) {
				PropertyEditorRegistrar registrar = (PropertyEditorRegistrar) it
						.next();
				registrar.registerCustomEditors(registry);
			}
		}
	}

	private void registerCustomEditor(AbstractBeanFactory beanFactory,
			PropertyEditorRegistry registry) {
		Map customEditors = beanFactory.getCustomEditors();
		if (!CollectionUtil.isEmpty(customEditors)) {
			for (Iterator it = customEditors.entrySet().iterator(); it
					.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				Class requiredType = (Class) entry.getKey();
				Object value = entry.getValue();
				if (value instanceof PropertyEditor) {
					PropertyEditor editor = (PropertyEditor) value;
					registry.registerCustomEditor(requiredType, editor);
				} else if (value instanceof Class) {
					Class editorClass = (Class) value;
					registry.registerCustomEditor(requiredType,
							(PropertyEditor) BeanUtils
									.instantiateClass(editorClass));
				} else {
					throw new IllegalStateException(
							"Illegal custom editor value type: "
									+ value.getClass().getName());
				}
			}
		}
	}

	public BeanWrapper getBeanWrapper() {
		BeanWrapper beanWrapper = new BeanWrapperImpl();
		registerEditor(beanWrapper);
		return beanWrapper;
	}

	private void registerEditor(BeanWrapper beanWrapper) {
		SpringBeanContainer container = (SpringBeanContainer) BeanContainerFactory
				.getBeanContainer(getClass().getClassLoader());
		if (container != null) {
			AbstractBeanFactory beanFactory = (AbstractBeanFactory) ((ConfigurableApplicationContext) container
					.getBeanContainerPrototype()).getBeanFactory();
			registerPropertyEditor(beanFactory, beanWrapper);
			registerCustomEditor(beanFactory, beanWrapper);
		}
	}

	public BeanWrapper getBeanWrapper(Object instance) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(instance);
		registerEditor(beanWrapper);
		return beanWrapper;
	}

	public BeanWrapper getBeanWrapper(Class clazz) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(clazz);
		registerEditor(beanWrapper);
		return beanWrapper;
	}

}
