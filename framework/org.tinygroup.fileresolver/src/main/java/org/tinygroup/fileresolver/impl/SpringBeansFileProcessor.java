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
package org.tinygroup.fileresolver.impl;

import org.tinygroup.fileresolver.FileResolver;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.vfs.FileObject;

public class SpringBeansFileProcessor extends AbstractFileProcessor {

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(".springbeans.xml")
				|| fileObject.getFileName().endsWith(".beans.xml");
	}

	public void process(ClassLoader loader) {
		if(loader==this.getClass().getClassLoader()){
			SpringUtil.regSpringConfigXml(fileObjects);
			SpringUtil.refresh();
		}else{
			SpringUtil.getSubApplicationContext(fileObjects, loader);
		}
		
	}


	public void setFileResolver(FileResolver fileResolver) {

	}

	
	public boolean supportRefresh() {
		return false;
	}

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}
	

}
