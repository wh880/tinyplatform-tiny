package org.tinygroup.sequence.exception;

import org.junit.Before;
import org.junit.Test;
import org.tinygroup.sequence.AbstractDBUnitTest;
import org.tinygroup.sequence.impl.MultipleSequenceDao;
import org.tinygroup.sequence.impl.MultipleSequenceFactory;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by wangwy11342 on 2016/6/21.
 */
public class NextValueExceptionTest extends AbstractDBUnitTest {
    //产生sequence的工厂对象
    private static MultipleSequenceFactory sequenceFactory;

    @Override
    protected List<String> getSchemaFiles() {
        return Arrays.asList("integrate/schema/db_sequence1.sql",
                "integrate/schema/db_sequence2.sql");
    }

    @Override
    protected List<String> getDataSetFiles() {
        return Arrays.asList("integrate/dataset/exception/init/db_sequence1.xml",
                "integrate/dataset/exception/init/db_sequence2.xml");
    }

    @Before
    public void init(){
        sequenceFactory=new MultipleSequenceFactory();
        MultipleSequenceDao multipleSequenceDao=new MultipleSequenceDao();
        List<DataSource> dataSources=createDataSourceList();
        multipleSequenceDao.setDataSourceList(dataSources);
        sequenceFactory.setMultipleSequenceDao(multipleSequenceDao);
    }

    //未初始化异常
    @Test
    public void testNotInit() throws Exception {
        try {
            sequenceFactory.getNextValue("user");
            fail("未初始化，应抛异常");
        }catch (SequenceException se){
            assertEquals("ERROR ## please init the MultipleSequenceDao first",se.getMessage());
        }
    }

    //序列名称为空、不存在异常
    @Test
    public void testSeqName() throws Exception {
        sequenceFactory.init();
        try {
            sequenceFactory.getNextValue(null);
            fail("seq参数获取为空应异常");
        } catch (IllegalArgumentException e) {
            assertEquals("The sequence name can not be null!",e.getMessage());
        }
        try {
            sequenceFactory.getNextValue("");
            fail("seq参数获取为空应异常");
        } catch (IllegalArgumentException e) {
            assertEquals("The sequence name can not be null!",e.getMessage());
        }

        try {
            sequenceFactory.getNextValue("user1");
            fail("seq参数名称不存在应异常");
        } catch (SequenceException e) {
            assertEquals("can not find the record, name=user1",e.getMessage());
        }
    }
}
