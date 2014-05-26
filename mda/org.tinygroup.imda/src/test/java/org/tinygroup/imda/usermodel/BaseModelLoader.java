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
package org.tinygroup.imda.usermodel;

import org.tinygroup.imda.ModelLoader;
import org.tinygroup.imda.test.util.ModelTestUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.vfs.FileObject;

public class BaseModelLoader implements ModelLoader{
	private static Logger logger = LoggerFactory.getLogger(BaseModelLoader.class);
	public static int LoaderValue = 5;
	public String getExtFileName() {
		
		return ".caseloader.xml";
	}

	public Object loadModel(FileObject fileObject) {
		logger.logMessage(LogLevel.INFO, "--------------------------");
		logger.logMessage(LogLevel.INFO, fileObject.getFileName());
		logger.logMessage(LogLevel.INFO, "--------------------------");
		ModelTestUtil.setValue(LoaderValue);
		return null;
	}

}
