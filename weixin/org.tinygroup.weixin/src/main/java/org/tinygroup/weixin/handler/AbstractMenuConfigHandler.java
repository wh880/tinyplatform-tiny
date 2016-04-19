package org.tinygroup.weixin.handler;

import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.menucommand.CommandExecutor;
import org.tinygroup.menucommand.CommandResult;
import org.tinygroup.menucommand.MenuConfigManager;
import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinHandlerMode;
import org.tinygroup.weixin.WeiXinSession;
import org.tinygroup.weixin.WeiXinSessionManager;
import org.tinygroup.weixin.exception.WeiXinException;

/**
 * 微信菜单信息处理器
 * @author yancheng11334
 *
 */
public abstract class AbstractMenuConfigHandler extends AbstractWeiXinHandler{

	protected static final String MENU_ID_NAME ="menuId";
	
	private MenuConfigManager menuConfigManager;
	
	private WeiXinSessionManager weiXinSessionManager;
	
	private BeanContainer<?> beanContainer;
	
	public AbstractMenuConfigHandler(){
		setPriority(Integer.MIN_VALUE+300);
		beanContainer = BeanContainerFactory.getBeanContainer(getClass().getClassLoader());
	}
	
	public MenuConfigManager getMenuConfigManager() {
		return menuConfigManager;
	}

	public void setMenuConfigManager(MenuConfigManager menuConfigManager) {
		this.menuConfigManager = menuConfigManager;
	}

	public WeiXinSessionManager getWeiXinSessionManager() {
		if(weiXinSessionManager==null){
			try{
				weiXinSessionManager = beanContainer.getBean(WeiXinSessionManager.DEFAULT_BEAN_NAME); 
			}catch(Exception e){
			    throw new WeiXinException("实例化默认weiXinSessionManager失败:",e);
			}
		}
		return weiXinSessionManager;
	}

	public void setWeiXinSessionManager(WeiXinSessionManager weiXinSessionManager) {
		this.weiXinSessionManager = weiXinSessionManager;
	}

	public WeiXinHandlerMode getWeiXinHandlerMode() {
		return WeiXinHandlerMode.RECEIVE;
	}

	public <T> boolean isMatch(T message,WeiXinContext context){
    	//消息类型不匹配直接返回
		if(!isMatchType(message)){
		    return false;
		}
		return isMatchByMenuId(context.getWeiXinSession()) || isMatchMessage(getContent(message),context);
	}
	
	public <T> void process(T message,WeiXinContext context){
		WeiXinSession  session =  context.getWeiXinSession();
		if(session==null){
		   throw new WeiXinException("没有找到该消息对应的会话");
		}
		String menuId = session.getParameter(MENU_ID_NAME);
		String content = getContent(message);
		try{
			CommandExecutor executor = menuConfigManager.getCommandExecutor(menuId, content, context);
			CommandResult result = executor.execute(context);
			if(result!=null){
				context.setOutput(wrapperReplyMessage(message,result.getMessage()));
				//对比Session中的menuId是否变化，如果不一致更新Session
				if(!StringUtil.equals(menuId, result.getMenuId())){
					session.setParameter(MENU_ID_NAME, result.getMenuId());
					getWeiXinSessionManager().addWeiXinSession(session);
				}
			}
			
		}catch(Exception e){
			throw new WeiXinException("菜单信息处理器发生异常",e);
		}
		
	}
	
	/**
	 * 判断菜单Id是否存在于用户会话（适合菜单已经建立）
	 * @param session
	 * @return
	 */
	protected boolean isMatchByMenuId(WeiXinSession session){
		return session==null?false:session.contains(MENU_ID_NAME);
	}
	
	/**
	 * 根据消息内容判断是否存在匹配的菜单(适合菜单还未建立)
	 * @param content
	 * @return
	 */
	protected  boolean isMatchMessage(String content,WeiXinContext context){
		return (content!=null && menuConfigManager.getCommandExecutor(null, content, context)!=null)?true:false;
	}
	
	/**
	 * 判断消息类型是否一致
	 * @param <T>
	 * @param message
	 * @return
	 */
	protected abstract <T> boolean isMatchType(T message);
	
	/**
	 * 获得消息的文本内容
	 * @param <T>
	 * @param message
	 * @return
	 */
	protected abstract <T> String getContent(T message);
	
	/**
	 * 对响应消息进行包装并返回
	 * @param <T>
	 * @param <OUTPUT>
	 * @param message
	 * @param content
	 * @return
	 */
	protected abstract <T,OUTPUT> OUTPUT wrapperReplyMessage(T message,String content);
}
