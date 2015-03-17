package org.tinygroup.tinydbdsl.select;

import org.tinygroup.tinydbdsl.base.Field;

/**
 * Created by luoguo on 2015/3/11.
 */
public class FieldStatistic extends SelectFieldImpl {
    private StatisticType statisticType = null;
    private Field field;

    public StatisticType getStatisticType() {
        return statisticType;
    }

    public Field getField() {
        return field;
    }

    public FieldStatistic(Field field, StatisticType statisticType) {
        this.field = field;
        this.statisticType = statisticType;
    }
}
