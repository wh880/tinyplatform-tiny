package org.tinygroup.metadata.config.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by wangwy11342 on 2016/5/19.
 * 嵌套对象属性
 */
@XStreamAlias("object-reference")
    public class ObjectReference {
    private String ref;

    private String isArray;

    private String collectionType;

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public void setIsArray(String isArray) {
        this.isArray = isArray;
    }

    public String getIsArray() {
        return isArray;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }
}
