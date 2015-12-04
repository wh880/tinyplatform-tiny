package org.tinygroup.custombeandefine.convention;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionDefaults;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.custombeandefine.BeanDefineCreate;
import org.tinygroup.custombeandefine.identifier.ConventionComponentIdentifier;
import org.tinygroup.custombeandefine.scan.CustomBeanDefinitionScanner;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.springutil.PathMatchingInJarResourcePatternResolver;

/**
 * 自动注册符合合约规范的类到容器中
 * 
 * @author renhui
 * 
 */
public class ConventionCustomBeanDefinitionRegistryPostProcessor extends
		ApplicationObjectSupport implements BeanFactoryPostProcessor,
		InitializingBean {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConventionCustomBeanDefinitionRegistryPostProcessor.class);

	private List<ConventionComponentIdentifier> conventionComponentIdentifierComposite;
	
    private BeanDefineCreate beanDefineCreate;

	public BeanDefineCreate getBeanDefineCreate() {
		return beanDefineCreate;
	}

	public void setBeanDefineCreate(BeanDefineCreate beanDefineCreate) {
		this.beanDefineCreate = beanDefineCreate;
	}


	public void setConventionComponentIdentifierComposite(
			List<ConventionComponentIdentifier> conventionComponentIdentifierComposite) {
		this.conventionComponentIdentifierComposite = conventionComponentIdentifierComposite;
	}
	

	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if(beanFactory instanceof BeanDefinitionRegistry){
			postProcessBeanDefinitionRegistry((BeanDefinitionRegistry)beanFactory);
		}
	}

	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		List<ConventionComponentIdentifier> identifiers = conventionComponentIdentifierComposite;
		if (CollectionUtil.isEmpty(identifiers)) {
			return;
		}
		List<String> patterns = new ArrayList<String>();
		for (ConventionComponentIdentifier identifier : identifiers) {
			patterns.addAll(identifier.getPackagePatterns());
		}
		if (CollectionUtil.isEmpty(patterns)) {
			return;
		}
		// 扫描，排重，加入约定扫入的beanDefinition
		String[] patternArray = new String[patterns.size()];
		long start = System.currentTimeMillis();
		int count = this.createScanner(registry).scan(
				patterns.toArray(patternArray));
		LOGGER.logMessage(
				LogLevel.INFO,
				"detect {0} conventional components from  project  duration: {1}ms",
				count, (System.currentTimeMillis() - start));
	}

	protected ClassPathBeanDefinitionScanner createScanner(
			BeanDefinitionRegistry registry) {
		CustomBeanDefinitionScanner scaner = new CustomBeanDefinitionScanner(
				registry, false);
		scaner.setBeanDefineCreate(beanDefineCreate);
		BeanDefinitionDefaults defaults = new BeanDefinitionDefaults();
		defaults.setAutowireMode(Autowire.BY_NAME.value());
		scaner.setBeanDefinitionDefaults(defaults);
		scaner.setScopeMetadataResolver(new AnnotationScopeMetadataResolver());
		scaner.setResourceLoader(new PathMatchingInJarResourcePatternResolver());
		scaner.setBeanNameGenerator(new DefaultBeanNameGenerator());
		addTypeFilters(scaner);
		return scaner;
	}

	private void addTypeFilters(ClassPathBeanDefinitionScanner scaner) {
		List<ConventionComponentIdentifier> identifiers = conventionComponentIdentifierComposite;
		if (CollectionUtil.isEmpty(identifiers)) {
			return;
		}
		for (final ConventionComponentIdentifier identifier : identifiers) {
			scaner.addIncludeFilter(new TypeFilter() {

				public boolean match(MetadataReader metadataReader,
						MetadataReaderFactory metadataReaderFactory)
						throws IOException {
					return identifier.isComponent(metadataReader
							.getClassMetadata().getClassName());
				}

			});

		}

	}

	public void afterPropertiesSet() throws Exception {
		if (CollectionUtil.isEmpty(conventionComponentIdentifierComposite)) {
			Map<String, ConventionComponentIdentifier> map = BeanFactoryUtils
					.beansOfTypeIncludingAncestors(
							this.getListableBeanFactory(),
							ConventionComponentIdentifier.class);
			conventionComponentIdentifierComposite = new ArrayList<ConventionComponentIdentifier>();
			conventionComponentIdentifierComposite.addAll(map.values());
		}

	}

	private ListableBeanFactory getListableBeanFactory() {
		if (getApplicationContext() instanceof ConfigurableApplicationContext) {
			return ((ConfigurableApplicationContext) getApplicationContext())
					.getBeanFactory();
		}
		return getApplicationContext();
	}

	
	public static void main(String[] args) {
		
		GenericBeanDefinition beanDefinition=new GenericBeanDefinition();
		beanDefinition.setBeanClassName("org.springframework.aop.framework.ProxyFactoryBean");
//		
		
		
	}
}
