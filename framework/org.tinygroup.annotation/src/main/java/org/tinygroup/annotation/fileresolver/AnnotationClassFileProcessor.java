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
package org.tinygroup.annotation.fileresolver;

import org.tinygroup.annotation.AnnotationExcuteManager;
import org.tinygroup.fileresolver.ProcessorCallBack;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.fileresolver.impl.MultiThreadFileProcessor;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.vfs.FileObject;

/**
 * 类文件搜索器
 * 
 * @author luoguo
 * 
 */
public class AnnotationClassFileProcessor extends AbstractFileProcessor {

	private static final String CLASS_EXT_FILENAME = ".class";

	public boolean isMatch(FileObject fileObject) {
		boolean isMatch = false;
		if (fileObject.getFileName().endsWith(CLASS_EXT_FILENAME)) {
			isMatch = true;
		}
		return isMatch;

	}

	public void process() {
		final AnnotationExcuteManager manager = SpringUtil
				.getBean(AnnotationExcuteManager.ANNOTATION_MANAGER_BEAN_NAME);
		MultiThreadFileProcessor.mutiProcessor(getFileResolver()
				.getFileProcessorThreadNum(), "annotation-muti", fileObjects,
				new ProcessorCallBack() {
					public void callBack(FileObject fileObject) {
						manager.processClassFileObject(fileObject);
					}
				});
	}

}