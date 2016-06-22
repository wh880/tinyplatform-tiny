package org.tinygroup.sequence.multi;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.tinygroup.sequence.AbstractDBUnitTest;
import org.tinygroup.sequence.impl.MultipleSequenceDao;
import org.tinygroup.sequence.impl.MultipleSequenceFactory;
import org.tinygroup.sequence.impl.SequenceDataSourceHolder;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * 数据源连不上
 * Created by wangwy11342 on 2016/6/22.
 */
public class DsBrokenTest extends AbstractDBUnitTest{
    //id序列set容器
    private Set<Long> sequenceSet = new TreeSet<Long>();

    private MultipleSequenceFactory sequenceFactory;

    private List<SequenceDataSourceHolder> holders;
    @Override
    protected List<String> getSchemaFiles() {
        return Arrays.asList(
                "integrate/schema/db_sequence1.sql",
                "integrate/schema/db_sequence2.sql",
                "integrate/schema/db_sequence3.sql",
                "integrate/schema/db_sequence4.sql");
    }

    @Override
    protected List<String> getDataSetFiles() {
        return Arrays.asList(
                "integrate/dataset/multi/init/dsbroken/db_sequence1.xml",
                "integrate/dataset/multi/init/dsbroken/db_sequence2.xml",
                "integrate/dataset/multi/init/dsbroken/db_sequence3.xml",
                "integrate/dataset/multi/init/dsbroken/db_sequence4.xml");
    }

    @Before
    public void init(){
        sequenceFactory=new MultipleSequenceFactory();
        MultipleSequenceDao multipleSequenceDao=new MultipleSequenceDao();
        List<DataSource> dataSources=createDataSourceList();
        multipleSequenceDao.setDataSourceList(dataSources);
        sequenceFactory.setMultipleSequenceDao(multipleSequenceDao);
        //初始化的时候正常
        sequenceFactory.init();
        holders = multipleSequenceDao.getDataSourceList();
    }

    //测试数据库宕机的情况
    @Test
    public void testDown() throws SQLException {

        //mock连接忽然连不上
        DataSource mock = EasyMock.createMock(DataSource.class);
        EasyMock.expect(mock.getConnection()).andThrow(new SQLException("Connection timeout!!!")).anyTimes();
        SequenceDataSourceHolder mockedHolder = new SequenceDataSourceHolder(mock);

        SequenceDataSourceHolder originHolder = holders.get(1);//最初的holder
        holders.set(1, mockedHolder);
        EasyMock.replay(mock);
        try {
            for (int i = 0; i < 1000; i++) {
                long temp = sequenceFactory.getNextValue("user");
                sequenceSet.add(temp);
            }
            assertEquals(sequenceSet.size(),1000);

            sequenceSet = new TreeSet<Long>();
            holders.set(1, originHolder);
            for (int i = 0; i < 1000; i++) {
                long temp = sequenceFactory.getNextValue("user");
                sequenceSet.add(temp);
            }
            assertEquals(sequenceSet.size(),1000);
//            System.out.println(sequenceSet);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    //测试所有数据库都宕机的情况
    @Test
    public void testAllDown() throws SQLException {
        sequenceFactory.getMultipleSequenceDao().setRetryTimes(10);//设置重连次数
        //mock连接忽然连不上
        DataSource mock = EasyMock.createMock(DataSource.class);
        EasyMock.expect(mock.getConnection()).andThrow(new SQLException("Connection timeout!!!")).anyTimes();
        SequenceDataSourceHolder mockedHolder = new SequenceDataSourceHolder(mock);
        //4个数据源全部挂掉
        holders.set(0, mockedHolder);
        holders.set(1, mockedHolder);
        holders.set(2, mockedHolder);
        holders.set(3, mockedHolder);
        EasyMock.replay(mock);
        try {
              sequenceFactory.getNextValue("user");
            fail("无可用数据源应报错！");
        } catch (Exception e) {
            assertEquals("MultipleSequenceDao没有可用的数据源了,数据源个数dataSourceNum=4,重试次数retryTimes=10",
                    e.getMessage());
        }

    }
}
