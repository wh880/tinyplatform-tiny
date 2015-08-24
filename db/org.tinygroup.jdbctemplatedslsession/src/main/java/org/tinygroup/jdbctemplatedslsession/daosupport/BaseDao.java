
package org.tinygroup.jdbctemplatedslsession.daosupport;

import java.io.Serializable;
import java.util.List;

import org.tinygroup.tinysqldsl.Pager;

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

	public List<T> query(T t);

	public Pager<T> queryPager (int start ,int limit ,T t);

	public int[] batchInsert(List<T> objs);

	public int[] batchUpdate(List<T> objs);

	public int[] batchDelete(List<T> objs);

}
