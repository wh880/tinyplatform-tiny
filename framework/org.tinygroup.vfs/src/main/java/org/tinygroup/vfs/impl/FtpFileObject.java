package org.tinygroup.vfs.impl;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.SchemaProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoguo on 14-3-31.
 */
public class FtpFileObject extends AbstractFileObject {
    private FTPClient ftpClient;
    URL url;
    private String extName;
    FTPFile ftpFile = null;
    private String path;

    public FtpFileObject(SchemaProvider schemaProvider) {
        super(schemaProvider);
    }

    public FtpFileObject(SchemaProvider ftpSchemaProvider, String resource) {
        super(ftpSchemaProvider);
        try {
            this.path = "";
            url = new URL(resource);
            connectFtpServer();
            //添加对文件的处理
            String path = url.getPath();
            String checkPath = path.substring(0, path.lastIndexOf("/"));
            if (!path.endsWith("/")) {//说明不是目录
                String fileName = path.substring(path.lastIndexOf("/"));
                fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                FTPFile[] files = ftpClient.listFiles(checkPath);
                for (FTPFile file : files) {
                    if (file.getName().equals(fileName)) {
                        ftpFile = file;//如果确实是文件
                        return;
                    }
                }
            }
            //是目录
            ftpFile = new FTPFile();
            ftpFile.setType(FTPFile.DIRECTORY_TYPE);
            ftpFile.setSize(0);
            ftpFile.setName(checkPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void connectFtpServer() throws IOException {
        ftpClient = new FTPClient();
        if (url.getPort() <= 0) {
            ftpClient.connect(url.getHost());
        } else {
            ftpClient.connect(url.getHost(), url.getPort());
        }
        if (url.getUserInfo() != null) {
            String userInfo[] = url.getUserInfo().split(":");
            String userName = null;
            String password = null;
            if (userInfo.length >= 1) {
                userName = userInfo[0];
            }
            if (userInfo.length >= 2) {
                password = userInfo[1];
            }
            if (!ftpClient.login(userName, password)) {
                throw new RuntimeException("登录失败：" + url.toString());
            }
        }
    }


    public URL getURL() {
        return url;
    }

    public String getAbsolutePath() {
        if (url != null) {//如果是根结点
            return url.getPath();
        }
        String parentAbsolutePath = parent.getAbsolutePath();
        if (parentAbsolutePath.endsWith("/"))
            return parentAbsolutePath + ftpFile.getName();
        else
            return parentAbsolutePath + "/" + ftpFile.getName();
    }

    public String getPath() {
        if (path == null) {// 如果没有计算过
            if (parent != null) {// 如果有父亲
                path = parent.getPath() + "/" + getFileName();
            } else {
                if (ftpFile.isDirectory()) {
                    return "";
                } else {
                    return "/" + ftpFile.getName();
                }
            }
        }
        return path;
    }


    public String getFileName() {
        return ftpFile.getName();
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
        return ftpFile.isDirectory();
    }

    public boolean isInPackage() {
        return false;
    }

    public boolean isExist() {
        return true;
    }

    public long getLastModifiedTime() {
        return ftpFile.getTimestamp().getTimeInMillis();
    }

    public long getSize() {
        return ftpFile.getSize();
    }

    public InputStream getInputStream() {
        try {
            return ftpClient.retrieveFileStream(ftpFile.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public OutputStream getOutputStream() {
        try {
            return ftpClient.storeFileStream(ftpFile.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<FileObject> getChildren() {
        try {
            FTPFile[] files = ftpClient.listFiles(ftpFile.getName());
            List<FileObject> fileObjects = new ArrayList<FileObject>();
            for (FTPFile file : files) {
                FtpFileObject fileObject = new FtpFileObject(schemaProvider);
                fileObject.setParent(this);
                fileObject.ftpFile = file;
                fileObjects.add(fileObject);
            }
            return fileObjects;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileObject getChild(String fileName) {
        try {
            FTPFile[] files = ftpClient.listFiles(ftpFile.getName());
            for (FTPFile file : files) {
                if (file.getName().equals(fileName)) {
                    FtpFileObject fileObject = new FtpFileObject(schemaProvider);
                    fileObject.ftpFile = file;
                    return fileObject;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("路径" + getPath() + "下找不到对应的文件：" + fileName);
    }
}
