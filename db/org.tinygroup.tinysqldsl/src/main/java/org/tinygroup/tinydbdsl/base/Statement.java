package org.tinygroup.tinydbdsl.base;

import java.util.List;

/**
 * Created by luoguo on 2015/3/11.
 */
public interface Statement {
    String sql();
    List<Object> getValues();
}
