package org.tinygroup.metadata.config.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by wangwy11342 on 2016/5/19.
 */
@XStreamAlias("dtos")
public class Dtos {
    @XStreamImplicit
    private List<Dto> DtoList;

    public List<Dto> getDtoList() {
        return DtoList;
    }

    public void setDtoList(List<Dto> dtoList) {
        DtoList = dtoList;
    }
}
