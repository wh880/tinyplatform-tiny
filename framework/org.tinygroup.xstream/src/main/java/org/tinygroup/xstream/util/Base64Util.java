package org.tinygroup.xstream.util;

import com.thoughtworks.xstream.core.util.Base64Encoder;

public class Base64Util {

	public static String byteArrayToBase64(byte[] b){
		return new Base64Encoder().encode(b);
	}
	
	public static byte[] base64ToByteArray(String s){
		return  new Base64Encoder().decode(s);
	}
}
