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

import org.tinygroup.exception.TinySysRuntimeException;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.SchemaProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能说明:URL资源
 * <p/>
 * <p/>
 * 开发人员: renhui <br>
 * 开发时间: 2013-8-29 <br>
 * <br>
 */
public abstract class URLFileObject extends AbstractFileObject {
	protected URL url;
	protected String path;
	protected String fileName;
	protected String extName;
	protected long fileSize;
	protected List<FileObject> children;

	public URLFileObject(SchemaProvider schemaProvider) {
		super(schemaProvider);
	}

	public URLFileObject(SchemaProvider schemaProvider, String resource) {
		super(schemaProvider);
		try {
			url = new URL(resource);
		} catch (MalformedURLException e) {
			throw new TinySysRuntimeException("不能定位到某个资源！");
		}
	}

	public String getAbsolutePath() {
		return url.getPath();
	}

	public String getPath() {
		if (path == null) {
			if (parent == null) {
				path = "";
			} else {
				path = parent.getPath() + "/" + getFileName();
			}
		}
		return path;
	}

	public String getFileName() {
		if (fileName == null) {
			String absolutePath = getAbsolutePath();
			int lastIndex = absolutePath.lastIndexOf("/");
			if (lastIndex != -1) {
				fileName = absolutePath.substring(lastIndex + 1);
			} else {
				fileName = "";
			}
		}
		return fileName;
	}

	public String getExtName() {
		if (extName == null) {
			String fileName = getFileName();
			int lastIndex = fileName.lastIndexOf(".");
			if (lastIndex != -1) {
				extName = fileName.substring(lastIndex + 1);
			} else {
				extName = "";
			}
		}
		return extName;
	}

	public boolean isFolder() {
		return false;
	}

	public boolean isExist() {
		return true;
	}

	public InputStream getInputStream() {
		InputStream stream = null;
		try {
			stream = url.openStream();
		} catch (IOException e) {
			stream = null;
		}
		return stream;
	}

	public OutputStream getOutputStream() {
		try {
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			return connection.getOutputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public FileObject getChild(String fileName) {
		List<FileObject> children = getChildren();
		if (children == null || children.size() == 0) {
			return null;
		}
		for (FileObject fileObject : getChildren()) {
			if (fileObject.getFileName().equals(fileName)) {
				return fileObject;
			}
		}
		return null;
	}

	public List<FileObject> getChildren() {
		if (children == null) {
			children = new ArrayList<FileObject>();
		}
		return children;
	}

	public boolean isInPackage() {
		return false;
	}

	public URL getURL() {
		return url;
	}

}
