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
package org.tinygroup.dict;

import junit.framework.TestCase;

import org.tinygroup.cache.jcs.JcsCache;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.dict.exception.DictRuntimeException;
import org.tinygroup.dict.exception.errorcode.DictExceptionErrorCode;
import org.tinygroup.dict.impl.DictManagerImpl;

public class DictManagerExceptionTest extends TestCase {

	@SuppressWarnings("unused")
	public void testNotFoundLoader() {
		DictManager dictManager = new DictManagerImpl();
		JcsCache cache = new JcsCache();
		dictManager.setCache(cache);
		cache.init("DC");
		DictLoader dictLoader = new SimpleDictLoader();
		dictLoader.setGroupName("simple");
		DictFilter dictFilter = new SimpleDictFilter();
		dictLoader.setDictFilter(dictFilter);
		dictLoader.setLanguage("CN");
		dictManager.addDictLoader(dictLoader);

		Context context = new ContextImpl();
		context.put("LANG", "EN");
		dictManager.load();
		try {
			Dict dict = dictManager.getDict("Gender", context);
		} catch (DictRuntimeException e) {
			assertEquals(DictExceptionErrorCode.DICT_LOADER_NOT_FOUND, e.getErrorCode().toString());
		}
	}

	@SuppressWarnings("unused")
	public void testNotFoundDicTypeName() {
		DictManager dictManager = new DictManagerImpl();
		JcsCache cache = new JcsCache();
		dictManager.setCache(cache);
		cache.init("DC");
		DictLoader dictLoader = new SimpleNonDictLoader();
		dictLoader.setGroupName("simple");
		DictFilter dictFilter = new SimpleDictFilter();
		dictLoader.setDictFilter(dictFilter);
		dictManager.addDictLoader(dictLoader);

		Context context = new ContextImpl();
		context.put("LANG", "CN");
		dictManager.load();
		try {
			Dict dict = dictManager.getDict("Gender", context);
		} catch (DictRuntimeException e) {
			assertEquals(DictExceptionErrorCode.DICT_TYPE_NAME_NOT_FOUND, e.getErrorCode().toString());
		}
	}
}
