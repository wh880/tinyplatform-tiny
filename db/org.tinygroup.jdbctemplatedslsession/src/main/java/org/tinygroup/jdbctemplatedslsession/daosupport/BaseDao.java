
package org.tinygroup.jdbctemplatedslsession.daosupport;

import org.tinygroup.tinysqldsl.Pager;

import java.io.Serializable;
import java.util.List;

/**
 * 基础dao类
 * @author renhui
 *
 * @param <T>
 */
public interface BaseDao <T,KeyType extends Serializable>{

	T add(T t);

	int edit(T t);

	int deleteByKey(KeyType pk);

	T getByKey(KeyType pk);

	int deleteByKeys(KeyType... pks);

	List<T> query(T t, OrderBy... orderArgs);

	Pager<T> queryPager(int start, int limit, T t, OrderBy... orderArgs);

	int[] batchInsert(List<T> objects);

	int[] batchUpdate(List<T> objects);

	int[] batchDelete(List<T> objects);

}
