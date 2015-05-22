package org.tinygroup.tinysqldsl;

import java.io.Serializable;
import java.util.List;

/**
 * dslsql执行操作接口
 *
 * @author renhui
 */
public interface DslSession {
    /**
     * 执行Insert语句,返回值为受影响记录数
     *
     * @param insert
     * @return
     */
    int execute(Insert insert);
    
    /**
     * 执行Insert语句，返回值为自增长的主键值。注意不要在创建insert对象的时候，自行设置主键值
     *
     * @param insert
     * @return
     */
    Number executeAndReturnKey(Insert insert);


    /**
     * 执行更新语句
     *
     * @param update
     * @return
     */
    int execute(Update update);

    /**
     * 执行删除语句
     *
     * @param delete
     * @return
     */
    int execute(Delete delete);

    /**
     * 返回一个结果，既然是有多个结果也只返回第一个结果
     *
     * @param select
     * @param requiredType
     * @param <T>
     * @return
     */
    <T> T fetchOneResult(Select select, Class<T> requiredType);

    /**
     * 把所有的结果变成一个对象数组返回
     *
     * @param select
     * @param requiredType
     * @param <T>
     * @return
     */
    <T> T[] fetchArray(Select select, Class<T> requiredType);

    /**
     * 把所有的结果变成一个对象列表返回
     *
     * @param select
     * @param requiredType
     * @param <T>
     * @return
     */
    <T> List<T> fetchList(Select select, Class<T> requiredType);
    
    /**
     * 返回一个结果，既然是有多个结果也只返回第一个结果
     *
     * @param select
     * @param requiredType
     * @param <T>
     * @return
     */
    <T> T fetchOneResult(ComplexSelect complexSelect, Class<T> requiredType);

    
    /**
     * 把所有的结果变成一个对象数组返回
     *
     * @param select
     * @param requiredType
     * @param <T>
     * @return
     */
    <T> T[] fetchArray(ComplexSelect complexSelect, Class<T> requiredType);

    /**
     * 把所有的结果变成一个对象列表返回
     *
     * @param select
     * @param requiredType
     * @param <T>
     * @return
     */
    <T> List<T> fetchList(ComplexSelect complexSelect, Class<T> requiredType);

}
