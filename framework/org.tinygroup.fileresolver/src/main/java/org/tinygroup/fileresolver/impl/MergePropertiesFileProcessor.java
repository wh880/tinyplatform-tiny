/**
 * 
 */
package org.tinygroup.fileresolver.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.tinygroup.config.util.ConfigurationUtil;
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
			value = ConfigurationUtil.replace(value, proMap);
			tempMap.put(key, value);
		}
		ConfigurationUtil.getConfigurationManager().getConfiguration().clear();
		ConfigurationUtil.getConfigurationManager().getConfiguration().putAll(tempMap);
		ConfigurationUtil.getConfigurationManager().replace();
		
	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return false;
	}

	

	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}
	
}
