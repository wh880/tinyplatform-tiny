/**
 * 
 */
package org.tinygroup.remoteconfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import org.junit.Assert;
import org.tinygroup.i18n.I18nMessageFactory;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.zk.client.ZKManager;

/**
 * @author Administrator
 * 
 */
public class ClientTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		init();
		
		try {
			//清理
			ZKManager.delete("", null);
			ZKManager.getAll(new ConfigPath());
			ZKManager.delete("", null);
			
			//单条查询
			Assert.assertNull(ZKManager.get("O2O" ,null));
			//校验是否存在
			Assert.assertFalse(ZKManager.exists("jjj" ,null));
			//增加
			ZKManager.set("a/b/c", "123123123" ,null);
			ZKManager.set("jjj", "9999" ,null);
		} catch (Exception e) {
			ZKManager.delete("O2O", null);
			Assert.fail();
		}
		
		//单条查询
		Assert.assertNotNull(ZKManager.get("jjj" ,null));
		//校验是否存在
		Assert.assertNotNull(ZKManager.exists("jjj",null));
		
		//批量查询
		Assert.assertTrue(ZKManager.getAll(null).size() == 2);
	}
	
	private static void init() throws IOException {
		loadI18n("zh", "CN");
		loadI18n("en", "US");
		loadI18n("zh", "TW");
	}
	
	private static void loadI18n(String language,String country) throws IOException{
		Locale locale = new Locale(language,country);
		Properties properties = new Properties();
		InputStream inputStream = ClientTest.class.getResourceAsStream(
				"/i18n/info_" + locale.getLanguage() + "_"
						+ locale.getCountry() + ".properties");
//		BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
		properties.load(inputStream);
		I18nMessageFactory.addResource(locale, properties);
	}
}
