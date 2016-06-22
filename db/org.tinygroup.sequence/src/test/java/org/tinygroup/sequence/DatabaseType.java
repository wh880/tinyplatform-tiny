package org.tinygroup.sequence;
/**
 * 
 * @author renhui
 *
 */
public enum DatabaseType {
    
    H2, MySQL;
    
    /**
     * 获取数据库类型枚举.
     * 
     * @param databaseProductName 数据库类型
     * @return 数据库类型枚举
     */
    public static DatabaseType valueFrom(final String databaseProductName) {
        try {
            return DatabaseType.valueOf(databaseProductName);
        } catch (final IllegalArgumentException ex) {
            throw new RuntimeException("Can not support database type:"+databaseProductName,ex);
        }
    }
}