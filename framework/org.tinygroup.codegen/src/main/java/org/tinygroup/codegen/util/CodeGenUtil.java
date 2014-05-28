package org.tinygroup.codegen.util;

import java.io.File;

import org.tinygroup.commons.tools.StringUtil;

public  class CodeGenUtil {

	public static String repalcePackage(String text){
		String str= StringUtil.replace(text, ".", File.separator);
		if(!str.endsWith(File.separator)){
			str=str+File.separator;
		}
		return str;
	}
	
	public static String repalceFileSeparator(String text){
		return StringUtil.replace(text, File.separator,".");
	}
	
}
