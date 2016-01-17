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
package org.tinygroup.fileresolver.impl;

import com.thoughtworks.xstream.XStream;
import org.tinygroup.fileresolver.FileResolver;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;
import org.tinygroup.xstream.config.*;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Collection;

/**
 * 
 * 功能说明:xstream文件处理器.优先级别低于i18n,但是高于其他处理器 

 * 开发人员: renhui <br>
 * 开发时间: 2013-11-20 <br>
 * <br>
 */
public class XStreamFileProcessor extends AbstractFileProcessor {

	private static final String XSTREAM_FILE_EXTENSION = ".xstream.xml";
	private String xstreamFileExtension = XSTREAM_FILE_EXTENSION;

	public String getXstreamFileExtension() {
		return xstreamFileExtension;
	}

	public void setXstreamFileExtension(String xstreamFileExtension) {
		this.xstreamFileExtension = xstreamFileExtension;
	}

	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(XSTREAM_FILE_EXTENSION);
	}

	private void process(FileObject fileObject) {
		try {
			LOGGER.logMessage(LogLevel.INFO, "找到XStream配置文件[{0}]，并开始加载...",
					fileObject.getAbsolutePath());
			XStream loadXStream = XStreamFactory.getXStream();
			InputStream inputStream = fileObject.getInputStream();
			XStreamConfiguration xstreamConfiguration = (XStreamConfiguration) loadXStream
					.fromXML(inputStream);
			try {
				inputStream.close();
			} catch (Exception e) {
				LOGGER.errorMessage("关闭文件流时出错,文件路径:{}",e, fileObject.getAbsolutePath());
			}
			XStream xStream = XStreamFactory.getXStream(xstreamConfiguration
					.getPackageName());

			loadAnnotationClass(xStream, xstreamConfiguration);
			if (xstreamConfiguration.getxStreamClassAliases() != null) {
				processClassAliases(xStream,
						xstreamConfiguration.getxStreamClassAliases());
			}
			LOGGER.logMessage(LogLevel.INFO, "XStream配置文件[{0}]，加载完毕。",
					fileObject.getAbsolutePath());
		} catch (Exception e) {
			LOGGER.errorMessage(
					String.format("processing file <%s>",
							fileObject.getAbsolutePath()), e);
		}
	}

	private void processClassAliases(XStream xStream,
			XStreamClassAliases classAliases) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		if (classAliases.getClassAliases() != null) {
			for (XStreamClassAlias classAlias : classAliases.getClassAliases()) {
				Class<?> clazz = Class.forName(classAlias.getType());
				xStream.alias(classAlias.getAliasName(), clazz);
				processClassAlias(xStream, classAlias, clazz);
			}
		}
	}

	private void processClassAlias(XStream xStream,
			XStreamClassAlias classAlias, Class<?> clazz)
			throws InstantiationException, IllegalAccessException {
		if (classAlias.getProperAliases() != null) {
			processPropertyAlias(xStream, classAlias, clazz);
		}
		if (classAlias.getPropertyImplicits() != null) {
			processPropertyImplicit(xStream, classAlias, clazz);
		}
		if (classAlias.getPropertyOmits() != null) {
			processPropertyOmit(xStream, classAlias, clazz);
		}
	}

	private void processPropertyOmit(XStream xStream,
			XStreamClassAlias classAlias, Class<?> clazz) {
		for (XStreamPropertyOmit propertyOmit : classAlias.getPropertyOmits()) {
			xStream.omitField(clazz, propertyOmit.getAttributeName());
		}
	}

	private void processPropertyImplicit(XStream xStream,
			XStreamClassAlias classAlias, Class<?> clazz)
			throws InstantiationException, IllegalAccessException {
		for (XStreamPropertyImplicit propertyImplicit : classAlias
				.getPropertyImplicits()) {
			String name = propertyImplicit.getAttributeName();
			Object newInstance = clazz.newInstance();
			if (newInstance instanceof Array) {
				xStream.addImplicitArray(clazz, name);
			} else if (newInstance instanceof Collection) {
				xStream.addImplicitCollection(clazz, name);
			}
		}
	}

	private void processPropertyAlias(XStream xStream,
			XStreamClassAlias classAlias, Class<?> clazz) {
		for (XStreamPropertyAlias propertyAlias : classAlias.getProperAliases()) {
			xStream.aliasAttribute(clazz, propertyAlias.getAttributeName(),
					propertyAlias.getAliasName());
		}
	}

	private void loadAnnotationClass(XStream xStream,
			XStreamConfiguration xstreamConfiguration)
			throws ClassNotFoundException {
		if (xstreamConfiguration.getxStreamAnnotationClasses() != null) {
			for (XStreamAnnotationClass annotationClass : xstreamConfiguration
					.getxStreamAnnotationClasses()) {
				xStream.processAnnotations(Class.forName(annotationClass
						.getClassName()));
			}
		}
	}

	public void process() {
		for (FileObject fileObject : fileObjects) {
			process(fileObject);
		}

	}

	public void setFileResolver(FileResolver fileResolver) {
		//do nothing
	}

	
	public int getOrder() {
		return HIGHEST_PRECEDENCE+20;
	}



}
