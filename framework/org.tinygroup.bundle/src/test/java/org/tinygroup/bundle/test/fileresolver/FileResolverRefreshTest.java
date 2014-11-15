package org.tinygroup.bundle.test.fileresolver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.fileresolver.FileResolver;
import org.tinygroup.fileresolver.impl.FileResolverImpl;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springutil.SpringBeanContainer;
import org.tinygroup.springutil.fileresolver.SpringBeansFileProcessor;

public class FileResolverRefreshTest extends TestCase {
	private static Logger logger = LoggerFactory
			.getLogger(FileResolverRefreshTest.class);

	public void testScan() {
		BeanContainerFactory.setBeanContainer(SpringBeanContainer.class
				.getName());
		FileResolver resolver = new FileResolverImpl();
		resolver.addFileProcessor(new SpringBeansFileProcessor());
		String path = System.getProperty("user.dir") + File.separator
				+ "testJar" + File.separator
				+ "org.tinygroup.bundlejar-1.2.0-SNAPSHOT.jar";
		URL u = null;
		File f = new File(path);
		try {
			u = f.toURI().toURL();
		} catch (MalformedURLException e) {
			assertTrue(false);
		}

		ClassLoader loader = new URLClassLoader(new URL[] { u });
		resolver.setClassLoader(loader);
		resolver.addResolvePath(path);
		resolver.addIncludePathPattern("");
		resolver.resolve();
		try {
			BeanContainerFactory.getBeanContainer(
					this.getClass().getClassLoader()).getBean(
					"bundleTestService");
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}

		logger.logMessage(LogLevel.INFO, "==================================");
		resolver.removeResolvePath(path);
		resolver.refresh();
		try {
			BeanContainerFactory.getBeanContainer(
					this.getClass().getClassLoader()).getBean(
					"bundleTestService");
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}
}
