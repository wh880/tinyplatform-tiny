package org.tinygroup.custombeandefine.scan;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.tinygroup.custombeandefine.BeanDefineCreate;

public class CustomBeanDefinitionScanner extends
		ClassPathBeanDefinitionScanner {

	private BeanDefineCreate beanDefineCreate;
	
	public BeanDefineCreate getBeanDefineCreate() {
		return beanDefineCreate;
	}

	public void setBeanDefineCreate(BeanDefineCreate beanDefineCreate) {
		this.beanDefineCreate = beanDefineCreate;
	}

	public CustomBeanDefinitionScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	public CustomBeanDefinitionScanner(BeanDefinitionRegistry registry,
			boolean useDefaultFilters) {
		super(registry, useDefaultFilters);
	}


	@Override
	public Set<BeanDefinition> findCandidateComponents(String basePackage) {
		Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
		try {
			String resourcePattern = DEFAULT_RESOURCE_PATTERN;
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ resolveBasePackage(basePackage) + "/" + resourcePattern;
			ResourcePatternResolver resourcePatternResolver = (ResourcePatternResolver) getResourceLoader();
			MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(
					resourcePatternResolver);
			Resource[] resources = resourcePatternResolver
					.getResources(packageSearchPath);
			boolean traceEnabled = logger.isTraceEnabled();
			boolean debugEnabled = logger.isDebugEnabled();
			for (int i = 0; i < resources.length; i++) {
				Resource resource = resources[i];
				if (traceEnabled) {
					logger.trace("Scanning " + resource);
				}
				if (resource.isReadable()) {
					MetadataReader metadataReader = metadataReaderFactory
							.getMetadataReader(resource);
					if (isCandidateComponent(metadataReader)) {
						ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) beanDefineCreate.createBeanDefinition(metadataReader);
						sbd.setResource(resource);
						sbd.setSource(resource);
						if (debugEnabled) {
							logger.debug("Identified candidate component class: "
									+ resource);
						}
						candidates.add(sbd);
					} else {
						if (traceEnabled) {
							logger.trace("Ignored because not matching any filter: "
									+ resource);
						}
					}
				} else {
					if (traceEnabled) {
						logger.trace("Ignored because not readable: "
								+ resource);
					}
				}
			}
		} catch (IOException ex) {
			throw new BeanDefinitionStoreException(
					"I/O failure during classpath scanning", ex);
		}
		return candidates;
	}

}
