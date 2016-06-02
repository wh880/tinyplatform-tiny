package org.tinygroup.database.config.view;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by wangwy11342 on 2016/5/26.
 */
@XStreamAlias("ref-view-ids")
public class RefViewIds {
    @XStreamImplicit(itemFieldName="ref-view-id")
    private List<String> refViewIdList;

    public List<String> getRefViewIdList() {
        return refViewIdList;
    }

    public void setRefViewIdList(List<String> refViewIdList) {
        this.refViewIdList = refViewIdList;
    }
}
