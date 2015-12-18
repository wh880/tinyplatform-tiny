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
import org.tinygroup.context2object.TypeCreator;
import org.tinygroup.context2object.config.BasicTypeConverter;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class ClassNameObjectGeneratorWithException implements ObjectGenerator<Object, String> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ClassNameObjectGeneratorWithException.class);
	private List<TypeCreator<?>> typeCreatorList = new ArrayList<TypeCreator<?>>();
	private Map<Class, List<TypeConverter<?, ?>>> typeConverterMap = new HashMap<Class, List<TypeConverter<?, ?>>>();
	private List<ObjectAssembly<?>> assemblies = new ArrayList<ObjectAssembly<?>>();

	public Object getObject(String varName, String bean, String className,
			ClassLoader loader, Context context) {
		if (StringUtil.isBlank(className) && StringUtil.isBlank(bean)) {
			throw new RuntimeException("传入的className和beanID不可同时为空");
		}
		Object o = null;
		Class clazz = null;
		if (bean != null) { // 以bean为优先
			o = getInstanceBySpringBean(bean);
			clazz = o.getClass();
		} else {
			// 如果传入的className不为空
			clazz = getClazz(className, loader);
		}
		Object parseResult = getObject(null, varName, clazz, loader, context);
		if (parseResult == null) {
			return o;
		}
		return parseResult;
	}

	private Object getObject(String preName, String varName, Class<?> clazz,
			ClassLoader loader, Context context) {
		List<TypeConverter<?, ?>> typeConverterList = getTypeConverterList(clazz);
		if (typeConverterList != null) {// 如果存在typeConverter
			Object value = context.get(varName);
			if (value == null) {
				LOGGER.logMessage(LogLevel.WARN, "类型:{0}在上下文中的变量名:{1}值为null",
						clazz, varName);
				return typeConverterList.get(0).getObject(null);
			}
			for (TypeConverter typeConverter : typeConverterList) {
				if (value.getClass() == typeConverter.getSourceType()) {
					return typeConverter.getObject(value);
				}
			}
			// throw new
			// RuntimeException("未从已有的类型转换器中找到合适的转换器，目标类型:"+className+",值:"+value+",类型:"+value.getClass());
		}
		// 没有找到合适的转换器类型，继续执行
		// 处理基本类型
		if (isSimpleType(clazz)) {
			return getSimpleTypeValue(getReallyPropertyName(null, preName, varName), clazz, context);
		} else {
			// 处理对象
			Object o = getObjectInstance(clazz);
			Object result = buildObject(null, varName, o, loader, context);
			return result;
		}
	}

	/**
	 * 到了这一步 object对应的类型，既不是数组、集合、基本类型，同时也不存在转换器 只能进行解析拼装
	 */
	private Object buildObject(String preName, String varName, Object object,
			ClassLoader loader, Context context) {
		String objName = varName;
		Class clazz = object.getClass();
		ObjectAssembly assembly = getObjectAssembly(object.getClass());
		if (assembly != null) {
			assembly.assemble(varName, object, context);
			return object;
		}
		boolean allPropertyNull = true;
		if (isNull(objName)) {
			objName = getObjName(object);
		}
		for (PropertyDescriptor descriptor : PropertyUtils
				.getPropertyDescriptors(object.getClass())) {
			Class<?> propertyType = descriptor.getPropertyType();
			String propertyName = descriptor.getName();
			if (propertyType.equals(Class.class)) {
				continue;
			}
			if (implmentInterface(propertyType, Collection.class)) {
				// 如果是集合类型
				Field field = getDeclaredFieldWithParent(clazz, propertyName);
				if (field == null) {
					LOGGER.logMessage(LogLevel.WARN, "{}不存在字段{}",
							clazz.getName(), propertyName);
					continue;
				}
				String newPreName = getReallyPropertyName(preName, objName,
						propertyName);
				Class<?> type = field.getType();
				ParameterizedType pt = (ParameterizedType) field
						.getGenericType();
				Type[] actualTypeArguments = pt.getActualTypeArguments();
				Collection<Object> collection = (Collection<Object>) getObjectInstance(type);
				buildCollection(newPreName, null, collection,
						(Class) actualTypeArguments[0], context);
				if (!collection.isEmpty()) {
					try {
						BeanUtils.setProperty(object, descriptor.getName(),
								collection);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					allPropertyNull = false;
				}
			} else if (propertyType.isArray()) {
				Object value = buildArrayObjectWithObject(preName, varName, propertyType.getComponentType(), context);
				if (value != null) {
					try {
						BeanUtils.setProperty(object, descriptor.getName(),
								value);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					allPropertyNull = false;
				}
			} else {
				Object o = getObject(objName, propertyName, propertyType,
						loader, context);
				if (o != null) {
					allPropertyNull = false;
					try {
						BeanUtils.setProperty(object, descriptor.getName(), o);
					} catch (Exception e) {
						throw new RuntimeException("为对象:" + object + "属性:"
								+ descriptor.getName() + "赋值:" + o + "时发生异常", e);
					}
				}
			}

		}
		if (allPropertyNull) {
			return null;
		}
		return object;
	}

	private void buildCollection(String preName, String varName,
			Collection<Object> collection, Class<?> clazz, Context context) {
		if (clazz == null) {
			throw new RuntimeException("组装集合时，传入的集合内对象类型不可为");
		}
		if (isSimpleType(clazz)) {
			String reallyVarName = varName;
			if (isNull(reallyVarName)) {
				reallyVarName = preName;
			}
			if (isNull(reallyVarName)) {
				throw new RuntimeException("简单类型数组或集合,变量名不可为空");
			}
			Object propertyValue = getPerpertyValue(reallyVarName, context);
			if (propertyValue != null) { // 如果值不为空
				if (propertyValue.getClass().isArray()) { // 值为数组
					// 如果是数组
					Object[] objArray = (Object[]) propertyValue;
					for (Object o : objArray) {
						collection.add(o);
					}
				} else {
					// 值不是数组
					collection.add(propertyValue);
				}
			}
		}
		// 单值类型
		if (checkIfNotComplexObjectCollection(varName, collection, clazz,
				context, preName)) {
			return;
		}
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
			String propertyName = descriptor.getName();
			Object propertyValue = getPerpertyValue(preName, objName,
					propertyName, context);
			if (propertyValue != null) {
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
					if (realvalue.getClass() == descriptor.getPropertyType()
							|| implmentInterface(realvalue.getClass(),
									descriptor.getPropertyType())) {
						BeanUtils.setProperty(objecList.get(0),
								descriptor.getName(), realvalue);
					} else if (isSimpleType(propertyValue.getClass())) { // 若值也是简单类型则赋值，非简单类型，则不处理
						BeanUtils.setProperty(objecList.get(0),
								descriptor.getName(), realvalue);
					} else {
						TypeConverter converter = getTypeConverter(
								descriptor.getPropertyType(),
								realvalue.getClass());
						BeanUtils.setProperty(objecList.get(0),
								descriptor.getName(),
								converter.getObject(converter));
					}

				} else {
					Object[] objArray = (Object[]) propertyValue;
					Class componentType = objArray.getClass()
							.getComponentType();
					if (componentType == descriptor.getPropertyType()
							|| implmentInterface(componentType,
									descriptor.getPropertyType())) {
						for (int i = 0; i < size; i++) {
							BeanUtils.setProperty(objecList.get(i),
									descriptor.getName(), objArray[i]);
						}

					} else if (isSimpleType(propertyValue.getClass())) { // 若值也是简单类型则赋值，非简单类型，则不处理
						for (int i = 0; i < size; i++) {
							BeanUtils.setProperty(objecList.get(i),
									descriptor.getName(), objArray[i]);
						}
					} else {
						TypeConverter converter = getTypeConverter(
								descriptor.getPropertyType(), componentType);

						for (int i = 0; i < size; i++) {
							BeanUtils.setProperty(objecList.get(i),
									descriptor.getName(),
									converter.getObject(converter));
						}
					}
				}
				continue;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}
		// objecList的数据放入collection
		for (Object o : objecList) {
			collection.add(o);
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
		Class componentType = arrays.getClass().getComponentType();
		// 如果类型相同，或者值类型继承(实现)属性类型
		if (clazz == componentType || implmentInterface(componentType, clazz)) {
			for (int i = 0; i < arrays.length; i++) {
				collection.add(arrays[i]);
			}
		}
		// 还不行就去找转换器
		TypeConverter currentTypeConverter = getTypeConverter(clazz,
				componentType);
		for (int i = 0; i < arrays.length; i++) {
			collection.add(currentTypeConverter.getObject(arrays[i]));
		}
		return true;
	}

	private ObjectAssembly<?> getObjectAssembly(Class<?> type) {
		for (ObjectAssembly<?> assembly : assemblies) {
			if (assembly.isMatch(type)) {
				return assembly;
			}
		}
		return null;
	}

	private Object getSimpleTypeValue(String varName, Class<?> clazz,
			Context context) {
		Object value = getPerpertyValue(varName, context);
		if(value==null){
			return null;
		}
		if(String.class == value.getClass()){
			return BasicTypeConverter.getValue((String)value, String.class.getName());
		}
		return value;
	}

	public Collection<Object> getObjectCollection(String varName,
			String collectionName, String className, ClassLoader loader,
			Context context) {
		if (StringUtil.isBlank(className)) {
			throw new RuntimeException("传入的className不可为空");
		}
		Class clazz = getClazz(className, loader);
		Collection<Object> collection = (Collection<Object>) getObjectInstance(clazz);
		buildCollection(null, varName, collection, clazz, context);
		return collection;
	}

	public Object getObjectArray(String varName, String className,
			ClassLoader loader, Context context) {
		if (StringUtil.isBlank(className)) {
			throw new RuntimeException("传入的className不可为空");
		}
		Class clazz = getClazz(className, loader);
		return buildArrayObjectWithObject(null, varName, clazz, context);
	}
	
	private Object buildArrayObjectWithObject(String preName,String varName,
			Class<?> objectClass, Context context) {
		Collection<Object> collection = new ArrayList<Object>();
		buildCollection(preName,varName, collection, objectClass, context );
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

	public void addTypeConverter(TypeConverter<?, ?> typeConverter) {
		Class<?> clazz = typeConverter.getDestinationType();
		if (typeConverterMap.containsKey(clazz)) {
			typeConverterMap.get(clazz).add(typeConverter);
		} else {
			List<TypeConverter<?, ?>> list = new ArrayList<TypeConverter<?, ?>>();
			list.add(typeConverter);
			typeConverterMap.put(clazz, list);
		}
	}

	public void removeTypeConverter(TypeConverter<?, ?> typeConverter) {
		Class<?> clazz = typeConverter.getDestinationType();
		if (typeConverterMap.containsKey(clazz)) {
			List<TypeConverter<?, ?>> list = typeConverterMap.get(clazz);
			list.remove(typeConverter);
			if (list.size() == 0) {
				typeConverterMap.remove(typeConverter);
			}
		}
	}

	public void addTypeCreator(TypeCreator<?> typeCreator) {
		typeCreatorList.add(typeCreator);

	}

	public void removeTypeCreator(TypeCreator<?> typeCreator) {
		typeCreatorList.remove(typeCreator);

	}

	/**
	 * 根据clazz获取对象 先从creator中获取，若找不到，则去springbean中获取
	 * 
	 * @param clazz
	 * @return
	 */
	private Object getObjectInstance(Class<?> clazz) {
		Object o = getIntanceByCreator(clazz);
		if (o != null) {
			return o;
		}
		return getInstanceBySpringBean(clazz);
	}

	private Object getInstanceBySpringBean(String bean) {
		if (bean == null || "".equals(bean)) {
			return null;
		}
		try {
			return BeanContainerFactory.getBeanContainer(
					this.getClass().getClassLoader()).getBean(bean);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.WARN, e.getMessage());
			return null;
		}

	}

	private Object getInstanceBySpringBean(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}
		try {
			return BeanContainerFactory.getBeanContainer(
					this.getClass().getClassLoader()).getBean(clazz);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.WARN, e.getMessage());
			try {
				return clazz.newInstance();
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}
	}

	/**
	 * 根据clazz从creators中获取其实例
	 * 
	 * @param clazz
	 * @return 若找到则返回对象实例，否则返回null
	 */
	private Object getIntanceByCreator(Class<?> clazz) {
		for (TypeCreator<?> creator : typeCreatorList) {
			if (clazz.equals(creator.getType())) { // clazz是否继承自getClass
				return creator.getInstance();
			}
		}
		return null;
	}

	/**
	 * 判断clazz是否实现了interfaceClazz
	 * 
	 * @param clazz
	 * @param interfaceClazz
	 * @return
	 */
	private boolean implmentInterface(Class<?> clazz, Class<?> interfaceClazz) {
		return interfaceClazz.isAssignableFrom(clazz);
	}

	private Class<?> getClazz(String className, ClassLoader loader) {
		try {
			return loader.loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	boolean isSimpleType(Class<?> clazz) {
		if (clazz.equals(int.class) || clazz.equals(char.class)
				|| clazz.equals(byte.class) || clazz.equals(boolean.class)
				|| clazz.equals(short.class) || clazz.equals(long.class)
				|| clazz.equals(double.class) || clazz.equals(float.class)) {
			return true;
		}
		if (clazz.equals(Integer.class) || clazz.equals(Character.class)
				|| clazz.equals(Byte.class) || clazz.equals(Boolean.class)
				|| clazz.equals(Short.class) || clazz.equals(Long.class)
				|| clazz.equals(Double.class) || clazz.equals(Float.class)
				|| clazz.equals(String.class)) {
			return true;
		}
		return false;
	}

	private List<TypeConverter<?, ?>> getTypeConverterList(Class<?> destType) {
		return typeConverterMap.get(destType);
	}

	private TypeConverter<?, ?> getTypeConverter(Class<?> destType,
			Class<?> sourceType) {
		List<TypeConverter<?, ?>> typeConverterList = getTypeConverterList(destType);
		for (TypeConverter typeConverter : typeConverterList) {
			if (typeConverter.getSourceType() == sourceType) {
				return typeConverter;
			}
		}
		throw new RuntimeException("未找到合适的转换器类型,源类型:" + sourceType + "目标类型:"
				+ destType);
	}

	private boolean isNull(String str) {
		if (StringUtil.isBlank(str)) {
			return true;
		}
		return false;
	}

	private String getObjName(Object object) {
		String className = object.getClass().getSimpleName();
		if (className.length() == 1)
			return className.toLowerCase();
		return className.substring(0, 1).toLowerCase() + className.substring(1);
	}

	private Object getPerpertyValue(String preName, String objName,
			String propertyName, Context context) {
		String reallyName = getReallyPropertyName(preName, objName,
				propertyName);
		return getPerpertyValue(reallyName, context);
	}

	public String getReallyPropertyName(String preName, String objName,
			String propertyName) {
		if (preName == null || "".equals(preName)) {
			return String.format("%s.%s", objName, propertyName);
		}
		return String.format("%s.%s", preName, propertyName);
	}

	private Object getPerpertyValue(String reallyName, Context context) {
		Object value = context.get(reallyName);
		if (value == null) {
			value = context.get(reallyName.replace(".", "_"));
		}
		if (value == null) {
			int index = reallyName.indexOf('.') + 1;
			if (index != 0) {
				value = getPerpertyValue(reallyName.substring(index), context);
			}
		}
		return value;
	}

	private Field getDeclaredFieldWithParent(Class<?> clazz, String name) {
		try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			if (clazz.getSuperclass() != null) {
				return getDeclaredFieldWithParent(clazz.getSuperclass(), name);
			}

		}
		return null;

	}
}
