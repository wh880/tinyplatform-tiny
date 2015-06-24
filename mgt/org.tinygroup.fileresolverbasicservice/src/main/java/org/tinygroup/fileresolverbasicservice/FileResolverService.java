package org.tinygroup.fileresolverbasicservice;

import org.tinygroup.fileresolver.FileProcessor;
import org.tinygroup.fileresolver.FileResolver;

import java.util.List;

public class FileResolverService {
	private FileResolver resolver;

	public List<FileProcessor> getFileProcessors() {
		return resolver.getFileProcessorList();
	}
	
	public int getFileProcessorCount(){
		return resolver.getFileProcessorList().size();
	}
	

	public FileResolver getResolver() {
		return resolver;
	}

	public void setResolver(FileResolver resolver) {
		this.resolver = resolver;
	}

}
