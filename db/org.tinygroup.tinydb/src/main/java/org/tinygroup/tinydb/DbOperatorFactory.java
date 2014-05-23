package org.tinygroup.tinydb;

import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinydb.operator.DBOperator;

/**
 * Created by luoguo on 2014/5/23.
 */
public final class DbOperatorFactory {
    private DbOperatorFactory() {
    }

    public static DBOperator getDBOperator(String beanType) {
        BeanOperatorManager beanOperatorManager = (BeanOperatorManager) SpringUtil.getBean(BeanOperatorManager.OPERATOR_MANAGER_BEAN);
        return beanOperatorManager.getDbOperator(beanType);
    }
}
