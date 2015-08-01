package org.tinygroup.assembly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.tinygroup.commons.tools.CollectionUtil;

public class DefaultAssemblyService<T> extends ApplicationObjectSupport implements AssemblyService<T> {
	
	private List<T> exclusions=new ArrayList<T>();

	public void setExclusions(List<T> exclusions) {
        this.exclusions=exclusions;		
	}

	public List<T> findParticipants(Class<T> requiredType) {
		Map<String, T> map = BeanFactoryUtils.beansOfTypeIncludingAncestors(
				this.getListableBeanFactory(), requiredType);
		if (map.isEmpty()) {
			return null;
		}
		List<T> list = new ArrayList<T>(map.size());
		list.addAll(map.values());
		if(!CollectionUtil.isEmpty(exclusions)){
			for (T exclusion : exclusions) {
				list.remove(exclusion);
			}
		}
		return list;
	}
	
	private ListableBeanFactory getListableBeanFactory() {
		if (getApplicationContext() instanceof ConfigurableApplicationContext) {
			return ((ConfigurableApplicationContext) getApplicationContext())
					.getBeanFactory();
		}
		return getApplicationContext();
	}

}
