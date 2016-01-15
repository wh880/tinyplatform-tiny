package org.tinygroup.assembly;

import java.util.List;

import org.springframework.context.ApplicationContextAware;

public interface AssemblyService<T>  extends ApplicationContextAware{
	/**
	 * 设置需要被排除的列表
	 * @param exclusions
	 */
	void setExclusions(List<T> exclusions);
	 /**
	  * 返回符合的候选列表
	  * @param requiredType
	  * @return
	  */
	 List<T> findParticipants(Class<T> requiredType);
	 
}
