package org.tinygroup.sequence.thread;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tinygroup.sequence.AbstractDBUnitTest;
import org.tinygroup.sequence.impl.MultipleSequenceDao;
import org.tinygroup.sequence.impl.MultipleSequenceFactory;
import org.tinygroup.sequence.multi.MultipleSequenceTest;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created by wangwy11342 on 2016/6/20.
 */
public class MultiThreadTest extends AbstractDBUnitTest {
    //线程数
    private static final int THREAD_SIZE = 10;
    //单个线程获取next序列的次数
    private static final int NEXT_TIMES = 1005;
    //产生sequence的工厂对象,所有线程共用一个工厂对象
    private static MultipleSequenceFactory sequenceFactory;
    //id序列set容器
    private static Set<Long> sequenceSet = new TreeSet<Long>();

    @Override
    protected List<String> getSchemaFiles() {
        return Arrays.asList(
                "integrate/schema/db_sequence1.sql",
                "integrate/schema/db_sequence2.sql");
    }

    @Override
    protected List<String> getDataSetFiles() {
        return Arrays.asList(
                "integrate/dataset/thread/multi/init/db_sequence1.xml",
                "integrate/dataset/thread/multi/init/db_sequence2.xml");
    }

    @Before
    public  void initFactory(){
        sequenceFactory=new MultipleSequenceFactory();
        MultipleSequenceDao multipleSequenceDao=new MultipleSequenceDao();
        List<DataSource> dataSources=createDataSourceList();
        multipleSequenceDao.setDataSourceList(dataSources);
        multipleSequenceDao.init();
        sequenceFactory.setMultipleSequenceDao(multipleSequenceDao);
        sequenceFactory.init();
    }

    /**
     * 测试多线程下sequence序列没有重复数据
     */
    @Test
    public void testSequenceInThreads() throws InterruptedException {
        for(int i=0;i<THREAD_SIZE;i++) {
            Thread t = new OperatorThread();
            t.start();
            t.join();
        }
//        System.out.println(sequenceSet);
        //测试没有重复的id
        //THREAD_SIZE*NEXT_TIMES为获取id的总的次数
        //如果id不重复获取id数与序列set大小会相等
        Assert.assertEquals(THREAD_SIZE*NEXT_TIMES, sequenceSet.size());
    }

    class OperatorThread extends Thread{

        @Override
        public void run() {
            try {
                for (int i = 0; i < NEXT_TIMES; i++) {
                    long temp =sequenceFactory.getNextValue("user");
                    sequenceSet.add(temp);
                }
            } catch (Exception e) {
                Assert.fail(e.getMessage());
            }
        }
    }
}


