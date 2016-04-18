package org.tinygroup.menucommand;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.menucommand.config.MenuConfig;
import org.tinygroup.menucommand.config.SystemCommand;
import org.tinygroup.tinytestutil.AbstractTestUtil;

/**
 * 菜单命令的接口和命令跳转测试
 * @author yancheng11334
 *
 */
public class MenuConfigTest extends TestCase{

	private MenuConfigManager menuConfigManager;
	
	protected void setUp() throws Exception {
		AbstractTestUtil.init(null, false);
		menuConfigManager = BeanContainerFactory.getBeanContainer(getClass().getClassLoader()).getBean("menuConfigManager");
	}
	
	/**
	 * 测试MenuConfigManager的基本功能
	 */
	public void testMenuConfigManager(){
		//测试MenuConfig
		assertNotNull(menuConfigManager);
		MenuConfig config = menuConfigManager.getMenuConfig("m001");
		assertNotNull(config);
		assertEquals("服务菜单", config.getName());
		assertEquals(true,config.match("menu"));
		assertEquals(true,config.match("菜单"));
		
		MenuConfig gameConfig = menuConfigManager.getMenuConfig("m012");
		assertEquals(gameConfig.getParent(), config);
		
		assertEquals(true,gameConfig.match("g"));
		assertEquals(true,gameConfig.match("game"));
		assertEquals(false,gameConfig.match("GAME"));
		assertNotNull(gameConfig.getMatchMenuConfig("buy"));
		
		
		
		//测试SystemCommand
		SystemCommand systemCommand = menuConfigManager.getSystemCommand("back");
		assertNotNull(systemCommand);
		assertEquals("返回上一级", systemCommand.getName());
		
		//测试MenuCommand
		MenuConfig guessNumberConfig = menuConfigManager.getMenuConfig("g001");
		
		assertEquals("服务菜单/游戏/猜数字",guessNumberConfig.getMenuConfigPath());
		
		assertNull(guessNumberConfig.getMatchMenuCommand("abc"));
		assertNotNull(guessNumberConfig.getMatchMenuCommand("new"));
		assertNotNull(guessNumberConfig.getMatchMenuCommand("100"));
	}
	
	/**
	 *  测试MenuConfigManager的高级功能：菜单调整和菜单命令等
	 */
	public void testMenuCommand(){
		//应用上下文
		Context context = new ContextImpl();
		CommandExecutor  executor = null;
		CommandResult result = null;
		
		//第一次访问
		executor = menuConfigManager.getCommandExecutor(null, "m",context);
		result = executor.execute(context);
		assertNotNull(result);
		assertEquals("m001", result.getMenuId());
		
		//访问不存在的指令
		executor = menuConfigManager.getCommandExecutor("m001", "奇怪指令",context);
		result = executor.execute(context);
		assertNotNull(result);
		assertEquals("m001", result.getMenuId());
		
		//访问游戏
		executor = menuConfigManager.getCommandExecutor("m001", "game",context);
		result = executor.execute(context);
		assertNotNull(result);
		assertEquals("m012", result.getMenuId());
		
		//猜数字
		executor = menuConfigManager.getCommandExecutor("m012", "guess",context);
		result = executor.execute(context);
		assertNotNull(result);
		assertEquals("g001", result.getMenuId());
		
		//回到上一级
		executor = menuConfigManager.getCommandExecutor("g001", "back",context);
		result = executor.execute(context);
		assertNotNull(result);
		assertEquals("m012", result.getMenuId());
		
		//回到首页
		executor = menuConfigManager.getCommandExecutor("g001", "home",context);
		result = executor.execute(context);
		assertNotNull(result);
		assertEquals("m001", result.getMenuId());
		
		//退出
		executor = menuConfigManager.getCommandExecutor("g001", "exit",context);
		result = executor.execute(context);
		assertNotNull(result);
		assertNull(result.getMenuId());
		
		//查询
		executor = menuConfigManager.getCommandExecutor("m023", "query 区",context);
		result = executor.execute(context);
		assertNotNull(result);
	}
	
	public void testShowTime(){
		
		//应用上下文
		Context context = new ContextImpl();
		CommandExecutor  executor = null;
		CommandResult result = null;
		
		//中式时间
		executor = menuConfigManager.getCommandExecutor("g003", "1",context);
		result = executor.execute(context);
		assertNotNull(result);
		
		//英式时间
		executor = menuConfigManager.getCommandExecutor("g003", "2",context);
		result = executor.execute(context);
		assertNotNull(result);
	}
	
	public void testGuessNumber(){
		//应用上下文
		Context context = new ContextImpl();
		CommandExecutor  executor = null;
		CommandResult result = null;
		
		context.put("userId", "user123");
		
		//测试新建
		executor = menuConfigManager.getCommandExecutor("g001", "new",context);
		result = executor.execute(context);
		assertNotNull(result);
		
		//测试范围越界
		executor = menuConfigManager.getCommandExecutor("g001", "2000",context);
		result = executor.execute(context);
		assertNotNull(result);
		
		//猜测第一次
		executor = menuConfigManager.getCommandExecutor("g001", "50",context);
		result = executor.execute(context);
		assertNotNull(result);
		
		//猜测第二次
		executor = menuConfigManager.getCommandExecutor("g001", "1",context);
		result = executor.execute(context);
		assertNotNull(result);
		
		//猜测第三次
		executor = menuConfigManager.getCommandExecutor("g001", "100",context);
		result = executor.execute(context);
		assertNotNull(result);
		
		//清理数据
		executor = menuConfigManager.getCommandExecutor("g001", "delete",context);
		result = executor.execute(context);
		assertNotNull(result);
		
	}
}
