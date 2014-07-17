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
package org.tinygroup.springutil.fileresolver;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.springutil.SpringBeanContainer;
import org.tinygroup.vfs.FileObject;

public class SpringBeansFileProcessor extends AbstractFileProcessor {

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(".springbeans.xml")
				|| fileObject.getFileName().endsWith(".beans.xml");
	}

	public void process() {
		SpringBeanContainer container = (SpringBeanContainer)BeanContainerFactory.getBeanContainer(this.getClass().getClassLoader());
		if(getFileResolver().getClassLoader()==this.getClass().getClassLoader()){
			container.regSpringConfigXml(fileObjects);
			container.refresh();
		}else{
			container.getSubBeanContainer(fileObjects, getFileResolver().getClassLoader());
		}
		
	}



	
	public boolean supportRefresh() {
		return false;
	}

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}
	

}
