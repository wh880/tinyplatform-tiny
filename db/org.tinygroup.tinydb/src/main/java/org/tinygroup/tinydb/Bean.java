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
package org.tinygroup.tinydb;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean，用于描述各种对象
 */
public class Bean extends HashMap<String, Object> {
    private static final long serialVersionUID = 7766936015089695L;
    private String type;// 对象所属的类，命名规则与类相同,
    private Map<String, Boolean> updateMarkMap=new HashMap<String, Boolean>();

    public Bean(Bean bean) {
        putAll(bean);
        type = bean.getType();
    }

    public Bean setProperty(String propertyName, Object property) {
        put(propertyName, property);
        return this;
    }

    public Bean set(String propertyName, Object property) {
        put(propertyName, property);
        return this;
    }

    public Bean setProperty(Bean bean) {
        putAll(bean);
        return this;
    }
    
	public Object put(String key, Object value) {
		Object object= super.put(key, value);
		updateMarkMap.put(key, true);
		return object;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> map) {
		super.putAll(map);
		for (String key : map.keySet()) {
			updateMarkMap.put(key, true);
		}
	}

	@SuppressWarnings("unchecked")
    public <T> T getProperty(String propertyName) {
        return (T) this.get(propertyName);
    }

    /**
     * @param type
     */
    public Bean(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
    public void clearMark(){
    	updateMarkMap.clear();
    }

	public boolean getMark(String propertyName) {
		Boolean mark=updateMarkMap.get(propertyName);
		if(mark==null){
		   mark=false;
		}
		return mark;
	}

}
