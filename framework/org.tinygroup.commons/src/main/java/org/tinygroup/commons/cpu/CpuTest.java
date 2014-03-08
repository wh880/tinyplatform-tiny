package org.tinygroup.commons.cpu;

import com.sun.management.OperatingSystemMXBean;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by luoguo on 14-3-7.
 */
public class CpuTest {
    static void cpu() throws InterruptedException {
        com.sun.management.OperatingSystemMXBean opMXbean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Long start = System.currentTimeMillis();
        long startT = opMXbean.getProcessCpuTime();
        /**    Collect data every 5 seconds      */
        TimeUnit.SECONDS.sleep(1);
        Long end = System.currentTimeMillis();
        long endT = opMXbean.getProcessCpuTime();
        //end - start 即为当前采集的时间单元，单位ms
        //endT - startT 为当前时间单元内cpu使用的时间，单位为ns
        double ratio = (endT - startT) / 1000000.0 / (end - start) / opMXbean.getAvailableProcessors();
        System.out.println(ratio);
        //        System.out.println(opMXbean.getSystemLoadAverage());
    }

    static void print() throws IOException, InterruptedException {
        MBeanServerConnection mbsc = ManagementFactory.getPlatformMBeanServer();

        OperatingSystemMXBean osMBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        System.out.println(osMBean);
        long nanoBefore = System.nanoTime();
        long cpuBefore = osMBean.getProcessCpuTime();

        // Call an expensive task, or sleep if you are monitoring a remote process

        long cpuAfter = osMBean.getProcessCpuTime();
        long nanoAfter = System.nanoTime();
        Thread.sleep(10);
        long percent;
        if (nanoAfter > nanoBefore)
            percent = ((cpuAfter - cpuBefore) * 100L) / (nanoAfter - nanoBefore);
        else
            percent = 0;

        System.out.println("Cpu usage: " + percent + "%");
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        Map<String, Object> runtimeProperties = getValueMap(ManagementFactory.getRuntimeMXBean());
        Map<String, Object> operationProperties = getValueMap(ManagementFactory.getOperatingSystemMXBean());
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        while (true) {
            Thread.sleep(1000);
            for (long id : threadMXBean.getAllThreadIds()) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(id);
                System.out.println(threadMXBean.getThreadCpuTime(id));
            }
        }
        //                    print(runtimeProperties);
        //                    print(operationProperties);
    }

    private static void print(Map<String, Object> map) {
        System.out.println("==================================");
        for (String key : map.keySet()) {
            System.out.printf("%s:%s\n", key, map.get(key));
        }
        System.out.println("----------------------------------");
    }

    private static Map<String, Object> getValueMap(Object object) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Method method : object.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())) {
                Object value;
                try {
                    value = method.invoke(object);

                } catch (Exception e) {
                    value = e;
                } // try
                map.put(method.getName().substring(3), value);
            } // if
        } // for
        return map;
    }
}
