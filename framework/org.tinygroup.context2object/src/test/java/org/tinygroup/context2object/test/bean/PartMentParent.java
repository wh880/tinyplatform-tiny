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
package org.tinygroup.context2object.test.bean;

import java.util.ArrayList;
import java.util.List;

public class PartMentParent implements PartInterface {
	private List<SmallCat> cats;
	private SmallCat[] catsArray;
	
	public List<SmallCat> getCats() {
		if(cats==null)
			cats = new ArrayList<SmallCat>();
		return cats;
	}
	public void setCats(List<SmallCat> cats) {
		this.cats = cats;
	}
	
	public SmallCat[] getCatsArray() {
		return catsArray;
	}
	public void setCatsArray(SmallCat[] catsArray) {
		this.catsArray = catsArray;
	}
	
	
}
