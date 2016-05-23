package org.tinygroup.metadata.config.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by wangwy11342 on 2016/5/19.
 */
@XStreamAlias("property")
public class Property {
    @XStreamAsAttribute
    @XStreamAlias("ref")
    private String ref;

    @XStreamAsAttribute
    @XStreamAlias("is-array")
    private String  isArray;

    @XStreamAsAttribute
    @XStreamAlias("collection-type")
    private String CollectionType;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getIsArray() {
        return isArray;
    }

    public void setIsArray(String isArray) {
        this.isArray = isArray;
    }

    public String getCollectionType() {
        return CollectionType;
    }

    public void setCollectionType(String collectionType) {
        CollectionType = collectionType;
    }
}
