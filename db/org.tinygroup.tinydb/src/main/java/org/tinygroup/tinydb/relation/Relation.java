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
package org.tinygroup.tinydb.relation;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 表间关系定义
 * 
 * @author luoguo
 * 
 */
@XStreamAlias("relation-config")
public class Relation {
	public static final String MORE_TO_ONE = "n:1";
	public static final String ONE_TO_MORE = "1:n";
	public static final String LIST_MODE = "List";
	public static final String SET_MODE = "Set";
	public static final String ARRAY_MODE = "Array";

	@XStreamAsAttribute
	String id;// 关系标识
	@XStreamAsAttribute
	String type;// 类型
	@XStreamAsAttribute
	@XStreamAlias("collection-mode")
	String collectionMode;// array,list,set
	//keyName是当前表中的外键字段
	@XStreamAsAttribute
	@XStreamAlias("key-name")
	String keyName;// 外键属性，从表的关联名称，不一定与主表的主键名称相同
	//若父bean与当前bean关系是ontToMore，则此属性作为当前bean存在于父bean的key值
	@XStreamAlias("relation-key-name")
	@XStreamAsAttribute
	String relationKeyName;
	//mainName是parent表中的主键字段
	@XStreamAsAttribute
	@XStreamAlias("main-name")//主表关联名称，不一定是主表的主键名称
	String mainName;
	@XStreamAsAttribute
	String mode;// 1..n,n..1
	@XStreamAlias("relations")
	List<Relation> relations;// 子关系
	transient Relation parent;// 父亲
	
	
	public Relation() {
		
	}

	public Relation(String id, String type, String collectionMode,
			String keyName, String mode) {
		super();
		this.id = id;
		this.type = type;
		this.collectionMode = collectionMode;
		this.keyName = keyName;
		this.mode = mode;
	}

	
	public Relation(String id, String type, String collectionMode,
			String keyName, String mode, List<Relation> relations) {
		super();
		this.id = id;
		this.type = type;
		this.collectionMode = collectionMode;
		this.keyName = keyName;
		this.mode = mode;
		setRelations(relations);
	}
	
	public Relation(String id, String type, String collectionMode,
			String keyName, String mode, Relation relation) {
		super();
		this.id = id;
		this.type = type;
		this.collectionMode = collectionMode;
		this.keyName = keyName;
		this.mode = mode;
		addRelation(relation);
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCollectionMode() {
		if (collectionMode == null) {
			collectionMode = LIST_MODE;
		}
		return collectionMode;
	}

	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<Relation> getRelations() {
		if(relations==null){
			relations=new ArrayList<Relation>();
		}
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		for (Relation relation : relations) {
			addRelation(relation);
		}
	}

	public void addRelation(Relation relation) {
		if (relations == null) {
			relations = new ArrayList<Relation>();
		}
		relations.add(relation);
		relation.setParent(this);
	}

	public Relation getParent() {
		return parent;
	}

	public void setParent(Relation parent) {
		this.parent = parent;
	}

	public String getMainName() {
		return mainName;
	}

	public void setMainName(String mainName) {
		this.mainName = mainName;
	}

	public String getRelationKeyName() {
		return relationKeyName;
	}

	public void setRelationKeyName(String relationKeyName) {
		this.relationKeyName = relationKeyName;
	}
	
	
}
