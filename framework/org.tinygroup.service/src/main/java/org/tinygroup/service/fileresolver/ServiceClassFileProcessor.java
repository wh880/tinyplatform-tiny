package org.tinygroup.service.fileresolver;

import org.tinygroup.fileresolver.ProcessorCallBack;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.fileresolver.impl.MultiThreadFileProcessor;
import org.tinygroup.service.exception.ServiceLoadException;
import org.tinygroup.service.loader.ServiceInterfaceServiceLoader;
import org.tinygroup.service.registry.ServiceRegistry;
import org.tinygroup.vfs.FileObject;

public class ServiceClassFileProcessor extends AbstractFileProcessor {

	private static final String SERVICE_CLASS_EXT_FILENAME = ".class";

	private ServiceInterfaceServiceLoader serviceLoader;
	
	private ServiceRegistry serviceRegistry;

	public ServiceInterfaceServiceLoader getServiceLoader() {
		return serviceLoader;
	}

	public void setServiceLoader(ServiceInterfaceServiceLoader serviceLoader) {
		this.serviceLoader = serviceLoader;
	}
	
	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(SERVICE_CLASS_EXT_FILENAME);
	}

	public void process() {
		MultiThreadFileProcessor.mutiProcessor(getFileResolver()
				.getFileProcessorThreadNumber(), "serviceinterface-muti",
				fileObjects, new ProcessorCallBack() {
					public void callBack(FileObject fileObject) {
						serviceLoader.addClassFileObject(fileObject,
								getFileResolver().getClassLoader());
					}
				});
		try {
			serviceLoader.loadService(serviceRegistry);
		} catch (ServiceLoadException e) {
			logger.errorMessage(e.getMessage(), e);
		}
	}

}
