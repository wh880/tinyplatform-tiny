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
package org.tinygroup.context2object.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context.Context;
import org.tinygroup.context2object.ObjectAssembly;
import org.tinygroup.context2object.ObjectGenerator;
import org.tinygroup.context2object.TypeConverter;
import org.tinygroup.context2object.config.BasicTypeConverter;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

import com.google.common.collect.ObjectArrays;

public class ClassNameObjectGenerator extends BaseClassNameObjectGenerator implements
		ObjectGenerator<Object, String> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ClassNameObjectGenerator.class);
	private List<TypeConverter<?, ?>> typeConverterList = new ArrayList<TypeConverter<?, ?>>();
	

	public Object getObject(String varName, String bean, String className,
			ClassLoader loader, Context context) {
		if (className == null || "".equals(className)) {
			return getObject(varName, bean, null, context, null);
		}
		// 20130808注释LoaderManagerFactory
		// return getObject(varName, bean, LoaderManagerFactory.getManager()
		// .getClass(className), context, null);
		return getObject(varName, bean, getClazz(className, loader), context,
				null);
	}

	public Object getObjectArray(String varName, String className,
			ClassLoader loader, Context context) {
		// 20130808注释LoaderManagerFactory
		// return buildArrayObjectWithObject(varName, LoaderManagerFactory
		// .getManager().getClass(className), context, null);
		return buildArrayObjectWithObject(varName, getClazz(className, loader),
				context, null);
	}

	
	public Collection<Object> getObjectCollection(String collectionClassName,
			ClassLoader loader) {
		Class<?> collectionClass = getClazz(collectionClassName, loader);
		return (Collection<Object>) getObjectInstance(collectionClass);
	}

	public Collection<Object> getObjectCollection(String varName,
			String collectionName, String className, ClassLoader loader,
			Context context) {
		// 20130808注释LoaderManagerFactory
		// Class<?> collectionClass =
		// LoaderManagerFactory.getManager().getClass(
		// collectionName);
		// Class<?> clazz =
		// LoaderManagerFactory.getManager().getClass(className);
		Class<?> collectionClass = getClazz(collectionName, loader);
		Class<?> clazz = getClazz(className, loader);
		Collection<Object> collection = (Collection<Object>) getObjectInstance(collectionClass);
		buildCollection(varName, collection, clazz, context, null);
		if (collection.isEmpty()) {
			return null;
		}
		return collection;
	}

	private Object getObject(String varName, String bean, Class<?> clazz,
			Context context, String preName) {
		if (bean != null) {
			Object o = getInstanceBySpringBean(bean);
			if (o != null) { // 如果不为空则继续进行
				Object result = buildObject(varName, o, context, preName);
				if (result == null) {// 如果解析返回null(表明没有从cotext中获取任何属性，则返回bean)
					return o;
				} else {
					return result; // 否则返回解析结果
				}
			}
		}
		// 当通过beanid获取bean失败时，则按原有逻辑进行
		if (clazz == null || isSimpleType(clazz)) {
			return null;
		} else if (clazz.isArray()) {
			return buildArrayObjectWithArray(varName, clazz, context, preName);
		} else {
			Object o = getObjectInstance(clazz);
			return buildObject(varName, o, context, preName);
		}
	}

	private Object buildObject(String varName, Object object, Context context,
			String preName) {
		if (object == null) {
			return null;
		}
		Class<?> clazz = object.getClass();

		String objName = varName;
		ObjectAssembly assembly = getObjectAssembly(object.getClass());
		if (assembly != null) {
			assembly.assemble(varName, object, context);
			return object;
		}
		// 20130424
		// 属性是否全部为空，若全部为空，则返回空对象
		boolean allPropertyNull = true;
		if (isNull(objName)) {
			objName = getObjName(object);
		}
		for (PropertyDescriptor descriptor : PropertyUtils
				.getPropertyDescriptors(object.getClass())) {
			if (descriptor.getPropertyType().equals(Class.class)) {
				continue;
			}

			// 201402025修改此处代码，修改propertyName获取逻辑
			// String propertyName = getPropertyName(clazz,
			// descriptor.getName());
			String propertyName = descriptor.getName();
			Object propertyValue = getPerpertyValue(preName, objName,
					propertyName, context);// user.name,user_name,name
			if (propertyValue != null) { // 如果值取出来为空，则跳过不处理了
				try {
					if (descriptor.getPropertyType().equals( // 如果类型相同，或者值类型继承(实现)属性类型
							propertyValue.getClass())
							|| implmentInterface(propertyValue.getClass(),
									descriptor.getPropertyType())) {
						BeanUtils.setProperty(object, descriptor.getName(),
								propertyValue);
						allPropertyNull = false;
						continue;
					} else if (isSimpleType(descriptor.getPropertyType())) {// 若是简单类型
						if (String.class==propertyValue.getClass()) { // 如果值是String类型
							BeanUtils.setProperty(object, descriptor.getName(),
									BasicTypeConverter.getValue(
											propertyValue.toString(),
											descriptor.getPropertyType().getName()));
							allPropertyNull = false;
						} else if (isSimpleType(propertyValue.getClass())) { // 若值也是简单类型则赋值，非简单类型，则不处理
							BeanUtils.setProperty(object, descriptor.getName(),
									propertyValue.toString());
							allPropertyNull = false;
						} else {
							LOGGER.logMessage(LogLevel.WARN,
									"参数{0}.{1}赋值失败,期望类型{3},实际类型{4}", objName,
									propertyName, descriptor.getPropertyType(),
									propertyValue.getClass());
						}
						continue;
					}
					// else {
					// }
					// 以上处理均未进入，则该类型为其他类型，需要进行递归
				} catch (Exception e) {
					LOGGER.errorMessage("为属性{0}赋值时出现异常", e,
							descriptor.getName());
				}
			}

			// 查找转换器，如果存在转换器，但是值为空，则跳出不处理
			// 若转换器不存在，则继续进行处理
			TypeConverter typeConverter = getTypeConverter(descriptor
					.getPropertyType());
			if (typeConverter != null) {
				if (propertyValue != null) {
					try {
						BeanUtils.setProperty(object, descriptor.getName(),
								typeConverter.getObject(propertyValue));
						allPropertyNull = false;
					} catch (Exception e) {
						LOGGER.errorMessage("为属性{0}赋值时出现异常", e,
								descriptor.getName());
					}
				}
				continue;
			}
			// 对象值为空，且转换器不存在
			if (!isSimpleType(descriptor.getPropertyType())) {
				// 如果是共它类型则递归调用
				try {
					String newPreName = getReallyPropertyName(preName, objName,
							propertyName);
					// ===============begin======================
					// 20151208修改逻辑为getDeclaredFieldWithParent获取type再获取属性，解决无法获取父类的属性的问题
					// Class<?> type = null;
					// try{
					Field field = getDeclaredFieldWithParent(clazz,
							propertyName);
					// type =
					// clazz.getDeclaredField(descriptor.getName()).getType();
					// type = descriptor.getPropertyType();
					// }catch (NoSuchFieldException e)

					if (field == null) {
						LOGGER.logMessage(LogLevel.WARN, "{}不存在字段{}",
								clazz.getName(), propertyName);
						continue;
					}
					Class<?> type = field.getType();
					// ===============end======================
					if (type.isArray()) {// 如果是数组
						Object value = getObject(newPreName, null,
								descriptor.getPropertyType(), context,
								null);
						if (value != null) {
							BeanUtils.setProperty(object, descriptor.getName(),
									value);
							allPropertyNull = false;
						}
					} else if (implmentInterface(descriptor.getPropertyType(),
							Collection.class)) {// 如果是集合
						// ===============begin======================
						// 20151208修改逻辑为getDeclaredFieldWithParent获取type再获取属性，解决无法获取父类的属性的问题
						Field propertyType = getDeclaredFieldWithParent(clazz,
								propertyName);
						if (propertyType == null) {
							LOGGER.logMessage(LogLevel.WARN, "{}不存在字段{}",
									clazz.getName(), propertyName);
							continue;
						}
						// ParameterizedType pt = (ParameterizedType) clazz
						// .getDeclaredField(descriptor.getName())
						// .getGenericType();
						ParameterizedType pt = (ParameterizedType) propertyType
								.getGenericType();
						// ===============end======================
						Type[] actualTypeArguments = pt
								.getActualTypeArguments();
						Collection<Object> collection = (Collection<Object>) getObjectInstance(type);
						buildCollection(newPreName, collection,
								(Class) actualTypeArguments[0], context,
								null);
						if (!collection.isEmpty()) {
							BeanUtils.setProperty(object, descriptor.getName(),
									collection);
							allPropertyNull = false;
						}
					} else {// 如果是其他类型
						Object value = getObject(newPreName, null,
								descriptor.getPropertyType(), context,
								null);
						if (value != null) {
							BeanUtils.setProperty(object, descriptor.getName(),
									value);
							allPropertyNull = false;
						}
					}
				} catch (Exception e) {
					LOGGER.errorMessage("为属性{0}赋值时出现异常", e,
							descriptor.getName());
				}
			}
		}
		if (allPropertyNull) {
			return null;
		}
		return object;
	}

	

	private void buildCollection(String varName, Collection<Object> collection,
			Class<?> clazz, Context context, String preName) {
		if (clazz == null) {
			return;
		} else if (isSimpleType(clazz)) {
			buildCollectionSimple(varName, collection, clazz, context, preName);
			// } else if (clazz.isEnum()) {
			// buildCollectionEnum(varName, collection, clazz, context,
			// preName);
		} else {
			buildCollectionObject(varName, collection, clazz, context, preName);
		}
	}


	private void buildCollectionSimple(String varName,
			Collection<Object> collection, Class<?> clazz, Context context,
			String preName) {
		if (clazz == null) {
			return;
		}
		String reallyVarName = varName;
		if (isNull(reallyVarName)) {
			reallyVarName = preName;
		}
		if (isNull(reallyVarName)) {
			throw new RuntimeException("简单类型数组或集合,变量名不可为空");
		}

		Object propertyValue = getPerpertyValue(reallyVarName, context);
		if (propertyValue != null) {
			if (propertyValue.getClass().isArray()) {
				// 如果是数组
				Object[] objArray = (Object[]) propertyValue;
				if(objArray.getClass().getComponentType()==String.class){
					for (Object o : objArray) {
						collection.add(BasicTypeConverter.getValue((String)o, clazz.getName()));
					}
				}else{
					for (Object o : objArray) {
						collection.add(o);
					}
				}
			} else {
				if(propertyValue.getClass()==String.class){
					collection.add(BasicTypeConverter.getValue((String)propertyValue, clazz.getName()));
				}else{
					collection.add(propertyValue);
				}
				
			}
		}
		// return collection;
	}

	// 处理非复杂对象，如enmu等类型
	// 此种方式判断依据是preName.varName存在一个数组值
	// 因为如果是复杂对象，那么必然不存在preName.varName数组，而是存在preName.varName.propertyName数组
	private boolean checkIfNotComplexObjectCollection(String varName,
			Collection<Object> collection, Class<?> clazz, Context context,
			String preName) {
		String reallyName = preName;
		if (StringUtil.isBlank(varName) && StringUtil.isBlank(preName)) {
			return false;
		} else if (StringUtil.isBlank(preName)) {
			reallyName = varName;
		} else if (StringUtil.isBlank(varName)) {
			reallyName = preName;
		} else {
			reallyName = getReallyPropertyName(null, preName, varName);
		}

		Object value = getPerpertyValue(reallyName, context);
		if (value == null) {
			return false;
		}
		if (!value.getClass().isArray()) {
			return false;
		}
		Object[] arrays = (Object[]) value;

		for (int i = 0; i < arrays.length; i++) {
			Object propertyValue = arrays[i];
			if (clazz.equals( // 如果类型相同，或者值类型继承(实现)属性类型
					propertyValue.getClass())
					|| implmentInterface(propertyValue.getClass(), clazz)) {
				collection.add(propertyValue);
				continue;
			}
			TypeConverter typeConverter = getTypeConverter(clazz);
			if (typeConverter != null) {
				if (propertyValue != null) {
					collection.add(typeConverter.getObject(propertyValue));
				}
			} else {
				throw new RuntimeException("未找到对象" + clazz + "的转换器,源数据类型:"
						+ propertyValue.getClass());
				// throw new
				// RuntimeException("无法处理对象"+objecList.get(index)+"的属性"+descriptor.getName()+"期望类型"+descriptor
				// .getPropertyType()+"实际值:"+propertyValue);
			}
		}
		return true;
	}

	/**
	 * 处理非基本类型的集合
	 * 
	 * @param varName
	 *            变量名
	 * @param collection
	 *            集合对象
	 * @param clazz
	 *            类型class
	 * @param context
	 *            上下文
	 * @param preName
	 *            前置变量名
	 */
	private void buildCollectionObject(String varName,
			Collection<Object> collection, Class<?> clazz, Context context,
			String preName) {
		if (clazz == null) {
			return;
		}
		// 如果是非复杂对象,比如Enmu\BigDecimal等单值对象
		if (checkIfNotComplexObjectCollection(varName, collection, clazz,
				context, preName)) {
			return;
		}
		// 处理复杂对象
		dealComplexObject(varName, collection, clazz, context, preName);
	}

	private void dealComplexObject(String varName,
			Collection<Object> collection, Class<?> clazz, Context context,
			String preName) {
		Object object = getObjectInstance(clazz);
		String objName = varName;
		if (isNull(objName)) {
			// 20130806修改变量名为首字母小写
			objName = getObjName(object);
		}
		Map<String, Object> valueMap = new HashMap<String, Object>();
		Class<?> reallyType = object.getClass();
		int size = -1;

		// 先计算出collection的size
		for (PropertyDescriptor descriptor : PropertyUtils
				.getPropertyDescriptors(reallyType)) {
			if (descriptor.getPropertyType().equals(Class.class)) {
				continue;
			}
			if (size != -1) {
				break;
			}
			// 201402025修改此处代码，修改propertyName获取逻辑
			// String propertyName = getPropertyName(reallyType,
			// descriptor.getName());
			String propertyName = descriptor.getName();

			Object propertyValue = getPerpertyValue(preName, objName,
					propertyName, context);
			if (propertyValue != null) {
				valueMap.put(propertyName, propertyValue);
				if (propertyValue.getClass().isArray()) {
					// 如果是数组
					Object[] objArray = (Object[]) propertyValue;
					if (objArray.length > size) {
						size = objArray.length;
					}
				} else if (size == -1) {
					size = 1;
				}
			}
		}
		// 20130424
		// 说明集合完全没有相关的值
		// 返回空集合
		if (size == -1) {
			return;
		}
		// 初始化objecList的数据
		List<Object> objecList = new ArrayList<Object>();
		for (int i = 0; i < size; i++) {
			Object o = getObjectInstance(clazz);
			objecList.add(o);
		}

		for (PropertyDescriptor descriptor : PropertyUtils
				.getPropertyDescriptors(reallyType)) {
			if (descriptor.getPropertyType().equals(Class.class)) {
				continue;
			}
			// 201402025修改此处代码，修改propertyName获取逻辑
			// String propertyName = getPropertyName(reallyType,
			// descriptor.getName());
			String propertyName = descriptor.getName();
			Object propertyValue = getPerpertyValue(preName, objName,
					propertyName, context);
			if (propertyValue == null) {
				continue;
			}

			try {
				if (size == 1) {
					Object realvalue = propertyValue;
					if (propertyValue.getClass().isArray()) {
						realvalue = ((Object[]) propertyValue)[0];
					}
					setSimpleValue(objecList, 0, descriptor, realvalue);

				} else {
					Object[] objArray = (Object[]) propertyValue;
					for (int i = 0; i < size; i++) {
						// BeanUtils.setProperty(objecList.get(i),
						// descriptor.getName(), objArray[i]);
						setSimpleValue(objecList, i, descriptor, objArray[i]);
					}
				}
				continue;
			} catch (Exception e) {
				LOGGER.errorMessage("为属性{0}赋值时出现异常", e, descriptor.getName());
			}
		}
		// objecList的数据放入collection
		for (Object o : objecList) {
			collection.add(o);
		}
		// return collection;
	}

	private void setSimpleValue(List<Object> objecList, int index,
			PropertyDescriptor descriptor, Object propertyValue)
			throws IllegalAccessException, InvocationTargetException {

		if (descriptor.getPropertyType().equals( // 如果类型相同，或者值类型继承(实现)属性类型
				propertyValue.getClass())
				|| implmentInterface(propertyValue.getClass(),
						descriptor.getPropertyType())) {
			BeanUtils.setProperty(objecList.get(index), descriptor.getName(),
					propertyValue);
			return;
		} else if (isSimpleType(descriptor.getPropertyType())) {// 若是简单类型
			if (isSimpleType(propertyValue.getClass())) { // 若值也是简单类型则赋值，非简单类型，则不处理
				BeanUtils.setProperty(objecList.get(index),
						descriptor.getName(), propertyValue.toString());
			} else {
				LOGGER.logMessage(LogLevel.WARN,
						"对象{0}属性{1}赋值失败,期望类型{3},实际类型{4}", objecList.get(index),
						descriptor.getName(), descriptor.getPropertyType(),
						propertyValue.getClass());
			}
			return;
		}
		TypeConverter typeConverter = getTypeConverter(descriptor
				.getPropertyType());

		if (typeConverter != null) {
			if (propertyValue != null) {
				try {
					BeanUtils.setProperty(objecList.get(index),
							descriptor.getName(),
							typeConverter.getObject(propertyValue));
				} catch (Exception e) {
					LOGGER.errorMessage("为属性{0}赋值时出现异常", e,
							descriptor.getName());
				}
			}
		} else {
			throw new RuntimeException("无法处理对象" + objecList.get(index) + "的属性"
					+ descriptor.getName() + "期望类型"
					+ descriptor.getPropertyType() + "实际值:" + propertyValue);
		}

		// if(descriptor.getPropertyType().isEnum()){
		// BeanUtils.setProperty(objecList.get(index),
		// descriptor.getName(), getEnmuObject(descriptor.getPropertyType(),
		// propertyValue));
		// }else{
		// BeanUtils.setProperty(objecList.get(index),
		// descriptor.getName(), propertyValue);
		// }
	}


	private Object buildArrayObjectWithArray(String varName,
			Class<?> arrayClass, Context context, String preName) {
		return buildArrayObjectWithObject(varName,
				arrayClass.getComponentType(), context, preName);
	}

	private Object buildArrayObjectWithObject(String varName,
			Class<?> objectClass, Context context, String preName) {
		if (objectClass == null) {
			return null;
		}
		Collection<Object> collection = new ArrayList<Object>();
		buildCollection(varName, collection, objectClass, context, preName);
		if (collection.isEmpty()) {
			return null;
		} else {
			Object array = Array.newInstance(objectClass, collection.size());
			Iterator<Object> iterator = collection.iterator();
			Object[] objectsArray = (Object[]) array;
			for (int i = 0; i < collection.size(); i++) {
				objectsArray[i] = iterator.next();
			}
			return array;
		}
	}

	private TypeConverter<?, ?> getTypeConverter(Class<?> destType) {
		for (TypeConverter<?, ?> typeConverter : typeConverterList) {
			if (typeConverter.getDestinationType().equals(destType)) {
				return typeConverter;
			}
		}
		return null;
	}

	


	

	

	public void addTypeConverter(TypeConverter<?, ?> typeConverter) {
		typeConverterList.add(typeConverter);
	}

	public void removeTypeConverter(TypeConverter<?, ?> typeConverter) {
		typeConverterList.remove(typeConverter);
	}


	

	

}
