package org.tinygroup.sequence.multi;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.tinygroup.sequence.AbstractDBUnitTest;
import org.tinygroup.sequence.impl.MultipleSequenceDao;
import org.tinygroup.sequence.impl.MultipleSequenceFactory;

/**
 * 多数据源序列号生成测试
 * @author renhui
 *
 */
public class MultipleSequenceTest extends AbstractDBUnitTest{
	
	@Test
	public void testSequence(){
		MultipleSequenceFactory sequenceFactory=new MultipleSequenceFactory();
		MultipleSequenceDao multipleSequenceDao=new MultipleSequenceDao();
		List<DataSource> dataSources=createDataSourceList();
		multipleSequenceDao.setDataSourceList(dataSources);
//		multipleSequenceDao.setAdjust(false);
//		multipleSequenceDao.init();
		sequenceFactory.setMultipleSequenceDao(multipleSequenceDao);
		sequenceFactory.init();
		try {
			for (int i = 0; i < 100; i++) {
				System.out.println(sequenceFactory.getNextValue("user"));
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Override
	protected List<String> getSchemaFiles() {
		return Arrays.asList(
                "integrate/schema/db_sequence1.sql", 
                "integrate/schema/db_sequence2.sql");
	}

	@Override
	protected List<String> getDataSetFiles() {
		return Arrays.asList(
                "integrate/dataset/multi/init/db_sequence1.xml", 
                "integrate/dataset/multi/init/db_sequence2.xml");
	}
	

}
