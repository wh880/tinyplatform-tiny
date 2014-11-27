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
package org.tinygroup.cepcorepc.test.regex;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.cepcorepc.test.synchronous.Service;
import org.tinygroup.cepcorepc.test.synchronous.SynchronousProcessor;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceInfo;

public class RegexProcessor2 implements EventProcessor{
	List<ServiceInfo> list = new ArrayList<ServiceInfo>();
	public void process(Event event) {
	
		System.out.println("RegexProcessor processor2");
	}

	public void setCepCore(CEPCore cepCore) {
		// TODO Auto-generated method stub
		
	}

	public List<ServiceInfo> getServiceInfos() {
		return list;
	}

	public String getId() {
		return SynchronousProcessor.class.getName();
	}

	public int getType() {
		return EventProcessor.TYPE_LOCAL;
	}

	public int getWeight() {
		return 0;
	}
	
	public void addService(Service s){
		list.add(s);
	}

	public List<String> getRegex() {
		List<String> list = new ArrayList<String>();
		list.add("(.)*");
		return list;
	}

	public boolean isRead() {
		// TODO Auto-generated method stub
		return true;
	}

	public void setRead(boolean read) {
		// TODO Auto-generated method stub
		
	}
}
