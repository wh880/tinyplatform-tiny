package org.tinygroup.commons.cpu;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * 返回当前java进程占用的CPU情况
 *
 * @author luoguo
 */
public final class MonitorUtil {
    public static final int INTERVAL_TIME = 1000;
    static OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    static RuntimeMXBean runbean = (RuntimeMXBean) ManagementFactory.getRuntimeMXBean();
    static long beforeProcessTime = osbean.getProcessCpuTime();
    static long beforeUpTime = runbean.getUptime();
    static long lastGetTime = System.currentTimeMillis();
    static double cpuUage = 0;

    /*
    * 获取CPU利用率
    */
    public static double getCpuUsage() {
        return getCpuUsage(INTERVAL_TIME);
    }

    /**
     * 获取CPU利用率
     *
     * @param intervalTime 间隔时间,单位ms，如果没有超过间隔时间，则取上次取到的数据，如果有超过则重新计算
     * @return
     */
    public static double getCpuUsage(int intervalTime) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastGetTime >= intervalTime) {
            long afterProcessTime = osbean.getProcessCpuTime();
            long afterUpTime = runbean.getUptime();
            double cal = (afterProcessTime - beforeProcessTime) / ((afterUpTime - beforeUpTime) * 10000f);
            cpuUage = Math.min(100f, cal);
            beforeProcessTime = afterProcessTime;
            beforeUpTime = beforeUpTime;
            lastGetTime = currentTime;
        }
        return cpuUage;
    }
}
