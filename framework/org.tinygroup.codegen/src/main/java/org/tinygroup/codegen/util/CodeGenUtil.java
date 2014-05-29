package org.tinygroup.codegen.util;

import java.io.File;

import org.tinygroup.commons.tools.StringUtil;

public  class CodeGenUtil {

	public static String packageToPath(String text){
		String str= StringUtil.replace(text, ".", File.separator);
		if(!str.endsWith(File.separator)){
			str=str+File.separator;
		}
		return str;
	}
	
	public static String pathToPackage(String text){
		return StringUtil.replace(text, File.separator,".");
	}
	
}
