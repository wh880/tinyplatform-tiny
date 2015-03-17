package org.tinygroup.tinydbdsl;

import org.tinygroup.tinydbdsl.base.Condition;
import org.tinygroup.tinydbdsl.base.Field;
import org.tinygroup.tinydbdsl.base.Statement;
import org.tinygroup.tinydbdsl.base.Table;
import org.tinygroup.tinydbdsl.select.*;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by luoguo on 2015/3/11.
 */
public class Select<T> implements Statement {
    public static Select select(SelectField... fields) {
        return new Select();
    }

    public static Select selectFrom(Table... tables) {
        return new Select();
    }

    public Select from(Table... tables) {
        return this;
    }

    public Select where(Condition... conditions) {
        return this;
    }

    public Select page(int start, int limit) {
        return this;
    }

    public FieldStatistic count() {
        return new FieldStatistic(null, StatisticType.COUNT);
    }

    public Select orderBy(OrderField... orderFields) {
        return this;
    }

    public Select groupBy(Field... fields) {
        return this;
    }

    public Select join(Select select, JoinType joinType) {
        return this;
    }

    public Select join(Select select) {
        return this;
    }

    public Select union(Select select) {
        return this;
    }

    public Select unionAll(Select select) {
        return this;
    }

    public Select into(Table table) {
        return this;
    }

    public Select on(Condition... conditions) {
        return this;
    }

    public Select having(Condition... conditions) {
        return this;
    }

    public static Condition and(Condition... conditions) {
        return null;
    }

    public static Condition or(Condition... conditions) {
        return null;
    }

    public static Condition not(Condition... conditions) {
        return null;
    }

    public static FieldCustom customField(String format, Field... fields) {
        return new FieldCustom(format, fields);
    }

    public String sql() {
        return null;
    }

    public T fetchOne() {
        return null;
    }

    public T fetchFirst() {
        return null;
    }

    public T[] fetchArray() {
        return null;
    }

    public List<T> fetchList() {
        return null;
    }

    public ResultSet fetchResult() {
        return null;
    }
}
