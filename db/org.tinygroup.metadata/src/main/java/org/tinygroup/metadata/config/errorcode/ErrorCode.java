package org.tinygroup.metadata.config.errorcode;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by wangwy11342 on 2016/5/19.
 * 错误码信息
 */
@XStreamAlias("error-code")
public class ErrorCode {
    @XStreamAsAttribute
    @XStreamAlias("id")
    private String id;

    @XStreamAsAttribute
    @XStreamAlias("code")
    private String code;

    @XStreamAsAttribute
    @XStreamAlias("short-description")
    private String shortDescription;

    @XStreamAsAttribute
    @XStreamAlias("long-description")
    private String longDescription;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
