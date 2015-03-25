package org.tinygroup.tinydbdsl.select;


/**
 * An offset clause in the form OFFSET offset or in the form OFFSET offset (ROW
 * | ROWS)
 */
public class Offset {

	private long offset;
	private boolean offsetJdbcParameter = false;
	private String offsetParam = null;

	public Offset(long offset, boolean offsetJdbcParameter, String offsetParam) {
		super();
		this.offset = offset;
		this.offsetJdbcParameter = offsetJdbcParameter;
		this.offsetParam = offsetParam;
	}

	public static Offset offsetRow(long offset) {
		return new Offset(offset, false, "ROW");
	}

	public Offset offsetRows(long offset) {
		return new Offset(offset, false, "ROWS");
	}

	public Offset offsetRowWithParam(long offset) {
		return new Offset(offset, true, "ROW");
	}

	public Offset offsetRowsWithParam(long offset) {
		return new Offset(offset, true, "ROWS");
	}

	public long getOffset() {
		return offset;
	}

	public String getOffsetParam() {
		return offsetParam;
	}

	public void setOffset(long l) {
		offset = l;
	}

	public void setOffsetParam(String s) {
		offsetParam = s;
	}

	public boolean isOffsetJdbcParameter() {
		return offsetJdbcParameter;
	}

	public void setOffsetJdbcParameter(boolean b) {
		offsetJdbcParameter = b;
	}

	@Override
	public String toString() {
		return " OFFSET " + (offsetJdbcParameter ? "?" : offset)
				+ (offsetParam != null ? " " + offsetParam : "");
	}
}
