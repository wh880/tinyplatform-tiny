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
package org.tinygroup.vfs.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.SchemaProvider;
import org.tinygroup.vfs.VFS;

public class FileObjectImpl extends AbstractFileObject {

	private String path;
	private FileObject parent;
	private List<FileObject> children;
	private File file = null;

	public FileObjectImpl(SchemaProvider schemaProvider, String resource) {
		super(schemaProvider);
		File newFile = new File(resource);
		init(newFile);

	}

	private void init(File file) {
		this.file = file;
	}

	public String toString() {
		return file.getAbsolutePath();
	}

	public FileObjectImpl(SchemaProvider schemaProvider, File file) {
		super(schemaProvider);
		init(file);
	}

	public String getFileName() {
		return file.getName();
	}

	public String getPath() {
		if (path == null) {// 如果没有计算过
			if (parent != null) {// 如果有父亲
				path = parent.getPath() + "/" + getFileName();
			} else {
				if (file.isDirectory()) {
					return "";
				} else {
					return "/" + file.getName();
				}
			}
		}
		return path;
	}

	public String getAbsolutePath() {
		return file.getAbsolutePath();
	}

	public String getExtName() {
		int lastIndexOfDot = file.getName().lastIndexOf(".");
		if (lastIndexOfDot == -1) {
			// 如果不存在
			return null;
		}
		return file.getName().substring(lastIndexOfDot + 1);
	}

	public long getSize() {
		if (file.exists() && file.isFile()) {
			return file.length();
		}
		return 0;
	}

	public InputStream getInputStream() {
		try {
			if (file.exists() && file.isFile()) {
				return new BufferedInputStream(new FileInputStream(file));
			} else {
				return null;
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(file.getAbsolutePath()
					+ "获取FileInputStream出错，原因" + e);
		} finally {
		}
	}

	public boolean isFolder() {
		if (file.exists()) {
			return file.isDirectory();
		}
		return false;
	}

	public FileObject getParent() {
		return parent;
	}

	public List<FileObject> getChildren() {
		if (children != null) {
			return children;
		}
		if (file.exists() && file.isDirectory()) {
			File[] subFiles = file.listFiles();
			children = new ArrayList<FileObject>();
			for (File subfile : subFiles) {
				FileObject fileObject = null;
				if (subfile.getName().endsWith(".jar")) {
					fileObject = VFS.resolveFile(subfile.getAbsolutePath());
				} else {
					fileObject = new FileObjectImpl(schemaProvider, subfile);
					fileObject.setParent(this);
				}
				children.add(fileObject);
			}
		}
		return children;
	}

	public long getLastModifiedTime() {
		return file.lastModified();
	}

	public SchemaProvider getSchemaProvider() {
		return schemaProvider;
	}

	public boolean isExist() {
		return file.exists();
	}

	public boolean isInPackage() {
		return false;
	}

	public FileObject getChild(String fileName) {
		if (getChildren() != null) {
			for (FileObject fileObject : getChildren()) {
				if (fileObject.getFileName().equals(fileName)) {
					return fileObject;
				}
			}
		}
		return null;
	}

	public URL getURL() {
		try {
			return new URL(FileSchemaProvider.FILE_PROTOCAL + getAbsolutePath());
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public OutputStream getOutputStream() {
		try {
			return new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public void setParent(FileObject fileObject) {
		this.parent = fileObject;
	}

}
