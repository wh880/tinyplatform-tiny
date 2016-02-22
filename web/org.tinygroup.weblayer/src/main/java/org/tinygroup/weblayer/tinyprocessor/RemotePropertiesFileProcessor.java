/**
 * 
 */
package org.tinygroup.weblayer.tinyprocessor;

import java.util.Map;

import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.remoteconfig.manager.ConfigItemReader;
import org.tinygroup.vfs.FileObject;

/**
 * @author Administrator
 *
 */
public class RemotePropertiesFileProcessor extends AbstractFileProcessor {

	ConfigItemReader configItemReader;
	
	public void setConfigItemReader(ConfigItemReader configItemReader) {
		this.configItemReader = configItemReader;
	}
	
	public void process() {
		Map<String ,String> configMap = configItemReader.getALL();
		ConfigurationUtil.getConfigurationManager().getConfiguration().putAll(configMap);
	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return false;
	}

}
