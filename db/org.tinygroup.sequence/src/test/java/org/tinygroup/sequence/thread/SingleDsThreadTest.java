package org.tinygroup.sequence.thread;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tinygroup.sequence.AbstractDBUnitTest;
import org.tinygroup.sequence.impl.DefaultSequence;
import org.tinygroup.sequence.impl.DefaultSequenceDao;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by wangwy11342 on 2016/6/21.
 * 单个数据源多线程情况下测试sequence
 */
public class SingleDsThreadTest extends AbstractDBUnitTest {
    //线程数
    private static final int THREAD_SIZE = 10;
    //单个线程获取next序列的次数
    private static final int NEXT_TIMES = 1005;

    private static final int STEP = 11;
    //产生sequence的工厂对象,所有线程共用一个工厂对象
    private DefaultSequence defaultSequence=new DefaultSequence();
    //id序列set容器
    private Set<Long> sequenceSet = new TreeSet<Long>();

    @Override
    protected List<String> getSchemaFiles() {
        return Collections.singletonList("integrate/schema/db_sequence1.sql");
    }

    @Override
    protected List<String> getDataSetFiles() {
        return Collections.singletonList("integrate/dataset/thread/single/init/db_sequence1.xml");
    }


    @Before
    public  void init(){
        DefaultSequenceDao sequenceDao=new DefaultSequenceDao();
        sequenceDao.setDataSource(createDataSourceList().get(0));
        sequenceDao.setStep(STEP);
        defaultSequence.setSequenceDao(sequenceDao);
        defaultSequence.setName("user");
    }

    @Test
    public void testOneDs() throws InterruptedException {
        for(int i=0;i<THREAD_SIZE;i++) {
            Thread t = new OperatorThread();
            t.start();
            t.join();
        }
        System.out.println(sequenceSet);
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
                    long temp =defaultSequence.nextValue();
                    sequenceSet.add(temp);
                }
            } catch (Exception e) {
                Assert.fail(e.getMessage());
            }
        }
    }
}
