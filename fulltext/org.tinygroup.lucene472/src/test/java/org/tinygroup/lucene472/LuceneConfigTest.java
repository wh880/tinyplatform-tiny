package org.tinygroup.lucene472;

import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.sqlindexsource.config.SqlConfigs;
import org.tinygroup.templateindex.config.BaseIndexConfig;
import org.tinygroup.tinyrunner.Runner;

public class LuceneConfigTest extends TestCase{

	private LuceneConfigManager luceneConfigManager;
	protected void setUp() throws Exception {
		super.setUp();
		Runner.init("application.xml",null);
		luceneConfigManager = BeanContainerFactory.getBeanContainer(this.getClass().getClassLoader()).getBean("luceneConfigManager");
	}
	
	public void testIndexInstall() throws Exception {
		assertNotNull(luceneConfigManager);
		
		List<BaseIndexConfig> list = luceneConfigManager.getIndexConfigList();
		assertEquals(1, list.size());
		
		//SQL
		BaseIndexConfig config = list.get(0);
		assertEquals(SqlConfigs.class,config.getClass());
		
		assertEquals("sqlConfigsIndexOperator",config.getBeanName());
		
		Set<String> fields = config.getQueryFields();
		assertEquals(5,fields.size());
	}
}
