/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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
 */
package org.tinygroup.weblayer.tinyprocessor;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tinygroup.commons.file.FileDealUtil;
import org.tinygroup.commons.io.StreamUtil;
import org.tinygroup.commons.tools.FileUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.fileresolver.FullContextFileRepository;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.uiengine.config.UIComponent;
import org.tinygroup.uiengine.manager.UIComponentManager;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.weblayer.AbstractTinyProcessor;
import org.tinygroup.weblayer.WebContext;

/**
 * UI引擎处理css及js文件的合并并输出
 *
 * @author luoguo
 */
public class UiEngineTinyProcessor extends AbstractTinyProcessor {
    public static final int CACHE_TIME = 86400;
    static Pattern urlPattern = Pattern.compile("(url[(][\"\']?)(.*?)([\"\']?[)])");
    UIComponentManager uiComponentManager;
    private static final String CACHE_CONTROL = "max-age=315360000";
    private FullContextFileRepository fullContextFileRepository;
    
    public static final String UI_STORAGE_TYPE = "storageType";
    
    private JsResourceOperator jsResourceOperator;
    private CssResourceOperator cssResourceOperator;
    
    public UIComponentManager getUiComponentManager() {
        return uiComponentManager;
    }

    public void setUiComponentManager(UIComponentManager uiComponentManager) {
        this.uiComponentManager = uiComponentManager;
    }

    public FullContextFileRepository getFullContextFileRepository() {
        return fullContextFileRepository;
    }

    public void setFullContextFileRepository(FullContextFileRepository fullContextFileRepository) {
        this.fullContextFileRepository = fullContextFileRepository;
    }

	private static final Logger logger = LoggerFactory.getLogger(UiEngineTinyProcessor.class);

