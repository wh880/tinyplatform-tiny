package org.tinygroup.tinydbdsl.base;

/**
 * Created by luoguo on 2015/3/11.
 */
public final class Operator {

    private final String operator;
    private static final Operator ZERO = new Operator("");
    public static final Operator eq = new Operator("=");
    public static final Operator between = new Operator("BETWEEN");
    public static final Operator neq = new Operator("<>");
    public static final Operator isNull = new Operator("IS NULL");
    public static final Operator isNotNull = new Operator("IS NOT NULL");
    public static final Operator gt = new Operator(">");
    public static final Operator lt = new Operator("<");
    public static final Operator gte = new Operator(">=");
    public static final Operator lte = new Operator("<=");
    public static final Operator exists = new Operator("EXISTS");
    public static final Operator like = new Operator("LIKE");
    public static final Operator leftLike= new Operator("LEFT_LIKE");
    public static final Operator rightLike = new Operator("RIGHT_LIKE");
    public static final Operator in = new Operator("IN");

    private Operator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}