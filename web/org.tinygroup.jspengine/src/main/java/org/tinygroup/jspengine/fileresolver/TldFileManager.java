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
package org.tinygroup.jspengine.fileresolver;

import org.tinygroup.vfs.FileObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 功能说明: tld文件管理对象

 * 开发人员: renhui <br>
 * 开发时间: 2013-11-12 <br>
 * <br>
 */
public class TldFileManager {
      
	 private static TldFileManager manager=new TldFileManager();
	 
	 private  List<FileObject> fileObjects;
	  
	 private  TldFileManager(){
		 fileObjects=new ArrayList<FileObject>();
	 }
	 
	 public static TldFileManager getInstance(){
		 return manager;
	 }

	public List<FileObject> getTldFiles() {
		return fileObjects;
	}

	public void addTldFile(FileObject fileObject){
		fileObjects.add(fileObject);
	}
	 
	public void removeTldFile(FileObject fileObject){
		fileObjects.remove(fileObject);
	}
}
