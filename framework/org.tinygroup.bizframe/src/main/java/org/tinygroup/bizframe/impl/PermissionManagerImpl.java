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
package org.tinygroup.bizframe.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.tinygroup.bizframe.PermissionObject;
import org.tinygroup.bizframe.PermissionSubject;

//TODO，缓冲支持，权限主体、客体继承支持，需要在策略代码中加以实现
/**
 * 如果没有配置策略，那么就是允许即返回是否配置了允许，禁止即是否配置了禁止
 * 
 * @author luoguo
 * 
 */
public class PermissionManagerImpl<K extends Comparable<K>> extends
		AbstractPermissionManager<K> {

	private Map<String, PermissionSubject<K, ?>> permissionSubjectMap = new HashMap<String, PermissionSubject<K, ?>>();
	private Map<String, PermissionObject<K, ?>> permissionObjectMap = new HashMap<String, PermissionObject<K, ?>>();
	private Map<String, HashSet<String>> allowPermissionMap = new HashMap<String, HashSet<String>>();
	private Map<String, HashSet<String>> blockPermissionMap = new HashMap<String, HashSet<String>>();

	public PermissionSubject<K, ?> addPermissionSubject(
			PermissionSubject<K, ?> permissionSubject) {
		permissionSubjectMap.put(getPermissionSubjectKey(permissionSubject),
				permissionSubject);
		return permissionSubject;
	}

	public PermissionObject<K, ?> addPermissionObject(
			PermissionObject<K, ?> permissionObject) {
		permissionObjectMap.put(getPermissionObjectKey(permissionObject),
				permissionObject);
		return permissionObject;
	}

	public void removePermissionObject(PermissionObject<K, ?> permissionObject) {
		permissionObjectMap.remove(getPermissionObjectKey(permissionObject));
	}

	public void removePermissionSubject(
			PermissionSubject<K, ?> permissionSubject) {
		permissionSubjectMap.remove(getPermissionSubjectKey(permissionSubject));
	}

	public PermissionSubject<K, ?> getPermissionSubject(String subjectBeanType,
			K keyValue, Class<? extends PermissionSubject> subjectClassType) {
		return permissionSubjectMap.get(getKey(subjectBeanType, keyValue));
	}

	public PermissionObject<K, ?> getPermissionObject(String objectBeanType,
			K keyValue, Class<? extends PermissionObject> objectClassType) {
		return permissionObjectMap.get(getKey(objectBeanType, keyValue));
	}

	public void addAllowPermission(PermissionSubject<K, ?> permissionSubject,
			PermissionObject<K, ?> permissionObject) {
		String permissionSubjectKey = getPermissionSubjectKey(permissionSubject);
		String permissionObjectKey = getPermissionObjectKey(permissionObject);
		addAllowPermission(permissionSubjectKey, permissionObjectKey);

	}

	public void addAllowPermission(
			List<PermissionSubject<K, ?>> permissionSubjectList,
			List<PermissionObject<K, ?>> permissionObjectList) {
		for (PermissionSubject<K, ?> permissionSubject : permissionSubjectList) {
			for (PermissionObject<K, ?> permissionObject : permissionObjectList) {
				addAllowPermission(permissionSubject, permissionObject);
			}
		}
	}

	public void removeAllowPermission(
			PermissionSubject<K, ?> permissionSubject,
			PermissionObject<K, ?> permissionObject) {
		String permissionSubjectKey = getPermissionSubjectKey(permissionSubject);
		String permissionObjectKey = getPermissionObjectKey(permissionObject);
		removeAllowPermission(permissionSubjectKey, permissionObjectKey);
	}

	public void removeAllowPermission(
			List<PermissionSubject<K, ?>> permissionSubjectList,
			List<PermissionObject<K, ?>> permissionObjectList) {
		for (PermissionSubject<K, ?> permissionSubject : permissionSubjectList) {
			for (PermissionObject<K, ?> permissionObject : permissionObjectList) {
				removeAllowPermission(permissionSubject, permissionObject);
			}
		}
	}

	public void addAllowPermission(String permissionSubjectType,
			K permissionSubjectId, String permissionObjectType,
			K permissionObjectId) {
		String permissionSubjectKey = getKey(permissionSubjectType,
				permissionSubjectId);
		String permissionObjectKey = getKey(permissionObjectType,
				permissionObjectId);
		addAllowPermission(permissionSubjectKey, permissionObjectKey);

	}

	public void removeAllowPermission(String permissionSubjectType,
			K permissionSubjectId, String permissionObjectType,
			K permissionObjectId) {
		String permissionSubjectKey = getKey(permissionSubjectType,
				permissionSubjectId);
		String permissionObjectKey = getKey(permissionObjectType,
				permissionObjectId);
		removeAllowPermission(permissionSubjectKey, permissionObjectKey);

	}

	private void removeAllowPermission(String permissionSubjectKey,
			String permissionObjectKey) {
		HashSet<String> permissionObjectIdList = allowPermissionMap
				.get(permissionSubjectKey);
		if (permissionObjectIdList != null) {
			permissionObjectIdList.remove(permissionObjectKey);
		}
	}

	private void addAllowPermission(String permissionSubjectKey,
			String permissionObjectKey) {
		HashSet<String> permissionObjectIdList = allowPermissionMap
				.get(permissionSubjectKey);
		if (permissionObjectIdList == null) {
			permissionObjectIdList = new HashSet<String>();
			allowPermissionMap
					.put(permissionSubjectKey, permissionObjectIdList);
		}
		permissionObjectIdList.add(permissionObjectKey);
	}

	public void addBlockPermission(PermissionSubject<K, ?> permissionSubject,
			PermissionObject<K, ?> permissionObject) {
		String permissionSubjectKey = getPermissionSubjectKey(permissionSubject);
		String permissionObjectKey = getPermissionObjectKey(permissionObject);
		addBlockPermission(permissionSubjectKey, permissionObjectKey);

	}

	public void addBlockPermission(
			List<PermissionSubject<K, ?>> permissionSubjectList,
			List<PermissionObject<K, ?>> permissionObjectList) {
		for (PermissionSubject<K, ?> permissionSubject : permissionSubjectList) {
			for (PermissionObject<K, ?> permissionObject : permissionObjectList) {
				addBlockPermission(permissionSubject, permissionObject);
			}
		}
	}

	public void removeBlockPermission(
			PermissionSubject<K, ?> permissionSubject,
			PermissionObject<K, ?> permissionObject) {
		String permissionSubjectKey = getPermissionSubjectKey(permissionSubject);
		String permissionObjectKey = getPermissionObjectKey(permissionObject);
		removeBlockPermission(permissionSubjectKey, permissionObjectKey);
	}

	public void removeBlockPermission(
			List<PermissionSubject<K, ?>> permissionSubjectList,
			List<PermissionObject<K, ?>> permissionObjectList) {
		for (PermissionSubject<K, ?> permissionSubject : permissionSubjectList) {
			for (PermissionObject<K, ?> permissionObject : permissionObjectList) {
				removeBlockPermission(permissionSubject, permissionObject);
			}
		}
	}

	public void addBlockPermission(String permissionSubjectType,
			K permissionSubjectId, String permissionObjectType,
			K permissionObjectId) {
		String permissionSubjectKey = getKey(permissionSubjectType,
				permissionSubjectId);
		String permissionObjectKey = getKey(permissionObjectType,
				permissionObjectId);
		addBlockPermission(permissionSubjectKey, permissionObjectKey);

	}

	public void removeBlockPermission(String permissionSubjectType,
			K permissionSubjectId, String permissionObjectType,
			K permissionObjectId) {
		String permissionSubjectKey = getKey(permissionSubjectType,
				permissionSubjectId);
		String permissionObjectKey = getKey(permissionObjectType,
				permissionObjectId);
		removeBlockPermission(permissionSubjectKey, permissionObjectKey);

	}

	private void removeBlockPermission(String permissionSubjectKey,
			String permissionObjectKey) {
		HashSet<String> permissionObjectIdList = blockPermissionMap
				.get(permissionSubjectKey);
		if (permissionObjectIdList != null) {
			permissionObjectIdList.remove(permissionObjectKey);
		}
	}

	private void addBlockPermission(String permissionSubjectKey,
			String permissionObjectKey) {
		HashSet<String> permissionObjectIdList = blockPermissionMap
				.get(permissionSubjectKey);
		if (permissionObjectIdList == null) {
			permissionObjectIdList = new HashSet<String>();
			blockPermissionMap
					.put(permissionSubjectKey, permissionObjectIdList);
		}
		permissionObjectIdList.add(permissionObjectKey);
	}

	private String getKey(String type, K id) {
		return type + "-" + id;
	}

	public boolean isBlockDirectly(PermissionSubject<K, ?> permissionSubject,
			PermissionObject<K, ?> permissionObject) {
		String permissionSubjectKey = getPermissionSubjectKey(permissionSubject);
		String permissionObjectKey = getPermissionObjectKey(permissionObject);
		return isBlockDirectly(permissionSubjectKey, permissionObjectKey);
	}

	private boolean isBlockDirectly(String permissionSubjectKey,
			String permissionObjectKey) {
		HashSet<String> permissionObjectIdList = blockPermissionMap
				.get(permissionSubjectKey);
		if (permissionObjectIdList == null) {
			return false;
		} else {
			return permissionObjectIdList.contains(permissionObjectKey);
		}
	}

	public boolean isAllowDirectly(PermissionSubject<K, ?> permissionSubject,
			PermissionObject<K, ?> permissionObject) {
		String permissionSubjectKey = getPermissionSubjectKey(permissionSubject);
		String permissionObjectKey = getPermissionObjectKey(permissionObject);
		return isAllowDirectly(permissionSubjectKey, permissionObjectKey);
	}

	private boolean isAllowDirectly(String permissionSubjectKey,
			String permissionObjectKey) {
		HashSet<String> permissionObjectIdList = allowPermissionMap
				.get(permissionSubjectKey);
		if (permissionObjectIdList == null) {
			return false;
		} else {
			return permissionObjectIdList.contains(permissionObjectKey);
		}
	}

	public boolean isBlockDirectly(String permissionSubjectType,
			K permissionSubjectId, String permissionObjectType,
			K permissionObjectId) {
		String permissionSubjectKey = getKey(permissionSubjectType,
				permissionSubjectId);
		String permissionObjectKey = getKey(permissionObjectType,
				permissionObjectId);
		return isBlockDirectly(permissionSubjectKey, permissionObjectKey);
	}

	public boolean isAllowDirectly(String permissionSubjectType,
			K permissionSubjectId, String permissionObjectType,
			K permissionObjectId) {
		String permissionSubjectKey = getKey(permissionSubjectType,
				permissionSubjectId);
		String permissionObjectKey = getKey(permissionObjectType,
				permissionObjectId);
		return isAllowDirectly(permissionSubjectKey, permissionObjectKey);
	}

	private String getPermissionSubjectKey(
			PermissionSubject<K, ?> permissionSubject) {
		return getKey(permissionSubject.getType(), permissionSubject.getId());
	}

	private String getPermissionObjectKey(
			PermissionObject<K, ?> permissionObject) {
		return getKey(permissionObject.getType(), permissionObject.getId());
	}

	public List<PermissionSubject<K, ?>> getPermissionSubjects(
			String subjectBeanType,
			Class<? extends PermissionSubject> subjectClassType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PermissionObject<K, ?>> getPermissionObjects(
			String objectBeanType,
			Class<? extends PermissionObject> objectClassType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PermissionObject<K, ?>> getAssignedPermission(
			String subjectBeanType, String objectBeanType,
			K permissionSubjectId,
			Class<? extends PermissionObject> objectClassType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PermissionSubject<K, ?>> getChildPermissionSubjects(
			String subjectBeanType, K parentSubjectId,
			Class<? extends PermissionSubject> subjectClassType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PermissionObject<K, ?>> getChildPermissionObjects(
			String objectBeanType, K parentObjectId,
			Class<? extends PermissionObject> objectClassType) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
