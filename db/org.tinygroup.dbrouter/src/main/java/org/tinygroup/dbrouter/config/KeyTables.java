/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.dbrouter.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XStreamAlias("key-tables")
public class KeyTables {

	@XStreamImplicit
	private List<KeyTable> keyTableList;
	
	//二级map，第一级key是language，第二级key是className
	private Map<String,Map<String,KeyTable>> keyMap;

	public List<KeyTable> getKeyTableList() {
		if(keyTableList==null){
			keyTableList = new ArrayList<KeyTable>();	
		}
		return keyTableList;
	}

	public void setKeyTableList(List<KeyTable> keyTableList) {
		this.keyTableList = keyTableList;
	}
	
	public void init(){
		keyMap = new HashMap<String,Map<String,KeyTable>>();
		for(KeyTable table:getKeyTableList()){
			Map<String,KeyTable> classMap = keyMap.get(table.getLanguage());
			if(classMap==null){
				classMap = new HashMap<String,KeyTable>();
				keyMap.put(table.getLanguage(), classMap);
			}
			classMap.put(table.getClassName(), table);
		}
	}
	
	public KeyTable getKeyTable(String language,String className){
		if(keyMap.containsKey(language)){
		   return keyMap.get(language).get(className);
		}
		return null;
	}
	
}
