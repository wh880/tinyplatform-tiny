package org.tinygroup.dynamicdatasource;

import java.sql.Connection;

public interface ConnectionProxy extends Connection {

	Connection getTargetConnection();

}