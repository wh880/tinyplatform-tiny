package org.tinygroup.sequence.single;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.tinygroup.sequence.AbstractDBUnitTest;
import org.tinygroup.sequence.impl.DefaultSequence;
import org.tinygroup.sequence.impl.DefaultSequenceDao;

/**
 * 单库序列号生成测试
 * @author renhui
 *
 */
public class SingleSequenceTest extends AbstractDBUnitTest{
	
	
	@Test
	public void testSingle(){
		DefaultSequenceDao sequenceDao=new DefaultSequenceDao();
		sequenceDao.setDataSource(createDataSourceList().get(0));
		sequenceDao.setStep(10);
		DefaultSequence defaultSequence=new DefaultSequence();
		defaultSequence.setSequenceDao(sequenceDao);
		defaultSequence.setName("user");
		for (int i = 0; i < 100; i++) {
			long actualValue=defaultSequence.nextValue();
			Assert.assertEquals(i+1, actualValue);
		}
	}

	@Override
	protected List<String> getSchemaFiles() {
		return Collections.singletonList("integrate/schema/db_sequence1.sql");
	}

	@Override
	protected List<String> getDataSetFiles() {
		return Collections.singletonList("integrate/dataset/single/init/db_sequence1.xml");
	}

}
