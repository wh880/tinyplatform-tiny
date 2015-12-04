package org.tinygroup.custombeandefine.identifier.impl;


/**
 * 服务的约定类,包名以service结尾,类名以Service结尾的类
 * @author renhui
 *
 */
public class ConventionServiceIdentifier extends AbstractConventionIdentifier{

    protected String getHandlerType() {
        return "service";
    }

}
