package org.tinygroup.metadata.config.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.tinygroup.metadata.config.BaseObject;

import java.util.List;

/**
 * Created by wangwy11342 on 2016/5/19.
 */
@XStreamAlias("dto")
public class Dto extends BaseObject{
    @XStreamAsAttribute
    @XStreamAlias("package-name")
    private String packageName;//包名

    @XStreamAsAttribute
    @XStreamAlias("inherit")
    private String inherit;//引用继承dto唯一标识

    @XStreamAlias("properties")
    private List<Property> propertyList;//基本类型属性

    @XStreamAlias("objectReferences")
    private List<ObjectReference> ObjectReferenceList;//嵌套对象属性

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getInherit() {
        return inherit;
    }

    public void setInherit(String inherit) {
        this.inherit = inherit;
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    public List<ObjectReference> getObjectReferenceList() {
        return ObjectReferenceList;
    }

    public void setObjectReferenceList(List<ObjectReference> objectReferenceList) {
        ObjectReferenceList = objectReferenceList;
    }
}
