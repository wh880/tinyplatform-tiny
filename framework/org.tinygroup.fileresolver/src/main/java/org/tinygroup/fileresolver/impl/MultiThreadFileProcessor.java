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
package org.tinygroup.fileresolver.impl;

import org.tinygroup.fileresolver.ProcessorCallBack;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.threadgroup.MultiThreadProcessor;
import org.tinygroup.vfs.FileObject;

import java.util.List;

/**
 * 文件多线程处理器工具类
 * 
 * @author renhui
 * 
 */
public class MultiThreadFileProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(MultiThreadFileProcessor.class);

	private MultiThreadFileProcessor(){
		
	}

	public static void mutiProcessor(int threadNum,String mutiThreadName, List<FileObject> fileObjects,ProcessorCallBack callBack) {

		MultiThreadProcessor processors = new MultiThreadProcessor(mutiThreadName);
		int fileNums = fileObjects.size();
		int numOfEachThread = fileNums % threadNum == 0 ? fileNums/threadNum : fileNums/threadNum + 1;
		int fromIndex = 0;
		for (int i = 0; i < threadNum; i++) {
			int endIndex= fromIndex + numOfEachThread;
			if(endIndex>fileNums){
				endIndex=fileNums;
			}
			List<FileObject> files = fileObjects.subList(fromIndex,
					endIndex);
			fromIndex += numOfEachThread;
			FileProcessorThread processor = new FileProcessorThread(
					String.format("file-processor-thread-%d", i),
					files);
			processor.setCallBack(callBack);
			processors.addProcessor(processor);
		}
		long startTime = System.currentTimeMillis();
		processors.start();
		long endTime = System.currentTimeMillis();
		LOGGER.logMessage(LogLevel.INFO, "线程组:<{}>执行时间：{}", mutiThreadName,endTime
				- startTime);

	}

}
