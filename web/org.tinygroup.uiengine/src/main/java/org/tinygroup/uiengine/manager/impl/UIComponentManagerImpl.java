/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.uiengine.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.uiengine.config.UIComponent;
import org.tinygroup.uiengine.config.UIComponents;
import org.tinygroup.uiengine.manager.UIComponentManager;

public class UIComponentManagerImpl implements UIComponentManager {
    static Logger logger = LoggerFactory.getLogger(UIComponentManagerImpl.class);
    private List<UIComponent> uiComponentList = new ArrayList<UIComponent>();
    List<UIComponent> healthyComponentList = new ArrayList<UIComponent>();  //健康组件
    List<UIComponent> oldHealthyComponentList = new ArrayList<UIComponent>(); //临时保存的健康组件
    private volatile boolean  calculating; //是否计算中
    private volatile boolean  modified ; //组件是否被修改
    private Map<String, UIComponent> uiMap = new HashMap<String, UIComponent>();
    private UIComponentComparator comparator = new UIComponentComparator();//UI组件排序

    public List<UIComponent> getUiComponents() {
        return uiComponentList;
    }

    public List<UIComponent> getHealthUiComponents() {
        return calculating?oldHealthyComponentList:healthyComponentList;
    }

    public void addUIComponents(UIComponents uiComponents) {
        for (UIComponent component : uiComponents.getComponents()) {
            addUiComponent(component);
        }
    }

    private void addUiComponent(UIComponent component) {
        if (uiMap.get(component.getName()) == null) {
            uiComponentList.add(component);
            uiMap.put(component.getName(), component);
            modified = true;
        }
    }

    public boolean isHealth(String name) {
        UIComponent component = getUIComponent(name);
        if (component == null) {
            throw new RuntimeException("找不到UI组件包：" + name);
        }
        if (component.isComputed()) {// 如果已经计算好，则直接返回
        	if(!healthyComponentList.contains(component)){
        		healthyComponentList.add(component);
        	}
            return component.isHealth();
        } else {
            // 如果不依赖，则健康
            if (component.getDependencies() == null || component.getDependencies().trim().length() == 0) {
                component.setComputed(true);
                component.setHealth(true);
                if(!healthyComponentList.contains(component)){
            		healthyComponentList.add(component);
            	}
                return true;
            } else {
                String[] dependencies = component.getDependencies().split(",");
                for (String dependencyName : dependencies) {
                    if (!isHealth(dependencyName)) {
                        logger.logMessage(LogLevel.ERROR, "UI包<{}>依赖的<{}>UI包，不能被找到，因此不会被加载！依赖此包的应用可能不会被顺序执行。", name, dependencyName);
                        component.setComputed(true);
                        component.setHealth(false);
                        return false;
                    }
                }
                component.setComputed(true);
                component.setHealth(true);
                if(!healthyComponentList.contains(component)){
            		healthyComponentList.add(component);
            	}
                return true;
            }
        }
    }

    public UIComponent getUIComponent(String name) {
        return uiMap.get(name);
    }

    public String[] getComponentJsArray(UIComponent component) {
        String path = component.getJsResource();
        if (path != null && path.trim().length() > 0) {
            String[] paths = path.trim().split(",");
            for (int i = 0; i < paths.length; i++) {
                paths[i] = paths[i].trim();
            }
            return paths;
        }
        return null;
    }

    public String[] getComponentCssArray(UIComponent component) {
        String path = component.getCssResource();
        if (path != null && path.trim().length() > 0) {
            String[] paths = path.trim().split(",");
            for (int i = 0; i < paths.length; i++) {
                paths[i] = paths[i].trim();
            }
            return paths;
        }
        return null;
    }

    public void removeUIComponents(UIComponents uiComponents) {
        uiComponentList.removeAll(uiComponents.getComponents());
        for (UIComponent component : uiComponents.getComponents()) {
            uiMap.remove(component.getName());
            healthyComponentList.remove(component);
            modified = true;
        }
    }

