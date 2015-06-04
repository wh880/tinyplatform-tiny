package org.tinygroup.springmvc.util;


public final class WebUtil {
	
	public static String getExtension(String urlPath) {
		int end = urlPath.indexOf(';');
		if (end == -1) {
			end = urlPath.indexOf('?');
			if (end == -1) {
				end = urlPath.length();
			}
		}
		int dotIndex = urlPath.lastIndexOf('.');
		if (dotIndex != -1 && dotIndex < end) {
			return urlPath.substring(dotIndex + 1, end);
		}
		return null;
	}

	public static String getUriWithoutExtension(String urlPath) {
		int end = urlPath.indexOf(';');
		if (end == -1) {
			end = urlPath.indexOf('?');
			if (end == -1) {
				end = urlPath.length();
			}
		}
		int dotIndex = urlPath.lastIndexOf('.');
		if (dotIndex != -1 && dotIndex < end) {
			return urlPath.substring(0, dotIndex);
		}
		return urlPath;
	}

}
