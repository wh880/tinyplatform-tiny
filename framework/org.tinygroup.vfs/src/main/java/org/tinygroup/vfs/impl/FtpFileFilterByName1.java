package org.tinygroup.vfs.impl;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

/**
 * 只取文件名符合条件的ftp资源
 * @author wanggf10141
 *
 */
public class FtpFileFilterByName1 implements FTPFileFilter {

	private String fileName;

	public FtpFileFilterByName1(String fileName) {
		this.fileName = fileName;
	}

	public boolean accept(FTPFile ftpFile) {
		if (fileName.equals(ftpFile.getName())) {
			return true;
		}
		return false;
	}

}
