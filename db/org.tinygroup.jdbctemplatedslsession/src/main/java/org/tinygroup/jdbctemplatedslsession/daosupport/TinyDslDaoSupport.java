package org.tinygroup.jdbctemplatedslsession.daosupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.tinygroup.jdbctemplatedslsession.template.DslTemplate;
import org.tinygroup.jdbctemplatedslsession.template.DslTemplateImpl;
import org.tinygroup.tinysqldsl.DslSession;


/**
 * tiny DSL的dao基础类
 *
 * @author renhui
 */
public abstract class TinyDslDaoSupport extends DaoSupport {

    private DslTemplate dslTemplate;

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
