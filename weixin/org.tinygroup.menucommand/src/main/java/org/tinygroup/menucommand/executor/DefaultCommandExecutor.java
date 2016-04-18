package org.tinygroup.menucommand.executor;

import org.tinygroup.context.Context;
import org.tinygroup.menucommand.CommandExecutor;
import org.tinygroup.menucommand.CommandHandler;
import org.tinygroup.menucommand.CommandResult;
import org.tinygroup.menucommand.MenuCommandConstants;
import org.tinygroup.menucommand.exception.MenuCommandException;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.TemplateRender;
import org.tinygroup.template.impl.TemplateContextDefault;

/**
 * 默认命令执行器
 * @author yancheng11334
 *
 */
public class DefaultCommandExecutor implements CommandExecutor{

	private TemplateRender templateRender;
	
	private CommandHandler commandHandler;
	
	public TemplateRender getTemplateRender() {
		return templateRender;
	}

	public void setTemplateRender(TemplateRender templateRender) {
		this.templateRender = templateRender;
	}

	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

	public void setCommandHandler(CommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}

	public CommandResult execute(Context context) {
		if(commandHandler!=null){
		   commandHandler.beforeExecute(context);
		}
		try{
			String path = getRenderPath(context);
			String message = render(path,context);
			return new CommandResult(message,getMenuId(context));
		} catch (TemplateException e) {
			throw new MenuCommandException("命令执行器渲染模板发生异常:",e);
		}finally{
			if(commandHandler!=null){
			   commandHandler.afterExecute(context);
			}
		}
	}

	protected String render(String path,Context context) throws TemplateException{
		TemplateContext templateContext = new TemplateContextDefault();
		templateContext.setParent(context);
		try{
			return templateRender.renderTemplateWithOutLayout(path, templateContext);
		}finally{
			templateContext.setParent(null);
		}
	}
	
	protected String getRenderPath(Context context){
		return MenuCommandConstants.getRenderPath(context);
	}
	
	protected String getMenuId(Context context){
		return context.get(MenuCommandConstants.GOTO_MENU_ID_NAME);
	}
}
