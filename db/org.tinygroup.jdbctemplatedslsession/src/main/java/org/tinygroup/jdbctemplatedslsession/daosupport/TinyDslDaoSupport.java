package org.tinygroup.jdbctemplatedslsession.daosupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.tinygroup.jdbctemplatedslsession.template.DslTemplate;
import org.tinygroup.jdbctemplatedslsession.template.DslTemplateImpl;
import org.tinygroup.tinysqldsl.DslSession;
import org.tinygroup.tinysqldsl.Select;
import org.tinygroup.tinysqldsl.select.OrderByElement;

import java.util.ArrayList;
import java.util.List;


/**
 * tiny DSL的dao基础类
 *
 * @author renhui
 */
public abstract class TinyDslDaoSupport extends DaoSupport {

    private DslTemplate dslTemplate;

    /**
     * 添加排序字段工具方法
     *
     * @param select
     * @param orderByArray
     * @return
     */
    public static Select addOrderByElements(Select select, OrderBy... orderByArray) {
        if (orderByArray == null || select == null) {
            return select;
        }
        List<OrderByElement> orderByElements = new ArrayList<OrderByElement>();
        for (int i = 0; orderByArray[i] != null && i < orderByArray.length; i++) {
            OrderByElement tempElement = orderByArray[i].getOrderByElement();
            if (tempElement != null) {
                orderByElements.add(tempElement);
            }
        }
        if (orderByElements.size() > 0) {
            select.orderBy(orderByElements.toArray(new OrderByElement[0]));
        }
        return select;
    }

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {
        if (this.dslTemplate == null) {
            throw new IllegalArgumentException("'DslSession' or 'DslTemplate' is required");
        }
    }

    protected DslTemplate createDslTemplate(DslSession dslSession) {
        return new DslTemplateImpl(dslSession);
    }

    public final DslTemplate getDslTemplate() {
        return dslTemplate;
    }

    @Autowired(required = false)
    public final void setDslTemplate(DslTemplate dslTemplate) {
        this.dslTemplate = dslTemplate;
    }

    public final DslSession getDslSession() {
        return dslTemplate != null ? dslTemplate.getDslSession() : null;
    }

    @Autowired(required = false)
    public final void setDslSession(DslSession dslSession) {
        if (this.dslTemplate == null || dslSession != this.dslTemplate.getDslSession()) {
            this.dslTemplate = createDslTemplate(dslSession);
        }
    }

}
