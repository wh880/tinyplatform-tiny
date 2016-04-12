package org.tinygroup.uiengine;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.tinygroup.uiengine.config.UIComponent;
import org.tinygroup.uiengine.config.UIComponents;
import org.tinygroup.uiengine.manager.UIComponentManager;
import org.tinygroup.uiengine.manager.impl.UIComponentManagerImpl;

/**
 * UI组件排序类
 * @author yancheng11334
 *
 */
public class TestSort extends TestCase {

	private UIComponent u1;
	private UIComponent u2;
	private UIComponent u3;
	private UIComponent u4;
	private UIComponent u5;
	private UIComponent u6;
	
	UIComponentManager manager;
	
	protected void setUp(){
		u1 = new UIComponent();
		u1.setName("u1");
		u1.setDependencies("u2,u3");
		
		u2 = new UIComponent();
		u2.setName("u2");
		
		u3 = new UIComponent();
		u3.setName("u3");
		u3.setDependencies("u4");
		
		u4 = new UIComponent();
		u4.setName("u4");
		
		u5 = new UIComponent();
		u5.setName("u5");
		u5.setDependencies("u6");
		
		u6 = new UIComponent();
		u6.setName("u6");
	}
	
	private UIComponents createUIComponent(UIComponent... list){
		UIComponents components = new UIComponents();
		if(list!=null){
			ArrayList<UIComponent> newlist = new ArrayList<UIComponent>();
			for(UIComponent component:list){
				newlist.add(component);
			}
			components.setComponents(newlist);
		}
		return components;
	}
	
	/**
	 * 指定不同的顺序，计算完毕顺序固定
	 */
	public void testOrder(){
	    manager = new UIComponentManagerImpl();
		manager.addUIComponents(createUIComponent(u1,u2,u3,u4,u5,u6));
		manager.compute();
		
		assertEquals(u2.getName(), manager.getHealthUiComponents().get(0).getName());
		assertEquals(u4.getName(), manager.getHealthUiComponents().get(1).getName());
		assertEquals(u3.getName(), manager.getHealthUiComponents().get(2).getName());
		assertEquals(u1.getName(), manager.getHealthUiComponents().get(3).getName());
		assertEquals(u6.getName(), manager.getHealthUiComponents().get(4).getName());
		assertEquals(u5.getName(), manager.getHealthUiComponents().get(5).getName());
		
		manager = new UIComponentManagerImpl();
		manager.addUIComponents(createUIComponent(u6,u5,u4,u3,u2,u1));
		manager.compute();
		
		assertEquals(u2.getName(), manager.getHealthUiComponents().get(0).getName());
		assertEquals(u4.getName(), manager.getHealthUiComponents().get(1).getName());
		assertEquals(u3.getName(), manager.getHealthUiComponents().get(2).getName());
		assertEquals(u1.getName(), manager.getHealthUiComponents().get(3).getName());
		assertEquals(u6.getName(), manager.getHealthUiComponents().get(4).getName());
		assertEquals(u5.getName(), manager.getHealthUiComponents().get(5).getName());
	}
}
