package org.tinygroup.metadata.config.errorcode;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by wangwy11342 on 2016/5/19.
 * 错误码
 */
@XStreamAlias("error-code")
public class ErrorCodes {
    @XStreamAlias("package-name")
    @XStreamAsAttribute
    private String packageName;
    @XStreamImplicit
    private List<ErrorCode> errorCodeList;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<ErrorCode> getErrorCodeList() {
        return errorCodeList;
    }

    public void setErrorCodeList(List<ErrorCode> errorCodeList) {
        this.errorCodeList = errorCodeList;
    }
}
