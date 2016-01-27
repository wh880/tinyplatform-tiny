package org.tinygroup.aopcache.base;

/**
 * 缓存配置信息关联的元数据
 *
 * @author renhui
 */
public class CacheMetadata {

    private String keys;

    private String removeKeys;

    private String removeGroups;

    private long expire = Long.MAX_VALUE;//暂时无用

    private String parameterNames;

    private String group;

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getRemoveKeys() {
        return removeKeys;
    }

    public void setRemoveKeys(String removeKeys) {
        this.removeKeys = removeKeys;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getParameterNames() {
        return parameterNames;
    }

    public void setParameterNames(String parameterNames) {
        this.parameterNames = parameterNames;
    }

    public String getRemoveGroups() {
        return removeGroups;
    }

    public void setRemoveGroups(String removeGroups) {
        this.removeGroups = removeGroups;
    }

}
