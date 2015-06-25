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
package org.tinygroup.context2object.test.testcase;

import org.tinygroup.context2object.config.BasicTypeConverter;

import java.util.List;

public class TestBasicTypeConverter extends BastTestCast {
	private BasicTypeConverter basicConverter = new BasicTypeConverter();
	private String[] array = new String[] { "1", "", null, "15" };
	private Object[] oarray = new Object[] { "1", "", null, "15" };

	public void testInstanceof() {
		assertTrue( array instanceof String[] );
		assertFalse( oarray instanceof String[] );
	}
	public void testCharArray() {
		char[] result = basicConverter.convertToChar(array);
		assertEquals(result[0], '1');
		assertEquals(result[1], 0);
		assertEquals(result[2], 0);
		assertEquals(result[3], '1');
	}

	public void testCharacterArray() {
		Character[] result = basicConverter.convertToCharacter(array);
		assertEquals(result[0], Character.valueOf('1'));
		assertEquals(result[1], null);
		assertEquals(result[2], null);
		assertEquals(result[3], Character.valueOf('1'));
	}

	public void testCharacterCollection() {
		List<Character> result = (List<Character>) basicConverter
				.convertBasicTypeCollection(array, "java.util.List",
						"Character",
						TestBasicTypeConverter.class.getClassLoader());
		assertEquals(result.get(0), Character.valueOf('1'));
		assertEquals(result.get(1), null);
		assertEquals(result.get(2), null);
		assertEquals(result.get(3), Character.valueOf('1'));
	}

	public void testIntArray() {
		int[] result = basicConverter.convertToInt(array);
		assertEquals(result[0], 1);
		assertEquals(result[1], 0);
		assertEquals(result[2], 0);
		assertEquals(result[3], 15);
	}

	public void testIntegerArray() {
		Integer[] result = basicConverter.convertToInteger(array);
		assertEquals(result[0], Integer.valueOf("1"));
		assertEquals(result[1], null);
		assertEquals(result[2], null);
		assertEquals(result[3], Integer.valueOf("15"));
	}

	public void testIntegerCollection() {
		List<Integer> result = (List<Integer>) basicConverter
				.convertBasicTypeCollection(array, "java.util.List", "Integer",
						TestBasicTypeConverter.class.getClassLoader());
		assertEquals(result.get(0), Integer.valueOf("1"));
		assertEquals(result.get(1), null);
		assertEquals(result.get(2), null);
		assertEquals(result.get(3), Integer.valueOf("15"));
	}

	public void testLongArray() {
		long[] result = basicConverter.convertToLong(array);
		assertEquals(result[0], 1);
		assertEquals(result[1], 0);
		assertEquals(result[2], 0);
		assertEquals(result[3], 15);
	}

	public void testLongObjectArray() {
		Long[] result = basicConverter.convertToLongObject(array);
		assertEquals(result[0], Long.valueOf("1"));
		assertEquals(result[1], null);
		assertEquals(result[2], null);
		assertEquals(result[3], Long.valueOf("15"));
	}

	public void testLongCollection() {
		List<Long> result = (List<Long>) basicConverter
				.convertBasicTypeCollection(array, "java.util.List", "Long",
						TestBasicTypeConverter.class.getClassLoader());
		assertEquals(result.get(0), Long.valueOf("1"));
		assertEquals(result.get(1), null);
		assertEquals(result.get(2), null);
		assertEquals(result.get(3), Long.valueOf("15"));
	}

	public void testShortArray() {
		short[] result = basicConverter.convertToShort(array);
		assertEquals(result[0], 1);
		assertEquals(result[1], 0);
		assertEquals(result[2], 0);
		assertEquals(result[3], 15);
	}

	public void testShortObjectArray() {
		Short[] result = basicConverter.convertToShortObject(array);
		assertEquals(result[0], Short.valueOf("1"));
		assertEquals(result[1], null);
		assertEquals(result[2], null);
		assertEquals(result[3], Short.valueOf("15"));
	}

	public void testShortCollection() {
		List<Long> result = (List<Long>) basicConverter
				.convertBasicTypeCollection(array, "java.util.List", "Short",
						TestBasicTypeConverter.class.getClassLoader());
		assertEquals(result.get(0), Short.valueOf("1"));
		assertEquals(result.get(1), null);
		assertEquals(result.get(2), null);
		assertEquals(result.get(3), Short.valueOf("15"));
	}

	public void testDoubleArray() {
		double[] result = basicConverter.convertToDouble(array);
		assertEquals(result[0], Double.parseDouble("1"));
		assertEquals(result[1], Double.parseDouble("0"));
		assertEquals(result[2], Double.parseDouble("0"));
		assertEquals(result[3], Double.parseDouble("15"));
	}

	public void testDoubleObjectArray() {
		Double[] result = basicConverter.convertToDoubleObject(array);
		assertEquals(result[0], Double.valueOf("1"));
		assertEquals(result[1], null);
		assertEquals(result[2], null);
		assertEquals(result[3], Double.valueOf("15"));
	}

	public void testDoubleCollection() {
		List<Double> result = (List<Double>) basicConverter
				.convertBasicTypeCollection(array, "java.util.List", "Double",
						TestBasicTypeConverter.class.getClassLoader());
		assertEquals(result.get(0), Double.valueOf("1"));
		assertEquals(result.get(1), null);
		assertEquals(result.get(2), null);
		assertEquals(result.get(3), Double.valueOf("15"));
	}

	public void testFloatArray() {
		float[] result = basicConverter.convertToFloat(array);
		assertEquals(result[0], Float.parseFloat("1"));
		assertEquals(result[1], Float.parseFloat("0"));
		assertEquals(result[2], Float.parseFloat("0"));
		assertEquals(result[3], Float.parseFloat("15"));
	}

	public void testFloatObjectArray() {
		Float[] result = basicConverter.convertToFloatObject(array);
		assertEquals(result[0], Float.valueOf("1"));
		assertEquals(result[1], null);
		assertEquals(result[2], null);
		assertEquals(result[3], Float.valueOf("15"));
	}

	public void testFloatCollection() {
		List<Float> result = (List<Float>) basicConverter
				.convertBasicTypeCollection(array, "java.util.List", "Float",
						TestBasicTypeConverter.class.getClassLoader());
		assertEquals(result.get(0), Float.valueOf("1"));
		assertEquals(result.get(1), null);
		assertEquals(result.get(2), null);
		assertEquals(result.get(3), Float.valueOf("15"));
	}
}
