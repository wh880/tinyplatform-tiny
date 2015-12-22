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
package org.tinygroup.database.fileresolver;

import com.thoughtworks.xstream.XStream;
import org.tinygroup.database.ProcessorManager;
import org.tinygroup.database.config.processor.Processors;
import org.tinygroup.database.util.DataBaseUtil;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

public class ProcessorFileResolver extends AbstractFileProcessor {
	private static final String PROCESSOR_EXTFILENAME = ".database.processor.xml";
	ProcessorManager processorManager;
	
	
	public ProcessorManager getProcessorManager() {
		return processorManager;
	}

	public void setProcessorManager(ProcessorManager processorManager) {
		this.processorManager = processorManager;
	}

	public void process() {
		LOGGER.logMessage(LogLevel.INFO, "开始读取database.processor文件");
		XStream stream = XStreamFactory
				.getXStream(DataBaseUtil.PROCESSOR_XSTREAM);
		for (FileObject fileObject : deleteList) {
			LOGGER.logMessage(LogLevel.INFO, "开始移除database.processor文件{0}",
					fileObject.getAbsolutePath());
			Processors processors = (Processors)caches.get(fileObject.getAbsolutePath());
			if(processors!=null){
				processorManager.removeProcessors(processors);
				caches.remove(fileObject.getAbsolutePath());
			}
			LOGGER.logMessage(LogLevel.INFO, "移除database.processor文件{0}完毕",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			LOGGER.logMessage(LogLevel.INFO, "开始读取database.processor文件{0}",
					fileObject.getAbsolutePath());
			Processors oldProcessors = (Processors)caches.get(fileObject.getAbsolutePath());
			if(oldProcessors!=null){
				processorManager.removeProcessors(oldProcessors);
			}	
			Processors processors = (Processors) stream.fromXML(fileObject
					.getInputStream());
			processorManager.addProcessors(processors);
			caches.put(fileObject.getAbsolutePath(), processors);
			LOGGER.logMessage(LogLevel.INFO, "读取database.processor文件{0}完毕",
					fileObject.getAbsolutePath());
		}
		LOGGER.logMessage(LogLevel.INFO, "database.processor文件读取完毕");

	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(PROCESSOR_EXTFILENAME);
	}

}
