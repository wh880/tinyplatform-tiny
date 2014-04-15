package org.tinygroup.dbrouterjdbc3.jdbc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtil {

	/**
	 * 删除某个文件夹下的所有文件夹和文件
	 * 
	 * @param delpath
	 *            String
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return boolean
	 */
	public static void deleteFile(String delPath) {
		if (delPath == null || delPath.trim().length() == 0) {
			return;
		}
		String pathname = delPath.replaceAll("\\\\", "/");
		File file = new File(pathname);
		if (file.isDirectory()) {
			String[] fileList = file.list();
			for (int i = 0; i < fileList.length; i++) {
				deleteFile(pathname + "/" + fileList[i]);
			}
		}
		// System.out.println(file.getAbsolutePath() + ",删除成功");
		file.delete();
	}

}
