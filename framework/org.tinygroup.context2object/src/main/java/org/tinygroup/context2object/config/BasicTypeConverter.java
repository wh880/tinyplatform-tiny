/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.context2object.config;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context2object.util.Context2ObjectUtil;

import java.lang.reflect.Array;
import java.util.Collection;

public class BasicTypeConverter {
	public static Object convertBasicTypeArray(String[] stringArray,String className) {
		if("Character".equals(className)||"java.lang.Character".equals(className)){
			return convertToCharacter(stringArray);
		}else if("char".equals(className)){
			return convertToChar(stringArray);
		}else if("Integer".equals(className)||"java.lang.Integer".equals(className)){
			return convertToInteger(stringArray);
		}else if("int".equals(className)){
			return convertToInt(stringArray);
		}else if("Long".equals(className)||"java.lang.Long".equals(className)){
			return convertToLongObject(stringArray);
		}else if("long".equals(className)){
			return convertToLong(stringArray);
		}else if("Double".equals(className)||"java.lang.Double".equals(className)){
			return convertToDoubleObject(stringArray);
		}else if("double".equals(className)){
			return convertToDouble(stringArray);
		}else if("Float".equals(className)||"java.lang.Float".equals(className)){
			return convertToFloatObject(stringArray);
		}else if("float".equals(className)){
			return convertToFloat(stringArray);
		}else if("Short".equals(className)||"java.lang.Short".equals(className)){
			return convertToShortObject(stringArray);
		}else if("short".equals(className)){
			return convertToShort(stringArray);
		}else if("String".equals(className)||"java.lang.String".equals(className)){
			return stringArray;
		}else if("Byte".equals(className)||"java.lang.Byte".equals(className)){
			return convertToByteObject(stringArray);
		}else if("byte".equals(className)){
			return convertToByte(stringArray);
		}else{
			return null;
		}  
	}
	
	public static Object convertBasicTypeCollection(String[] stringArray,String collectionClass,String className,ClassLoader loader) {
		Collection<Object> collection = Context2ObjectUtil.getCollectionInstance(collectionClass, loader);
		Object value = convertBasicTypeArray(stringArray, className);
		if(value == null){
			return null;
		}
		Object[] tArray = (Object[]) value;
		for(Object t:tArray){
			collection.add(t);
		}
		return  collection;
	}
	
	public static byte[] convertToByte(String[] stringArray) {
		byte[] array = (byte[]) Array.newInstance(byte.class,
				stringArray.length);
		for (int i = 0; i < array.length; i++) {
			if (StringUtil.isBlank(stringArray[i])) {
				array[i] = 0;
				continue;
			}
			array[i] = Byte.parseByte(stringArray[i]);
		}
		return array;
	}
	public static Byte[] convertToByteObject(String[] stringArray) {
		Byte[] array = (Byte[]) Array.newInstance(Byte.class,
				stringArray.length);
		for (int i = 0; i < array.length; i++) {
			if (StringUtil.isBlank(stringArray[i])) {
				array[i] = null;
				continue;
			}
			array[i] = Byte.valueOf(stringArray[i]);
		}
		return array;
	}
	
	public static char[] convertToChar(String[] stringArray) {
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
	public static Character[] convertToCharacter(String[] stringArray) {
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
	
	
	
	public static int[] convertToInt(String[] stringArray) {
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
	public static Integer[] convertToInteger(String[] stringArray) {
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
	
	public static long[] convertToLong(String[] stringArray) {
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
	public static Long[] convertToLongObject(String[] stringArray) {
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
	
	public static double[] convertToDouble(String[] stringArray) {
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
	public static Double[] convertToDoubleObject(String[] stringArray) {
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

	public static float[] convertToFloat(String[] stringArray) {
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
	public static Float[] convertToFloatObject(String[] stringArray) {
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
	
	public static short[] convertToShort(String[] stringArray) {
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
	public static Short[] convertToShortObject(String[] stringArray) {
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

	public static Object getValue(String obj,String type){
		if("java.lang.String".equals(type)){
			return obj;
		}else if ("java.lang.Integer".equals(type)
				|| "Integer".equals(type)) {
			return Integer.valueOf(obj);
		} else if ("int".equals(type)) {
			return Integer.parseInt(obj);
		} else if ("java.lang.Byte".equals(type)
				|| "Byte".equals(type)) {
			return Byte.valueOf(obj);
		} else if ("byte".equals(type)) {
			return Byte.parseByte(obj);
		} else if ("java.lang.Boolean".equals(type)
				|| "Boolean".equals(type)) {
			return Boolean.valueOf(obj);
		} else if ("boolean".equals(type)) {
			return Boolean.parseBoolean(obj);
		} else if ("java.lang.Character".equals(type)
				|| "Character".equals(type)) {
			return Character.valueOf((obj).toCharArray()[0]);
		} else if ("char".equals(type)) {
			return (obj).toCharArray()[0];
		} else if ("java.lang.Double".equals(type)
				|| "Double".equals(type)) {
			return Double.valueOf(obj);
		} else if ("double".equals(type)) {
			return Double.parseDouble(obj);
		} else if ("java.lang.Short".equals(type)
				|| "Short".equals(type)) {
			return Short.valueOf(obj);
		} else if ("short".equals(type)) {
			return Short.parseShort(obj);
		} else if ("java.lang.Long".equals(type)
				|| "Long".equals(type)) {
			return Long.valueOf(obj);
		} else if ("long".equals(type)) {
			return Long.parseLong(obj);
		} else if ("java.lang.Float".equals(type)
				|| "Float".equals(type)) {
			return Float.valueOf(obj);
		} else if ("float".equals(type)) {
			return Float.parseFloat(obj);
		} else {
			return null;
		}
	}

}