	public void compute() {
		//只有组件被修改，同时计算已经完成
		if(modified  && !calculating){
			realCompute();
		}
	}
	
	private void realCompute(){
		calculating = true;
		//计算期间，访问旧的计算结果。
		oldHealthyComponentList = healthyComponentList;
		healthyComponentList = new ArrayList<UIComponent>();
		try{
			for (UIComponent component : uiComponentList) {
				this.isHealth(component.getName());		            
	        }
			//排序，固定渲染顺序
			Collections.sort(healthyComponentList,comparator);
		}finally{
			//计算结束，重置计算状态
			calculating = false;
			modified = false;
			oldHealthyComponentList.clear();
		}
	}

	public void reset() {
		healthyComponentList.clear();
	}
	
	/**
	 * UI组件的排序算法
	 * @author yancheng11334
	 *
	 */
	class UIComponentComparator implements Comparator<UIComponent>{

		/**
		 * UI组件关系为：前者依赖后者;后者依赖前者;两者无关系
		 */
		public int compare(UIComponent o1, UIComponent o2) {
			if(isDependent(o1,o2)){
			   return 1;
			}else if(isDependent(o2,o1)){
			   return -1;
			}else{
			   return compareByName(o1,o2);
			}
		}
		
		/**
		 * 按组件名称进行排序
		 * @param o1
		 * @param o2
		 * @return
		 */
		private int compareByName(UIComponent o1, UIComponent o2){
			int v1 = getNameProtity(o1.getName());
			int v2 = getNameProtity(o2.getName());
			if(v1>v2){
				return 1;
			}else if(v1<v2){
				return -1;
			}else{
				return 0;
			}
		}
		
		private int getNameProtity(String name){
			//简单采用hashcode做优先级
			return name.hashCode();
		}
		
		/**
		 * 前者是否继承依赖后者
		 * @param o1
		 * @param o2
		 * @return
		 */
		private boolean isDependent(UIComponent o1, UIComponent o2) {
			//前者没有依赖关系,直接判断false
			if(StringUtil.isEmpty(o1.getDependencies())){
				return false;
			}else{
				 Queue<String> queue = new LinkedList<String>();
				 QueryResult result = new QueryResult(o2.getName());
				 String[] dependencies = o1.getDependencies().split(",");
				 for(String dependency:dependencies){
					 //执行依赖关系搜索
					 query(queue,dependency,result);
				 }
				 return result.isFind();
			}
		}
		
		/**
		 * 查询依赖关系
		 * @param queue
		 * @param name
		 * @param result
		 */
		private void query(Queue<String> queue,String name,QueryResult result){
			//已经匹配，直接返回
			if(result.isFind()){
			   return ;
			}else{
				//入队操作
				if(name!=null){
					//如果找到目标，直接返回
					if(result.getTarget().equals(name)){
						result.setFind(true);
				    	return ;
					}else{
						 //往队列添加不重复的元素
						 if(!queue.contains(name)){
				    	     queue.add(name);
				    	 }
					}
				}
				
				//队列为空，终止查询并返回
				if(queue.isEmpty()){
				   return ;
				}
				
				//出队操作
				String element = queue.poll();
				UIComponent component = uiMap.get(element);
				if(!StringUtil.isEmpty(component.getDependencies())){
				    String[] dependencies = component.getDependencies().split(",");
				    for(String dependency:dependencies){
				    	//递归调用
				    	query(queue,dependency,result);
				    }
				}
			}
		}
		
	}
	
	/**
	 * 查询结果
	 * @author yancheng11334
	 *
	 */
	class QueryResult {
		private String target; //目标依赖
		private boolean find;  //是否找到
		public QueryResult(String target){
			this.target = target;
		}
		public String getTarget() {
			return target;
		}
		public boolean isFind() {
			return find;
		}
		public void setFind(boolean find) {
			this.find = find;
		}
		
	}
}
