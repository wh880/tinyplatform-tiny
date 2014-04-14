package org.tinygroup.vfs.impl;

import org.apache.commons.net.ftp.*;
import org.tinygroup.exception.TinySysRuntimeException;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.SchemaProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by luoguo on 14-3-31.
 */
public class FtpFileObject extends URLFileObject {

    private FTPFile ftpFile;
    private FTPClient ftpClient;

    private FtpFileObject(SchemaProvider schemaProvider) {
        super(schemaProvider);
    }

    public FtpFileObject(SchemaProvider schemaProvider, String resource) {
        super(schemaProvider, resource);
        connectFtpServer();
        initFTPFile();
    }

    private void connectFtpServer() {
        try {
            ftpClient = new FTPClient();
            FTPClientConfig ftpClientConfig = new FTPClientConfig();
            ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
            ftpClient.configure(ftpClientConfig);
            if (url.getPort() <= 0) {
                ftpClient.connect(url.getHost());
            } else {
                ftpClient.connect(url.getHost(), url.getPort());
            }
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                throw new RuntimeException("连接失败！");
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
                if (!ftpClient.setFileType(FTP.BINARY_FILE_TYPE)) {
                    throw new RuntimeException("设置二进制类型失败");
                }
                ftpClient.setBufferSize(100000);
                ftpClient.setControlEncoding("utf-8");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initFTPFile() {
        try {
            String path = url.getPath();
            if (path.endsWith("/")) { // 如果且以"/"结尾，去掉"/"
                path = path.substring(0, path.lastIndexOf("/"));
            }
            String checkPath = path.substring(0, path.lastIndexOf("/")); // 资源在服务器中所属目录
            if (checkPath.length() == 0) { // 如果所属目录为根目录
                checkPath = "/";
            }

            String fileName = path.substring(path.lastIndexOf("/"));
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            ftpClient.enterLocalPassiveMode();
            FTPFile[] files = ftpClient.listFiles(recode(checkPath), new FtpFileFilterByName(fileName)); // 从上级目录的子目录中过滤出当前资源
            if (files != null && files.length == 1) {
                ftpFile = files[0];
            } else {
                throw new TinySysRuntimeException("查找资源失败，url=" + url);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getAbsolutePath() {
        if (url != null) { // 如果是根结点
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
        if (fileName == null) {
            fileName = ftpFile.getName();
        }
        return fileName;
    }

    public boolean isFolder() {
        return !ftpFile.isFile();
    }

    public long getLastModifiedTime() {
        return ftpFile.getTimestamp().getTimeInMillis();
    }

    public long getSize() {
        if (fileSize > 0) {
            return fileSize;
        } else if (isFolder()) {
            return 0;
        }

        fileSize = ftpFile.getSize();
        return fileSize;
    }

    public InputStream getInputStream() {
        if (isFolder()) {
            return null;
        }

        InputStream is = null;
        try {
            String remote = getAbsolutePath();
            remote = recode(remote);
            is = ftpClient.retrieveFileStream(remote);
        } catch (IOException e) {
            throw new RuntimeException("获取输入流异常");
        }

        return is;
    }

    public OutputStream getOutputStream() {
        if (isFolder()) {
            return null;
        }

        OutputStream os = null;
        try {
            String remote = getAbsolutePath();
            remote = recode(remote);
            // os = ftpClient.storeUniqueFileStream(remote);
            os = ftpClient.storeFileStream(remote);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return os;
    }

    public List<FileObject> getChildren() {
        if (!isFolder()) {
            return null;
        }

        if (children == null) {
            try {
                // String pathname = ftpFile.getName();
                String pathname = getAbsolutePath();
                FTPFile[] files = ftpClient.listFiles(recode(pathname));
                List<FileObject> fileObjects = new ArrayList<FileObject>();
                for (FTPFile file : files) {
                    FtpFileObject fileObject = new FtpFileObject(schemaProvider);
                    fileObject.setParent(this);
                    fileObject.ftpFile = file;
                    fileObject.ftpClient = this.ftpClient;
                    fileObjects.add(fileObject);
                }
                return fileObjects;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return children;
    }

    private String recode(String str) {
        String recode = str;
        try {
            recode = new String(str.getBytes("UTF-8"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) { // 转码失败,忽略之
        }
        return recode;
    }

}
