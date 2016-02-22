/**
 * 
 */
package org.tinygroup.weblayer.tinyprocessor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.vfs.FileObject;


/**
 * @author Administrator
 *
 */
public class MergePropertiesFileProcessor extends AbstractFileProcessor {

	public void process() {
		Map<String ,String> proMap = ConfigurationUtil.getConfigurationManager().getConfiguration();
		Map<String ,String> tempMap = new HashMap<String, String>();
		for (Iterator<String> iterator = proMap.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			String value = proMap.get(key);
			value = replace(value, proMap);
			tempMap.put(key, value);
		}
		ConfigurationUtil.getConfigurationManager().getConfiguration().clear();
		ConfigurationUtil.getConfigurationManager().getConfiguration().putAll(tempMap);
	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return false;
	}

	/**
	 * 变量替换
	 * 
	 * @param value
	 * @param proMap
	 * @return
	 */
	private String replace(String value ,Map<String ,String> proMap) {
		Pattern pattern = Pattern.compile("(\\{[^\\}]*\\})");
		Matcher matcher = pattern.matcher(value);
		int curpos = 0;
		StringBuilder buf = new StringBuilder();
		while (matcher.find()) {
			buf.append(value.substring(curpos, matcher.start()));
			curpos = matcher.end();
			String var = value.substring(matcher.start(), curpos);
			System.out.println(var);
			buf.append(proMap.get(StringUtils.substring(var, 1, var.length()-1)));
			continue;
		}
		buf.append(value.substring(curpos));
		return buf.toString();
	}

}
