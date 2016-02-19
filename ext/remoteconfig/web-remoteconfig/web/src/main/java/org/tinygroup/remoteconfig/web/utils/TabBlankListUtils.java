/**
 * 
 */
package org.tinygroup.remoteconfig.web.utils;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.remoteconfig.IRemoteConfigConstant;

/**
 * 表格展示黑名单
 * 系统保留字
 * 
 * @author yanwj
 *
 */
public class TabBlankListUtils {

	public static List<String> addBlankList = new ArrayList<String>();
	
	static{
		addBlankList.add(IRemoteConfigConstant.MODULE_FLAG);
	}
	
	public static List<String> getBlankList() {
		return addBlankList;
	}
	
	public static boolean isAccept(String name) {
		return !addBlankList.contains(name);
	}
	
}
