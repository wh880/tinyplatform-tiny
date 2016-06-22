package org.tinygroup.jdbctemplatedslsession.util;

import org.tinygroup.commons.tools.ArrayUtil;
import org.tinygroup.jdbctemplatedslsession.daosupport.OrderBy;
import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.select.OrderByElement;

import java.util.ArrayList;
import java.util.List;

/**
 * DSL工具类
 * Created by Hulk on 2016/2/2.
 */
public class TinyDSLUtil {
    /**
     * Utility classes should not have public constructors
     */
    private TinyDSLUtil() {
    }

    /**
     * 添加排序字段工具方法
     *
     * @param select
     * @param orderByArray
     * @return
     */
    public static Select addOrderByElements(Select select, OrderBy... orderByArray) {
        if (ArrayUtil.isEmptyArray(orderByArray) || select == null) {
            return select;
        }
        List<OrderByElement> orderByElements = new ArrayList<OrderByElement>();
        for (int i = 0, len = orderByArray.length; i < len && orderByArray[i] != null; i++) {
            OrderByElement tempElement = orderByArray[i].getOrderByElement();
            if (tempElement != null) {
                orderByElements.add(tempElement);
            }
        }
        if (!orderByElements.isEmpty()) {
            select.orderBy(orderByElements.toArray(new OrderByElement[0]));
        }
        return select;
    }

}
