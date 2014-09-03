package org.tinygroup.tinydb;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;


/**
 * 创建DbOperatorFactory
 * 
 * @author renhui
 * 
 */
public class DbOperatorFactoryBuilder {

	public DbOperatorFactory build(Reader reader) {
		return build(reader, null);
	}

	public DbOperatorFactory build(InputStream inputStream, String dataSource) {
		try {
			ConfigurationBuilder builder = new ConfigurationBuilder(
					inputStream, dataSource);
			return build(builder.parser());
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				// Intentionally ignore. Prefer previous error.
			}
		}

	}

	public DbOperatorFactory build(InputStream stream) {
		return build(stream, null);
	}

	public DbOperatorFactory build(Reader reader, String dataSource) {
		try {
			ConfigurationBuilder builder = new ConfigurationBuilder(reader,
					dataSource);
			return build(builder.parser());
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				// Intentionally ignore. Prefer previous error.
			}
		}
	}

	public DbOperatorFactory build(Configuration config) {
		return new DbOperatorFactory(config);
	}

}
