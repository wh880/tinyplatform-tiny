package org.tinygroup.fulltext;

import java.util.List;

/**
 * 分页对象
 * @author yancheng11334
 *
 * @param <T>
 */
public class Pager<T> {

	private List<T> records;//当前页的记录
    private int totalCount;//总记录数
    private int currentPage;//当前页
    private int limit;//每页记录数
    private int start;//开始记录数
    private int totalPages;//总页数

    public Pager(int totalCount, int start, List<T> records) {
        this(totalCount, start, 10, records);
    }

    public Pager(int totalCount, int start, int limit, List<T> records) {
    	super();
        this.records = records;
        init(totalCount, start, limit);
    }

    private void init(int totalCount, int start, int limit) {
        //设置基本参数
        this.totalCount = totalCount;
        int nLimit = limit <= 0 ? 10 : limit;
        //如果传入参数是小于0，那么设置为0
        int nStart = start < 0 ? 0 : start;
        this.limit = nLimit;
        this.start = nStart;
        this.totalPages = totalCount % nLimit == 0 ? totalCount / nLimit : totalCount / nLimit + 1;
        this.currentPage = nStart / nLimit == 0 ? 1 : nStart / nLimit + 1;
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }
    }

    public List<T> getRecords() {
        return records;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getLimit() {
        return limit;
    }

    public int getStart() {
        return start;
    }

    public int getTotalPages() {
        return totalPages;
    }

	@Override
	public String toString() {
		return "Pager [records=" + records + ", totalCount=" + totalCount
				+ ", currentPage=" + currentPage + ", limit=" + limit
				+ ", start=" + start + ", totalPages=" + totalPages + "]";
	}

}
