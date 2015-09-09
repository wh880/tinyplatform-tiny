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
package org.tinygroup.exception;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Properties;

import org.tinygroup.i18n.I18nMessageFactory;

import junit.framework.TestCase;

public class ExceptionTest extends TestCase {

	private  ExceptionTranslator translator;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		init();
	}




	public void testException(){
		BizExecute biz = new BizExecute();
		try{
			biz.execute();
		}catch(BizRuntimeException e){
			assertEquals(e.getMessage(), "error1");
		}
		try{
			biz.executeWithMsg();
		}catch(BizRuntimeException e){
			assertEquals(e.getMessage(),"hello error");
		}
//		throw new BizRuntimeException("0TE111011027","haha");
	}




	private void init() throws IOException {
		loadI18n("zh", "CN");
		loadI18n("en", "US");
		loadI18n("zh", "TW");
	}
	
	private void loadI18n(String language,String country) throws IOException{
		Locale locale = new Locale(language,country);
		Properties properties = new Properties();
		InputStream inputStream = getClass().getResourceAsStream(
				"/i18n/info_" + locale.getLanguage() + "_"
						+ locale.getCountry() + ".properties");
		BufferedReader bf = new BufferedReader(new   InputStreamReader(inputStream));
		properties.load(bf);
		I18nMessageFactory.addResource(locale, properties);
	}
}
