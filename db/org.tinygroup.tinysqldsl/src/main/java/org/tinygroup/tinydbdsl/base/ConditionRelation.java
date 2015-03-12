package org.tinygroup.tinydbdsl.base;

/**
 * Created by luoguo on 2015/3/11.
 */
public class ConditionRelation {
    private Condition[] conditions;
    private ConnectType connectType = ConnectType.AND;

    public ConditionRelation(Condition[] conditions) {
        this.conditions = conditions;
    }

    public Condition[] getConditions() {
        return conditions;
    }


    public ConnectType getConnectType() {
        return connectType;
    }

}
