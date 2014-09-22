package org.tinygroup.webservicetest;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class UserAdapter extends XmlAdapter<UserImpl, User> {
	//{
	public User unmarshal(UserImpl v) throws Exception {
		return v;
	}

	public UserImpl marshal(User v) throws Exception {
		if (v instanceof UserImpl)
			return (UserImpl) v;
		return new UserImpl(v.getName(), v.getAge());
	}

}