    public void reallyProcess(String servletPath, WebContext context) throws ServletException, IOException {
        logger.logMessage(LogLevel.DEBUG, "{}开始处理...", servletPath);
        HttpServletResponse response = context.getResponse();
        HttpServletRequest request = context.getRequest();
        String contextPath = context.get("TINY_CONTEXT_PATH");
        String lastModifiedSign;
        long modifiedSign = 0;
        long now = System.currentTimeMillis();
        if (servletPath.endsWith("uijs")) {
            modifiedSign = jsResourceOperator.getModifiedSign();
            response.setContentType("text/javascript");
        } else if (servletPath.endsWith("uicss")) {
            modifiedSign = cssResourceOperator.getModifiedSign();
            response.setContentType("text/css");
        } else {
            throw new RuntimeException("UiEngineTinyProcessor不能处理请求：" + servletPath);
        }
        lastModifiedSign = new Date(modifiedSign).toGMTString();
        String ims = request.getHeader("If-Modified-Since");
        if (ims != null && ims.length() > 0) {
            if (ims.equals(lastModifiedSign)) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                return;
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Last-modified", lastModifiedSign);
        response.setHeader("Connection", "keep-alive");
        response.setHeader("Cache-Control", CACHE_CONTROL);
        response.setHeader("Expires", new Date(now + CACHE_TIME).toGMTString());
        response.setHeader("Date", lastModifiedSign);
        if (servletPath.endsWith("uijs")) {
        	jsResourceOperator.writeJs(modifiedSign,response.getOutputStream());
        }
        if (servletPath.endsWith("uicss")) {
            cssResourceOperator.writeCss(modifiedSign,response.getOutputStream(), contextPath, servletPath);
        }

        logger.logMessage(LogLevel.DEBUG, "{}处理完成。", servletPath);

    }

    private static String convertUrl(String contextPath, String url, String servletPath) {
        if (contextPath == null) {
            contextPath = "";
        }
        if (url.startsWith("/") || url.startsWith("\\")) {
            return contextPath + url;
        } else if (url.startsWith("../") || url.startsWith("..\\")) {
            String firstThree = url.substring(0, 3);
            int count = 0;
            while (url.startsWith(firstThree)) {
                count++;
                url = url.substring(3);
            }
            String[] paths = servletPath.split("/");
            StringBuffer sb = new StringBuffer(contextPath);
            for (int i = 0; i < paths.length - count - 1; i++) {
                sb.append(paths[i]).append("/");
            }
            sb.append(url);
            return sb.toString();
        }
        return contextPath + servletPath.substring(0, servletPath.lastIndexOf('/') + 1) + url;
    }

	protected void customInit() throws ServletException {
		
		//初始化存储类型
		String storageType = get(UI_STORAGE_TYPE);
		
		jsResourceOperator = new JsResourceOperator();
		cssResourceOperator = new CssResourceOperator();
		
		if(StringUtil.isEmpty(storageType)){
			jsResourceOperator.storage = new TempFileStorage("uiengine.uijs");
			cssResourceOperator.storage =  new TempFileStorage("uiengine.uicss");
		}else{
			if("file".equals(storageType)){
				jsResourceOperator.storage = new TempFileStorage("uiengine.uijs");
				cssResourceOperator.storage =  new TempFileStorage("uiengine.uicss");
			}else if("memory".equals(storageType)){
				jsResourceOperator.storage = new MemoryStorage();
				cssResourceOperator.storage =  new MemoryStorage();
			}else{
				throw new RuntimeException(String.format("UiEngineTinyProcessor初始化失败:未知的存储类型[%s]，请检查配置文件", storageType));
			}
		}
		
	}
	
	/**
	 * 资源存储
	 * @author yancheng11334
	 *
	 */
	interface ResourceStorage {
		/**
		 * 资源是否还在
		 * @return
		 */
		boolean exist();
		/**
		 * 读取资源
		 * @return
		 */
		String read();
		/**
		 * 保存资源
		 * @param resource
		 */
		void store(String resource);
	}
	
	/**
	 * 临时文件存储，也是缺省配置
	 * @author yancheng11334
	 *
	 */
	class TempFileStorage implements ResourceStorage{

		private File storeFile;
		
		public TempFileStorage(String fileName){
			storeFile = new File(fileName);
		}

		public synchronized boolean exist() {
			return storeFile.exists();
		}
		
		public synchronized String read() {
			try {
				return FileUtil.readFileContent(storeFile, "UTF-8");
			} catch (Exception e) {
				throw new RuntimeException(String.format("读取文件[%s]发生异常:", storeFile.getAbsolutePath()),e);
			}
		}

		public  synchronized void store(String resource) {
			try {
				FileDealUtil.write(storeFile, resource);
			} catch (Exception e) {
				throw new RuntimeException(String.format("写入文件[%s]发生异常:", storeFile.getAbsolutePath()),e);
			}
		}

	}
	
	/**
	 * 内存方式存储
	 * @author yancheng11334
	 *
	 */
	class MemoryStorage implements ResourceStorage{

		private String result=null;
		
		public boolean exist() {
			return result!=null;
		}

		public String read() {
			return result;
		}

		public void store(String resource) {
            result = resource;			
		}
		
	}
	
	abstract class BaseOperator {
		protected ResourceStorage storage;
		protected long lastModified = 0;
		
		/**
		 * 计算修改码
		 * @return
		 */
		protected abstract long getModifiedSign(); 
	}
	
	/**
	 * JS资源操作
	 * @author yancheng11334
	 *
	 */
	class JsResourceOperator extends BaseOperator{
	
		public void writeJs(long modifiedSign,OutputStream outputStream) throws IOException {
			if(lastModified==0 || lastModified!=modifiedSign || !storage.exist()){
				//执行生成js逻辑,并更新验证时间戳
				byte[] b = createJs();
				storage.store(new String(b,"UTF-8"));
				lastModified = modifiedSign;
				outputStream.write(b);
			}else{
				//从存储中读取js
				String resource = storage.read();
				outputStream.write(resource.getBytes("UTF-8"));
			}
			outputStream.close();
		}
		
		private byte[] createJs() throws IOException{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			try{
				for (UIComponent component : uiComponentManager.getHealthUiComponents()) {
		            String[] paths = uiComponentManager.getComponentJsArray(component);
		            if (paths != null) {
		                for (String path : paths) {
		                    logger.logMessage(LogLevel.INFO, "正在处理js文件:<{}>", path);
		                    outputStream.write("try{\n".getBytes());
		                    FileObject fileObject = fullContextFileRepository.getFileObject(path);
		                    InputStream stream = new BufferedInputStream(fileObject.getInputStream());
		                    StreamUtil.io(stream, outputStream, true, false);
		                    stream.close();
		                    outputStream.flush();
		                    outputStream.write("\n;}catch(e){}\n".getBytes());
		                    logger.logMessage(LogLevel.INFO, "js文件:<{}>处理完毕", path);
		                }
		            }
		            if (component.getJsCodelet() != null) {
		                outputStream.write(component.getJsCodelet().getBytes("UTF-8"));
		            }
		        }
				return outputStream.toByteArray();
			}finally{
				outputStream.close();
			}
		}

		public long getModifiedSign() {
			long time = 0;
	        for (UIComponent component : uiComponentManager.getHealthUiComponents()) {
	            String[] paths = uiComponentManager.getComponentJsArray(component);
	            if (paths != null) {
	                for (String path : paths) {
	                    FileObject fileObject = fullContextFileRepository.getFileObject(path);
	                    if (fileObject != null && fileObject.isExist()) {
	                        time += fileObject.getLastModifiedTime();
	                        time += path.hashCode();
	                    } else {
	                        throw new RuntimeException("不能找到资源文件：" + component.getName() + "-" + path);
	                    }
	                }
	            }
	        }
	        return time;
		}
	}
	
	/**
	 * CSS资源操作
	 * @author yancheng11334
	 *
	 */
    class CssResourceOperator extends BaseOperator{
    	
    	public void writeCss(long modifiedSign,OutputStream outputStream,String contextPath,String servletPath)
                throws IOException {
			if(lastModified==0 || lastModified!=modifiedSign || !storage.exist()){
				//执行生成css逻辑,并更新验证时间戳
				byte[] b = createCss(contextPath,servletPath);
				storage.store(new String(b,"UTF-8"));
				lastModified = modifiedSign;
				outputStream.write(b);
			}else{
				//从存储中读取css
				String resource = storage.read();
				outputStream.write(resource.getBytes("UTF-8"));
			}
			outputStream.close();
    	}
    	
    	private byte[] createCss(String contextPath,String servletPath) throws IOException{
    		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    		try{
    			outputStream.write("@charset \"utf-8\";\n".getBytes());
                for (UIComponent component : uiComponentManager.getHealthUiComponents()) {
                    String[] paths = uiComponentManager.getComponentCssArray(component);
                    if (paths != null) {
                        for (String path : paths) {
                            logger.logMessage(LogLevel.INFO, "正在处理css文件:<{}>", path);
                            FileObject fileObject = fullContextFileRepository.getFileObject(path);
                            InputStream stream = new BufferedInputStream(fileObject.getInputStream());
                            byte[] buffer = new byte[stream.available()];
                            stream.read(buffer);
                            stream.close();
                            writeCss(outputStream, contextPath, new String(buffer, "UTF-8"), fileObject.getPath());
                            outputStream.write('\n');
                            logger.logMessage(LogLevel.INFO, "css文件:<{}>处理完毕", path);
                        }
                    }
                    if (component.getCssCodelet() != null) {
                        writeCss(outputStream, contextPath, component.getCssCodelet(), servletPath);
                    }
                }
                return outputStream.toByteArray();
    		}finally{
    			outputStream.close();
    		}
    	}
    	
    	private void writeCss(OutputStream outputStream, String contextPath, String string, String servletPath)
                throws IOException {
            Matcher matcher = urlPattern.matcher(string);
            int curpos = 0;
            while (matcher.find()) {
                outputStream.write(string.substring(curpos, matcher.start()).getBytes("UTF-8"));
                if (matcher.group(2).trim().startsWith("data:")) {
                    outputStream.write(matcher.group().getBytes("UTF-8"));
                } else {
                    outputStream.write(matcher.group(1).getBytes("UTF-8"));
                    outputStream.write(convertUrl(contextPath, matcher.group(2), servletPath).getBytes("UTF-8"));
                    outputStream.write(matcher.group(3).getBytes("UTF-8"));
                }
                curpos = matcher.end();
                continue;
            }
            outputStream.write(string.substring(curpos).getBytes("UTF-8"));
        }


		public long getModifiedSign() {
			long time = 0;
	        for (UIComponent component : uiComponentManager.getHealthUiComponents()) {
	            if (component != null) {
	                String[] paths = uiComponentManager.getComponentCssArray(component);
	                if (paths != null) {
	                    for (String path : paths) {
	                        FileObject fileObject = fullContextFileRepository.getFileObject(path);
	                        if (fileObject != null && fileObject.isExist()) {
	                            time += fileObject.getLastModifiedTime();
	                            time += path.hashCode();
	                        } else {
	                            throw new RuntimeException("不能找到资源文件：" + component.getName() + "-" + path);
	                        }
	                    }
	                }
	            }
	        }
	        return time;
		}
	}
}
