/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.dynamicdatasource;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

/**
 * 动态数据源
 * 
 * @author chenjiao
 * 
 */
public class DynamicDataSource implements DataSource {

	public static final String DATASOURCE_NAME = "dynamicDataSource";
	private static final Logger log = LoggerFactory
			.getLogger(DynamicDataSource.class);
	private DataSource dataSource = null;

	private Method unwrapMethod = null;
	private Method isWrapperForMethod = null;
	private Map<Connection, ConnectionTrace> connectionMap=new HashMap<Connection, ConnectionTrace>();
	private byte[] synObject = new byte[0];

	public Connection getConnection() throws SQLException {
		Connection connection = getConnectionProxy(getDataSource());
		addConnectionTrace(connection);
		return connection;
	}

	private void addConnectionTrace(Connection connection) {
		synchronized (synObject) {
			StringBuffer buffer = new StringBuffer();
			// 添加调用栈信息
			StackTraceElement st[] = Thread.currentThread().getStackTrace();
			for (int i = 0; i < st.length; i++) {
				buffer.append(st[i]).append("; ");
			}
			ConnectionTrace trace = new ConnectionTrace(connection,
					new java.util.Date(), Thread.currentThread().getId(), Thread
							.currentThread().getName(), buffer.toString());
			connectionMap.put(connection, trace);
		}
	}

	/**
	 * 此方法不监控数据库连接信息
	 */
	public Connection getConnection(String username, String password)
			throws SQLException {
		DataSource targetDataSource=getDataSource();
		Connection connection = targetDataSource.getConnection(username, password);
		ConnectionInvocationHandler handler=new ConnectionInvocationHandler(connection, targetDataSource);
		Connection proxycConnection=(Connection) handler.getProxy();
		return proxycConnection;
	}
	
	public Collection<ConnectionTrace> getConnectionTraces(){
		synchronized (synObject) {
			return connectionMap.values();
		}
	}
	
	protected Connection getConnectionProxy(DataSource targetDataSource) throws SQLException {
		ConnectionInvocationHandler handler=new ConnectionInvocationHandler(targetDataSource.getConnection(), targetDataSource);
		Connection collection=(Connection) handler.getProxy();
		return collection;
	}

	public PrintWriter getLogWriter() throws SQLException {
		return getDataSource().getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return getDataSource().getLoginTimeout();
	}

	public void setLogWriter(PrintWriter printWriter) throws SQLException {
		getDataSource().setLogWriter(printWriter);
	}

	public void setLoginTimeout(int timeout) throws SQLException {
		getDataSource().setLoginTimeout(timeout);
	}

	public DataSource getDataSource(String dataSourceName) {
		log.logMessage(LogLevel.DEBUG, "数据源名:" + dataSourceName);
		if (dataSourceName == null || dataSourceName.equals("")) {
			return this.dataSource;
		}
		return (DataSource) BeanContainerFactory.getBeanContainer(
				this.getClass().getClassLoader()).getBean(dataSourceName);

	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		String sp = DataSourceInfo.getDataSource();
		return getDataSource(sp);
	}

	@SuppressWarnings("unchecked")
	public <T> T unwrap(Class<T> iface) throws SQLException {
		DataSource dataSource = getDataSource();
		if (unwrapMethod == null) {
			unwrapMethod = getMethodByMethodName("unwrap");
		}
		try {
			return (T) unwrapMethod.invoke(dataSource, iface);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Method getMethodByMethodName(String methodName) {
		Method[] methods = this.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		throw new RuntimeException(
				"in org.tinygroup.datasource.DynamicDataSource : java.sql.DataSource has no method named \""
						+ methodName + "\"");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		DataSource dataSource = getDataSource();
		try {
			if (isWrapperForMethod == null) {
				isWrapperForMethod = getMethodByMethodName("isWrapperFor");
			}
			return (Boolean) isWrapperForMethod.invoke(dataSource, iface);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	private class ConnectionInvocationHandler implements InvocationHandler{
		private final DataSource targetDataSource;

		private Connection target;

		private boolean closed = false;

		public ConnectionInvocationHandler(Connection target,DataSource targetDataSource) {
			this.targetDataSource = targetDataSource;
			this.target=target;
		}
		
		public Object getProxy() {  
	        return Proxy.newProxyInstance(ConnectionProxy.class.getClassLoader(),   
	        		new Class[] {ConnectionProxy.class}, this);  
	    }  

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// Invocation on ConnectionProxy interface coming in...

			if (method.getName().equals("equals")) {
				// Only considered as equal when proxies are identical.
				return (proxy == args[0] ? Boolean.TRUE : Boolean.FALSE);
			}
			else if (method.getName().equals("hashCode")) {
				// Use hashCode of Connection proxy.
				return new Integer(System.identityHashCode(proxy));
			}
			else if (method.getName().equals("toString")) {
				// Allow for differentiating between the proxy and the raw Connection.
				StringBuffer buf = new StringBuffer("proxy for target Connection ");
				if (this.target != null) {
					buf.append("[").append(this.target.toString()).append("]");
				}
				else {
					buf.append(" from DataSource [").append(this.targetDataSource).append("]");
				}
			}
			else if (method.getName().equals("close")) {
				// Handle close method: only close if not within a transaction.
				synchronized (synObject) {
				   connectionMap.remove(proxy);
				}
				this.closed = true;
			}

			if (this.target == null) {
				if (this.closed) {
					throw new SQLException("Connection handle already closed");
				}
				this.target=targetDataSource.getConnection();
			}
			Connection actualTarget = this.target;
			// Invoke method on target Connection.
			try {
				Object retVal = method.invoke(actualTarget, args);
				return retVal;
			}
			catch (InvocationTargetException ex) {
				throw ex.getTargetException();
			}
		}
	}

}
