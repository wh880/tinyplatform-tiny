package org.tinygroup.tinydb.dialect.impl;

public abstract class AbstractColumnDialcet extends AbstractDialect {

	/** The name of the column for this sequence */
	private String columnName;

	/** The number of keys buffered in a cache */
	private int cacheSize = 1;
	

	/**
	 * Set the name of the column in the sequence table.
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * Return the name of the column in the sequence table.
	 */
	public String getColumnName() {
		return this.columnName;
	}

	/**
	 * Set the number of buffered keys.
	 */
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	/**
	 * Return the number of buffered keys.
	 */
	public int getCacheSize() {
		return this.cacheSize;
	}
	
}
