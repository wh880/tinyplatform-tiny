package org.tinygroup.tinydbdsl.select;


/**
 * A fetch clause in the form FETCH (FIRST | NEXT) row_count (ROW | ROWS) ONLY
 */
public class Fetch {

	private long rowCount;
	private boolean fetchJdbcParameter = false;
	private boolean isFetchParamFirst = false;
	private String fetchParam = "ROW";

	public Fetch(long rowCount, boolean fetchJdbcParameter,
			boolean isFetchParamFirst) {
		this(rowCount, fetchJdbcParameter, isFetchParamFirst, "ROW");
	}

	public Fetch(long rowCount, boolean fetchJdbcParameter,
			boolean isFetchParamFirst, String fetchParam) {
		super();
		this.rowCount = rowCount;
		this.fetchJdbcParameter = fetchJdbcParameter;
		this.isFetchParamFirst = isFetchParamFirst;
		this.fetchParam = fetchParam;
	}

	public Fetch fetchWithFirstRow(long rowCount) {
		return new Fetch(rowCount, false, true);
	}

	public Fetch fetchWithFirstRowParam(long rowCount) {
		return new Fetch(rowCount, true, true);
	}

	public Fetch fetchWithFirstRows(long rowCount) {
		return new Fetch(rowCount, false, true, "ROWS");
	}

	public Fetch fetchWithFirstRowsParam(long rowCount) {
		return new Fetch(rowCount, true, true, "ROWS");
	}

	public Fetch fetchWithNextRow(long rowCount) {
		return new Fetch(rowCount, false, false);
	}

	public Fetch fetchWithNextRowParam(long rowCount) {
		return new Fetch(rowCount, true, false);
	}

	public Fetch fetchWithNextRows(long rowCount) {
		return new Fetch(rowCount, false, false, "ROWS");
	}

	public Fetch fetchWithNextRowsParam(long rowCount) {
		return new Fetch(rowCount, true, false, "ROWS");
	}

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long l) {
		rowCount = l;
	}

	public boolean isFetchJdbcParameter() {
		return fetchJdbcParameter;
	}

	public String getFetchParam() {
		return fetchParam;
	}

	public boolean isFetchParamFirst() {
		return isFetchParamFirst;
	}

	public void setFetchJdbcParameter(boolean b) {
		fetchJdbcParameter = b;
	}

	public void setFetchParam(String s) {
		this.fetchParam = s;
	}

	public void setFetchParamFirst(boolean b) {
		this.isFetchParamFirst = b;
	}

	@Override
	public String toString() {
		return " FETCH " + (isFetchParamFirst ? "FIRST" : "NEXT") + " "
				+ (fetchJdbcParameter ? "?" : rowCount + "") + " " + fetchParam
				+ " ONLY";
	}
}
