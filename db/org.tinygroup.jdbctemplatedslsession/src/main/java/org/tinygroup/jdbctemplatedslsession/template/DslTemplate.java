package org.tinygroup.jdbctemplatedslsession.template;

import org.tinygroup.jdbctemplatedslsession.callback.*;
import org.tinygroup.tinysqldsl.DslSession;
import org.tinygroup.tinysqldsl.Pager;

import java.io.Serializable;
import java.util.List;

/**
 * dsl操作模板类
 * @author renhui
 *
 */
public interface DslTemplate {

	DslSession getDslSession();

	void setDslSession(DslSession dslSession);
    /**
     * 插入记录，主键值需要自己传人
     * @param t
     * @param callback
     * @return
     */
	<T> T insert(T t, InsertGenerateCallback<T> callback);
	
	/**
     * 插入记录操作，主键值可以自动生成
     * @param t
     * @param callback
     * @return
     */
	<T> T insertAndReturnKey(T t,
							 InsertGenerateCallback<T> callback);
	
    /**
     * 插入记录操作，主键值可以自动生成
     * @param autoGeneratedKeys
     * @param t
     * @param callback
     * @return
     */
	<T> T insertAndReturnKey(boolean autoGeneratedKeys, T t,
							 InsertGenerateCallback<T> callback);
	
	/**
	 * 更新操作,默认会忽略值为null的字段
	 * @param t
	 * @param callback
	 * @return
	 */
	<T> int update(T t, UpdateGenerateCallback<T> callback);

	/**
	 * 更新操作
	 * @param t
	 * @param callback
	 * @param ignore true:忽略更新值为null的字段
	 * @return
	 */
	<T> int update(T t, UpdateGenerateCallback<T> callback, boolean ignore);

	/**
	 * 根据主键删除记录
	 * @param pk
	 * @param callback
	 * @return
	 */
	int deleteByKey(Serializable pk, DeleteGenerateCallback<Serializable> callback);

	/**
	 * 根据主键查询记录
	 * @param pk
	 * @param callback
	 * @return
	 */
	<T> T getByKey(Serializable pk, Class<T> requiredType, SelectGenerateCallback<Serializable> callback);

	/**
	 * 根据主键数组删除记录
	 * @param callback
	 * @param pks
	 * @return
	 */
	int deleteByKeys(DeleteGenerateCallback<Serializable[]> callback,
					 Serializable... pks);
    /**
     * 查询操作
     * @param t
     * @param callback
     * @return
     */
	<T> List<T> query(T t, SelectGenerateCallback<T> callback);
    /**
     * 分页查询
     * @param start
     * @param limit
     * @param t
     * @param callback
     * @return
     */
	<T> Pager<T> queryPager(int start, int limit, T t, boolean isCursor,
							SelectGenerateCallback<T> callback);

	/**
	 * 批量新增，主键值需要设置到参数对象中
	 * @param objects
	 * @param callback
	 * @return
	 */
	<T> int[] batchInsert(List<T> objects, NoParamInsertGenerateCallback callback);

	/**
	 * 批量新增，主键值由框架生成
	 * @param autoGeneratedKeys
	 * @param objects
	 * @param callback
	 * @return
	 */
	<T> int[] batchInsert(boolean autoGeneratedKeys, List<T> objects,
						  NoParamInsertGenerateCallback callback);
    /**
     * 批量更改
	 * @param objects
	 * @param callback
     * @return
     */
	<T> int[] batchUpdate(List<T> objects, NoParamUpdateGenerateCallback callback);

	/**
	 * 批量删除
	 * @param objects
	 * @param callback
	 * @return
	 */
	<T> int[] batchDelete(List<T> objects, NoParamDeleteGenerateCallback callback);

}
