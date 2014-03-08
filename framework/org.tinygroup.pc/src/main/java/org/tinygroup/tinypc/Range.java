package org.tinygroup.tinypc;

/**
 * 数的范围，用于记录整数和长整数的范围
 * Created by luoguo on 14-3-3.
 */
public class Range<T> {
    /**
     * 起始数
     */
    private T start;
    /**
     * 结束数
     */
    private T end;

    public Range() {

    }

    public Range(T start, T end) {
        this.start = start;
        this.end = end;
    }

    public void setStart(T start) {
        this.start = start;
    }

    public void setEnd(T end) {
        this.end = end;
    }

    public T getStart() {
        return start;
    }

    public T getEnd() {
        return end;
    }

    public String toString() {
        return String.format("start:%s,end:%s", start, end);
    }
}