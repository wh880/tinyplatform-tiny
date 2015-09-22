
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

	public T add(T t);

	public int edit(T t);

	public int deleteByKey(KeyType pk);

	public T getByKey(KeyType pk);

	public int deleteByKeys(KeyType... pks);

	public List<T> query(T t, OrderBy... orderArgs);

	public Pager<T> queryPager(int start, int limit, T t, OrderBy... orderArgs);

	public int[] batchInsert(List<T> objects);

	public int[] batchUpdate(List<T> objects);

	public int[] batchDelete(List<T> objects);

}
