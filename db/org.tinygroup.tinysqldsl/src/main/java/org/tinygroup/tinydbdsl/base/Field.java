package org.tinygroup.tinydbdsl.base;

import org.tinygroup.tinydbdsl.select.*;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Field extends SelectFieldImpl {
    private String name;
    private String alias;

    public Field(String name) {
        this.name = name;
    }

    public Field(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public Field as(String alias) {
        Field field = new Field(name, alias);
        field.alias = alias;
        return field;
    }

    public Value value(Object value) {
        return new Value(this, value);
    }

    public Value to(Object value) {
        return new Value(this, value);
    }

    public String getName() {
        return name;
    }

    public DistinctField distinct() {
        return new DistinctField(this);
    }

    public FieldCustom custom(String format) {
        return new FieldCustom(format, this);
    }

    public FieldStatistic sum() {
        return new FieldStatistic(this, StatisticType.SUM);
    }

    public FieldStatistic count() {
        return new FieldStatistic(this, StatisticType.COUNT);
    }

    public FieldStatistic avg() {
        return new FieldStatistic(this, StatisticType.AVG);
    }

    public FieldStatistic max() {
        return new FieldStatistic(this, StatisticType.MAX);
    }

    public FieldStatistic min() {
        return new FieldStatistic(this, StatisticType.MIN);
    }

    public String getAlias() {
        return alias;
    }

    public OrderField asc() {
        return new OrderField(this);
    }

    public OrderField desc() {
        return new OrderField(this, OrderType.DESC);
    }


}
