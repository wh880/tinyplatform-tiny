package org.tinygroup.context2object.config;

import java.lang.reflect.Array;
import java.util.Collection;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context2object.util.Context2ObjectUtil;

public class BasicTypeConverter {
	public <T> T[] convertBasicTypeArray(String[] stringArray,String className) {
		if("Character".equals(className)||"java.lang.Character".equals(className)){
			return (T[]) convertToCharacter(stringArray);
		}else if("Integer".equals(className)||"java.lang.Integer".equals(className)){
			return (T[]) convertToInteger(stringArray);
		}else if("Long".equals(className)||"java.lang.Long".equals(className)){
			return (T[]) convertToLongObject(stringArray);
		}else if("Double".equals(className)||"java.lang.Double".equals(className)){
			return (T[]) convertToDoubleObject(stringArray);
		}else if("Float".equals(className)||"java.lang.Float".equals(className)){
			return (T[]) convertToFloatObject(stringArray);
		}else if("Short".equals(className)||"java.lang.Short".equals(className)){
			return (T[]) convertToShortObject(stringArray);
		}else if("String".equals(className)||"java.lang.String".equals(className)){
			return (T[]) stringArray;
		}else {
			throw new RuntimeException(className+"不是基本类型的封装类型");
		}  
	}
	
	public Object convertBasicTypeCollection(String[] stringArray,String collectionClass,String className,ClassLoader loader) {
		Collection<Object> collection = Context2ObjectUtil.getCollectionInstance(collectionClass, loader);
		Object[] tArray = convertBasicTypeArray(stringArray, className);
		for(Object t:tArray){
			collection.add(t);
		}
		return  collection;
	}
	
	public char[] convertToChar(String[] stringArray) {
		char[] array = (char[]) Array.newInstance(char.class,
				stringArray.length);
		for (int i = 0; i < array.length; i++) {
			if (StringUtil.isBlank(stringArray[i])) {
				array[i] = 0;
				continue;
			}
			array[i] = stringArray[i].charAt(0);
		}
		return array;
	}
	
	public Character[] convertToCharacter(String[] stringArray) {
		Character[] array =  (Character[]) Array.newInstance(Character.class,
				stringArray.length);
		for (int i = 0; i < array.length; i++) {
			if (StringUtil.isBlank(stringArray[i])) {
				array[i] = null;
				continue;
			}
			array[i] = Character.valueOf(stringArray[i].charAt(0));
		}
		return array;
	}
	
	public int[] convertToInt(String[] stringArray) {
		int[] array = (int[]) Array.newInstance(int.class,
				stringArray.length);
		for (int i = 0; i < array.length; i++) {
			if (StringUtil.isBlank(stringArray[i])) {
				array[i] = 0;
				continue;
			}
			array[i] = Integer.parseInt(stringArray[i]);
			
		}
		return array;
	}
	public Integer[] convertToInteger(String[] stringArray) {
		Integer[] array = (Integer[]) Array.newInstance(Integer.class,
				stringArray.length);
		for (int i = 0; i < array.length; i++) {
			if (StringUtil.isBlank(stringArray[i])) {
				array[i] = null;
				continue;
			}
			array[i] = Integer.valueOf(stringArray[i]);
			
		}
		return array;
	}
	
	public long[] convertToLong(String[] stringArray) {
		long[] array = (long[]) Array.newInstance(long.class,
				stringArray.length);
		for (int i = 0; i < array.length; i++) {
			if (StringUtil.isBlank(stringArray[i])) {
				array[i] = 0;
				continue;
			}
			array[i] = Long.parseLong(stringArray[i]);
		}
		return array;
	}
	public Long[] convertToLongObject(String[] stringArray) {
		Long[] array = (Long[]) Array.newInstance(Long.class,
				stringArray.length);
		for (int i = 0; i < array.length; i++) {
			if (StringUtil.isBlank(stringArray[i])) {
				array[i] = null;
				continue;
			}
			array[i] = Long.valueOf(stringArray[i]);
		}
		return array;
	}
	
	public double[] convertToDouble(String[] stringArray) {
		double[] array = (double[]) Array.newInstance(double.class,
				stringArray.length);
		for (int i = 0; i < array.length; i++) {
			if (StringUtil.isBlank(stringArray[i])) {
				array[i] = 0;
				continue;
			}
			array[i] = Double.parseDouble(stringArray[i]);
		}
		return array;
	}
	public Double[] convertToDoubleObject(String[] stringArray) {
		Double[] array = (Double[]) Array.newInstance(Double.class,
				stringArray.length);
		for (int i = 0; i < array.length; i++) {
			if (StringUtil.isBlank(stringArray[i])) {
				array[i] = null;
				continue;
			}
			array[i] = Double.valueOf(stringArray[i]);
		}
		return array;
	}

	public float[] convertToFloat(String[] stringArray) {
		float[] array = (float[]) Array.newInstance(float.class,
				stringArray.length);
		for (int i = 0; i < array.length; i++) {
			if (StringUtil.isBlank(stringArray[i])) {
				array[i] = 0;
				continue;
			}
			array[i] = Float.parseFloat(stringArray[i]);
		}
		return array;
	}
	public Float[] convertToFloatObject(String[] stringArray) {
		Float[] array = (Float[]) Array.newInstance(Float.class,
				stringArray.length);
		for (int i = 0; i < array.length; i++) {
			if (StringUtil.isBlank(stringArray[i])) {
				array[i] = null;
				continue;
			}
			array[i] = Float.valueOf(stringArray[i]);
		}
		return array;
	}
	
	public short[] convertToShort(String[] stringArray) {
		short[] array = (short[]) Array.newInstance(short.class,
				stringArray.length);
		for (int i = 0; i < array.length; i++) {
			if (StringUtil.isBlank(stringArray[i])) {
				array[i] = 0;
				continue;
			}
			array[i] = Short.parseShort(stringArray[i]);
		}
		return array;
	}
	public Short[] convertToShortObject(String[] stringArray) {
		Short[] array = (Short[]) Array.newInstance(Short.class,
				stringArray.length);
		for (int i = 0; i < array.length; i++) {
			if (StringUtil.isBlank(stringArray[i])) {
				array[i] = null;
				continue;
			}
			array[i] = Short.valueOf(stringArray[i]);
		}
		return array;
	}


}
