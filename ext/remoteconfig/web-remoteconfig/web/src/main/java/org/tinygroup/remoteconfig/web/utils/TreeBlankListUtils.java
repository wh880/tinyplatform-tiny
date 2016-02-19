/**
 * 
 */
package org.tinygroup.remoteconfig.web.utils;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.remoteconfig.IRemoteConfigConstant;

/**
 * 树节点黑名单
 * 系统保留字
 * 
 * @author yanwj
 *
 */
public class TreeBlankListUtils {

	public static List<String> treeBlankList = new ArrayList<String>();
	
	static{
		treeBlankList.add(IRemoteConfigConstant.MODULE_FLAG);
	}
	
	public static List<String> getBlankList() {
		return treeBlankList;
	}
	
	public static boolean isAccept(String name) {
		return !treeBlankList.contains(name);
	}
	
}
