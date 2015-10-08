package org.tinygroup.cache.redis.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 连接池配置
 * @author yancheng11334
 *
 */
public class PoolConfig {

	/**
	 * 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
	 */
	@XStreamAsAttribute
	@XStreamAlias("block-when-exhausted")
	private String blockWhenExhausted;
	
	/**
	 * 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接数超过最大空闲时,或连接数超过最大空闲连接数)
	 */
	@XStreamAsAttribute
	@XStreamAlias("eviction-policy-classname")
	private String evictionPolicyClassName;
	
	/**
	 * 是否启用后进先出, 默认true
	 */
	@XStreamAsAttribute
	private String lifo;
	
	/**
	 * 连接池最大空闲连接数，默认8个
	 */
	@XStreamAsAttribute
	@XStreamAlias("max-idle")
	private String maxIdle;
	
	/**
	 * 连接池最大连接数，默认8个
	 */
	@XStreamAsAttribute
	@XStreamAlias("max-total")
	private String maxTotal;
	
	/**
	 * 连接池最小空闲连接数, 默认0
	 */
	@XStreamAsAttribute
	@XStreamAlias("min-idle")
	private String minIdle;
	
	/**
	 * 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
	 */
	@XStreamAsAttribute
	@XStreamAlias("max-wait-millis")
	private String maxWaitMillis;
	
	/**
	 * 逐出连接的最小空闲时间 ，默认60000毫秒(1分钟)
	 */
	@XStreamAsAttribute
	@XStreamAlias("min-evictable-idle-millis")
	private String minEvictableIdleTimeMillis;
	
	/**
	 * 每次逐出检查时，逐出的最大数目，默认是-1
	 */
	@XStreamAsAttribute
	@XStreamAlias("max-evictable-num")
	private String numTestsPerEvictionRun;
	
	/**
	 * 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略生效)
	 */
	@XStreamAsAttribute
	@XStreamAlias("min-evictable-soft-millis")
	private String softMinEvictableIdleTimeMillis;
	
	/**
	 * 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认30000ms
	 */
	@XStreamAsAttribute
	@XStreamAlias("evictable-period")
	private String timeBetweenEvictionRunsMillis;
	
	/**
	 * 在获取连接时检查有效性, 默认false
	 */
	@XStreamAsAttribute
	@XStreamAlias("test-on-borrow")
	private String testOnBorrow;
	
	/**
	 * 在创建连接时检查有效性，默认false
	 */
	@XStreamAsAttribute
	@XStreamAlias("test-on-create")
	private String testOnCreate;
	
	/**
	 * 在返回连接时检查有效性，默认false
	 */
	@XStreamAsAttribute
	@XStreamAlias("test-on-return")
	private String testOnReturn;
	
	/**
	 * 在空闲时检查有效性, 默认true
	 */
	@XStreamAsAttribute
	@XStreamAlias("test-while-idle")
	private String testWhileIdle;
	

	public String getBlockWhenExhausted() {
		return blockWhenExhausted;
	}

	public void setBlockWhenExhausted(String blockWhenExhausted) {
		this.blockWhenExhausted = blockWhenExhausted;
	}

	public String getEvictionPolicyClassName() {
		return evictionPolicyClassName;
	}

	public void setEvictionPolicyClassName(String evictionPolicyClassName) {
		this.evictionPolicyClassName = evictionPolicyClassName;
	}

	public String getLifo() {
		return lifo;
	}

	public void setLifo(String lifo) {
		this.lifo = lifo;
	}

	public String getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(String maxIdle) {
		this.maxIdle = maxIdle;
	}

	public String getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(String maxTotal) {
		this.maxTotal = maxTotal;
	}

	public String getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(String maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public String getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public String getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(String minIdle) {
		this.minIdle = minIdle;
	}

	public String getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(String numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public String getSoftMinEvictableIdleTimeMillis() {
		return softMinEvictableIdleTimeMillis;
	}

	public void setSoftMinEvictableIdleTimeMillis(
			String softMinEvictableIdleTimeMillis) {
		this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
	}

	public String getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(
			String timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public String getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(String testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public String getTestOnCreate() {
		return testOnCreate;
	}

	public void setTestOnCreate(String testOnCreate) {
		this.testOnCreate = testOnCreate;
	}

	public String getTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(String testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public String getTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(String testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	@Override
	public String toString() {
		return "PoolConfig [blockWhenExhausted=" + blockWhenExhausted
				+ ", evictionPolicyClassName=" + evictionPolicyClassName
				+ ", lifo=" + lifo + ", maxIdle=" + maxIdle + ", maxTotal="
				+ maxTotal + ", minIdle=" + minIdle + ", maxWaitMillis="
				+ maxWaitMillis + ", minEvictableIdleTimeMillis="
				+ minEvictableIdleTimeMillis + ", numTestsPerEvictionRun="
				+ numTestsPerEvictionRun + ", softMinEvictableIdleTimeMillis="
				+ softMinEvictableIdleTimeMillis
				+ ", timeBetweenEvictionRunsMillis="
				+ timeBetweenEvictionRunsMillis + ", testOnBorrow="
				+ testOnBorrow + ", testOnCreate=" + testOnCreate
				+ ", testOnReturn=" + testOnReturn + ", testWhileIdle="
				+ testWhileIdle + "]";
	}
	
}
