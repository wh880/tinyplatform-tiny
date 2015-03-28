package org.tinygroup.tinysqldsl;

import java.util.List;

/**
 * dslsql执行操作接口
 *
 * @author renhui
 */
public interface DslSqlSession {

    int execute(Insert insert);

    int execute(Update update);

    int execute(Delete delete);

    <T> T fetchOneResult(Select select, Class<T> requiredType);

    <T> T[] fetchArray(Select select, Class<T> requiredType);

    <T> List<T> fetchList(Select select, Class<T> requiredType);

}
