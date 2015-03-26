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
package org.tinygroup.tinydbdsl.formitem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tinygroup.tinydbdsl.base.Alias;
import org.tinygroup.tinydbdsl.util.DslUtil;
import org.tinygroup.tinydbdsl.visitor.FromItemVisitor;

/**
 * FromItem列表
 * @author renhui
 *
 */
public class FromItemList implements FromItem {

	private List<FromItem> fromItems;

	public FromItemList() {
		super();
		fromItems = new ArrayList<FromItem>();
	}

	public FromItemList(List<FromItem> fromItems) {
		this.fromItems = fromItems;
	}

	public FromItemList(FromItem... fromItems) {
		this.fromItems = new ArrayList<FromItem>();
		Collections.addAll(this.fromItems, fromItems);
	}

	public List<FromItem> getFromItems() {
		return fromItems;
	}

	public void setFromItems(List<FromItem> fromItems) {
		this.fromItems = fromItems;
	}

	public Alias getAlias() {
		return new Alias();
	}

	public void setAlias(Alias alias) {

	}

	@Override
	public String toString() {
		return DslUtil.getStringList(fromItems, true, false);
	}

	public void accept(FromItemVisitor fromItemVisitor) {
		fromItemVisitor.visit(this);
	}

}