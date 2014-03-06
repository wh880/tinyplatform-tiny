/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.docgen.fileresolver;

import org.tinygroup.docgen.DocumentGeneraterManager;
import org.tinygroup.docgen.DocumentGenerater;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.vfs.FileObject;

/**
 * 模板文件扫描器
 * 
 * @author luoguo
 * 
 */
public class TemplateFileProcessor extends AbstractFileProcessor {
	/**
	 * 扫描文件的后缀名，由bean注入
	 */
	private String fileExtName;
	/**
	 * 文档类型，由bean注入
	 */
	private String documentType;

	public String getFileExtName() {
		return fileExtName;
	}

	public void setFileExtName(String fileExtName) {
		this.fileExtName = fileExtName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String docType) {
		this.documentType = docType;
	}

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(fileExtName);
	}

	public void process() {
		DocumentGenerater generate = SpringUtil
				.getBean(DocumentGenerater.DOCUMENT_GENERATE_BEAN_NAME);// 多实例
		for (FileObject fileObject : fileObjects) {
			logger.logMessage(LogLevel.DEBUG, "正在加载文档模板宏配置文件[{0}]",
					fileObject.getAbsolutePath());
			generate.addMacroFile(fileObject);
			logger.logMessage(LogLevel.DEBUG, "加载文档模板宏配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		DocumentGeneraterManager manager = SpringUtil
				.getBean(DocumentGeneraterManager.MANAGER_BEAN_NAME);
		manager.putDocumentGenerater(documentType, generate);
	}

}
