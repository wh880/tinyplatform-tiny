package org.tinygroup.springmvc.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.fileresolver.FileResolver;
import org.tinygroup.fileresolver.FileResolverFactory;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 支持classpath内嵌jar格式下，拿取jar内存资源文件
 * 
 */
public class PathMatchingInJarResourcePatternResolver extends
		PathMatchingResourcePatternResolver {
	private static final Logger logger = LoggerFactory
			.getLogger(PathMatchingInJarResourcePatternResolver.class);

	public PathMatchingInJarResourcePatternResolver() {
		super();
	}

	public PathMatchingInJarResourcePatternResolver(ClassLoader classLoader) {
		super(classLoader);
	}

	public PathMatchingInJarResourcePatternResolver(
			ResourceLoader resourceLoader) {
		super(resourceLoader);
	}

	protected Set<Resource> doFindPathMatchingFileResources(
			Resource rootDirResource, String pattern) {
		File rootDir;
		try {
			rootDir = rootDirResource.getFile().getAbsoluteFile();
		} catch (IOException ex) {
			logger.logMessage(
					LogLevel.WARN,
					"Cannot search for matching files underneath "
							+ rootDirResource
							+ " because it does not correspond to a directory in the file system",
					ex);
			return Collections.emptySet();
		}
		if (!rootDir.exists()) {
			// Silently skip non-existing directories.
			logger.logMessage(LogLevel.DEBUG,
					"Skipping [" + rootDir.getAbsolutePath()
							+ "] because it does not exist");
			return Collections.emptySet();
		}
		if (!rootDir.isDirectory()) {
			// Complain louder if it exists but is no directory.
			logger.logMessage(LogLevel.WARN,
					"Skipping [" + rootDir.getAbsolutePath()
							+ "] because it does not denote a directory");
			return Collections.emptySet();
		}
		if (!rootDir.canRead()) {
			logger.logMessage(
					LogLevel.WARN,
					"Cannot search for matching files underneath directory ["
							+ rootDir.getAbsolutePath()
							+ "] because the application is not allowed to read the directory");
			return Collections.emptySet();
		}
		String fullPattern = StringUtils.replace(rootDir.getAbsolutePath(),
				File.separator, "/");
		if (!pattern.startsWith("/")) {
			fullPattern += "/";
		}
		String subPattern = StringUtils.replace(pattern, File.separator, "/");
		fullPattern = fullPattern + subPattern;
		Set<Resource> result = new LinkedHashSet<Resource>(8);
		try {
			doRetrieveMatchingFiles(fullPattern, subPattern, rootDir, result);
		} catch (IOException e) {
			logger.errorMessage(e.getMessage(), e);
		}
		return result;
	}

	private void doRetrieveMatchingFiles(String fullPattern, String subPattern,
			File dir, Set<Resource> result) throws IOException {
		logger.logMessage(LogLevel.DEBUG,
				"Searching directory [" + dir.getAbsolutePath()
						+ "] for files matching pattern [" + fullPattern + "]");
		File[] dirContents = dir.listFiles();
		if (dirContents == null) {
			logger.logMessage(
					LogLevel.WARN,
					"Could not retrieve contents of directory ["
							+ dir.getAbsolutePath() + "]");
			return;
		}
		for (File content : dirContents) {
			String currPath = StringUtils.replace(content.getAbsolutePath(),
					File.separator, "/");
			if (content.isDirectory()
					&& getPathMatcher().matchStart(fullPattern, currPath + "/")) {
				if (!content.canRead()) {
					logger.logMessage(
							LogLevel.DEBUG,
							"Skipping subdirectory ["
									+ dir.getAbsolutePath()
									+ "] because the application is not allowed to read the directory");
				} else {
					doRetrieveMatchingFiles(fullPattern, subPattern, content,
							result);
				}
			}

			// can accurately judge by : new JarFile(content);
			if (content.isFile() && content.getName().endsWith(".jar")) {
				// JarFile jarFile= new JarFile(content);
				URL jarFileUrl = new URL("jar:" + content.toURI() + "!/");
				logger.logMessage(LogLevel.INFO,
						"find match jar resource [{0}]", jarFileUrl.toString());
				Resource dirRes = new UrlResource(jarFileUrl);
				Set<Resource> resources = doFindPathMatchingJarResources(
						dirRes, subPattern);
				if (!CollectionUtil.isEmpty(resources)) {
					result.addAll(resources);
				}
			}

			if (getPathMatcher().match(fullPattern, currPath)) {
				result.add(new FileSystemResource(content));
			}
		}
	}

	/**
	 * Find all class location resources with the given location via the
	 * ClassLoader.
	 * 
	 * @param location
	 *            the absolute path within the classpath
	 * @return the result as Resource array
	 * @throws IOException
	 *             in case of I/O errors
	 * @see java.lang.ClassLoader#getResources
	 * @see #convertClassLoaderURL
	 */
	protected Resource[] findAllClassPathResources(String location)
			throws IOException {
		Set<Resource> result = new LinkedHashSet<Resource>(16);
		List<String> scanPaths = getScanPaths();
		for (String path : scanPaths) {
			File file = new File(path);
			if (file.exists()) {
				Resource resource = null;
				if (path.endsWith(".jar")) {
					URL jarFileUrl = new URL("jar:" + file.toURI() + "!/");
					logger.logMessage(LogLevel.INFO,
							"find match jar resource [{0}]",
							jarFileUrl.toString());
					resource = new UrlResource(jarFileUrl);
				} else {
					resource = new FileSystemResource(path);
				}
				result.add(resource);
			}
		}
		return result.toArray(new Resource[result.size()]);
	}

	public List<String> getScanPaths() {
		FileResolver fileResolver = FileResolverFactory.getFileResolver();
		return fileResolver.getScanningPaths();
	}
}
