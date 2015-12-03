package org.tinygroup.servicewrapper;

import java.util.List;

import org.tinygroup.servicewrapper.annotation.ServiceWrapper;

public interface GeneratorServiceIn {
	@ServiceWrapper(serviceId="serviceUserObject")
	ServiceUser userObject(ServiceUser user,ServiceOrg org);
	@ServiceWrapper(serviceId="serviceUserList")
	List<ServiceUser>  userList(ServiceUser user,List<ServiceUser> users);
	@ServiceWrapper(serviceId="serviceUserArray")
	ServiceUser[] userArray(ServiceUser[] users);
}
