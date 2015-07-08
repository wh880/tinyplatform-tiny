/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.springmvc.coc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionDefaults;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.springmvc.support.AnnotationScopeMetadataResolver;
import org.tinygroup.springutil.PathMatchingInJarResourcePatternResolver;

/**
 * 自动注册符合合约规范的类到容器中
 * 
 * @author renhui
 * 
 */
public class ConventionBeanDefinitionRegistryPostProcessor extends
		ApplicationObjectSupport implements
		BeanDefinitionRegistryPostProcessor, InitializingBean {
	private static final Log logger = LogFactory
			.getLog(ConventionBeanDefinitionRegistryPostProcessor.class);

	private List<ConventionComponentIdentifier> conventionComponentIdentifierComposite;

	public void setConventionComponentIdentifierComposite(
			List<ConventionComponentIdentifier> conventionComponentIdentifierComposite) {
		this.conventionComponentIdentifierComposite = conventionComponentIdentifierComposite;
	}

	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}

	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		if (conventionComponentIdentifierComposite == null
				|| CollectionUtil
						.isEmpty(conventionComponentIdentifierComposite)) {
			return;
		}
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
		if (logger.isInfoEnabled()) {
			logger.info("detect " + count
					+ "s conventional components form web bundle  duration: "
					+ (System.currentTimeMillis() - start) + " ms");
		}
	}

	protected ClassPathBeanDefinitionScanner createScanner(
			BeanDefinitionRegistry registry) {
		ClassPathBeanDefinitionScanner scaner = new ClassPathBeanDefinitionScanner(
				registry, false);
		BeanDefinitionDefaults defaults=new BeanDefinitionDefaults();
		defaults.setAutowireMode(Autowire.BY_NAME.value());
		scaner.setBeanDefinitionDefaults(defaults);
		scaner.setScopeMetadataResolver(new AnnotationScopeMetadataResolver());
		scaner.setResourceLoader(new PathMatchingInJarResourcePatternResolver());
		scaner.setBeanNameGenerator(new DefaultBeanNameGenerator());
		addTypeFilters(scaner);
		return scaner;
	}

	private void addTypeFilters(ClassPathBeanDefinitionScanner scaner) {
		if (conventionComponentIdentifierComposite == null
				|| CollectionUtil
						.isEmpty(conventionComponentIdentifierComposite)) {
			return;
		}
		List<ConventionComponentIdentifier> identifiers = conventionComponentIdentifierComposite;
		if (CollectionUtil.isEmpty(identifiers)) {
			return;
		}
		for (final ConventionComponentIdentifier identifier : identifiers) {
			scaner.addIncludeFilter(new TypeFilter() {

				public boolean match(MetadataReader metadataReader,
						MetadataReaderFactory metadataReaderFactory)
						throws IOException {
					boolean match = identifier.isComponent(metadataReader
							.getClassMetadata().getClassName());
					if (match) {
						if (metadataReader.getAnnotationMetadata()
								.hasAnnotation(RequestMapping.class.getName())
								|| metadataReader.getAnnotationMetadata()
										.hasAnnotatedMethods(
												RequestMapping.class.getName())) {
							return false;
						}
					}
					return match;
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

}
