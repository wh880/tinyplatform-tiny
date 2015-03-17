package org.tinygroup.tinydbdsl.select;

import org.tinygroup.tinydbdsl.base.Field;

/**
 * Created by luoguo on 2015/3/11.
 */
public class OrderField {
    Field field;
    OrderType orderType = OrderType.ASC;

    public OrderField(Field field) {
        this.field = field;
    }

    public OrderField(Field field, OrderType orderType) {
        this.field = field;
        this.orderType = orderType;
    }

    public void setField(Field field) {
        this.field = field;
    }


    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}
