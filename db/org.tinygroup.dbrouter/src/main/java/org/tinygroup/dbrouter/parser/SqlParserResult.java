package org.tinygroup.dbrouter.parser;

import java.util.List;

import org.tinygroup.dbrouter.parser.base.Condition;

/**
 * sqlparser解析后的结果.
 */
public interface SqlParserResult {

    /**
     * 获取当前表名,如果sql中包含多张表，默认只返回第一张表.
     * @return
     */
    String getTableName();

    /**
     * 获取order by 的信息
     * @return
     */
    List<OrderByColumn> getOrderByElements();

    /**
     * 获取group by 信息
     * @return
     */
    List<GroupByColumn> getGroupByElements();

    /**
     * insert/update/delete/select.
     * @return
     */
    boolean isDML();
    /**
     * create/drop/alter/truncate    
     * @return
     */
    boolean isDDL();
    
    /**
     * 获取sql的SKIP值如果有的话，没有的情况下会返回DEFAULT值
     * @return
     */
    long getSkip();


    /**
     * 获取sql的max值如果有的话，没有的话会返回DEFAULT值
     * @return
     */
    long getMax();

    /**
     * 是否是for update语句
     * @return
     */
    boolean isForUpdate();
    
    /**
     * 如果表名需要替换,返回表名替换后的SQL
     * @return
     */
    String getReplaceSql();
    
    /**
     * 返回sql语句中相关的参数信息
     * @return
     */
    List<Condition> getConditions();
    
}
