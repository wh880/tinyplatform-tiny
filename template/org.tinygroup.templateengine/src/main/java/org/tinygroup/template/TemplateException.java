/**
 * Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 * <p/>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.gnu.org/licenses/gpl.html
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.template;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.tinygroup.template.listener.Point;

/**
 * Created by luoguo on 2014/6/4.
 */
public class TemplateException extends Exception {
    private ParserRuleContext context;
    private String fileName;
    private String originalMsg;
    private boolean showUpperMessage = true;
    private List<Macro> macroList; //关联的宏列表信息
    /**
     * 异常语法块起点
     */
    private Point startPoint;
    /**
     * 异常语法块终点
     */
    private Point endPoint;

    public TemplateException() {

    }

    public TemplateException(Exception e, ParserRuleContext tree, String fileName) {
        super(e);
        this.fileName = fileName;
        this.context = tree;
    }
    
    public TemplateException(String msg) {
        super(msg);
    }

    public TemplateException(Exception e) {
        super(e);
    }
    
    public TemplateException(Throwable cause) {
        super(cause);
    }

    
    public Point getStartPoint() {
    	if(context!=null && context.getStart()!=null){
    	   return new Point(context.getStart().getLine() ,context.getStart().getCharPositionInLine());
    	}
		return startPoint;
	}

    public Point getEndPoint() {
    	if(context!=null && context.getStart()!=null){
    	   return new Point(context.getStop().getLine() ,context.getStop().getCharPositionInLine());
    	}
		return endPoint;
	}

    public String getOriginalMsg(){
    	return originalMsg;
    }
    
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * 获得模板文件的路径
	 * @return
	 */
	public String getFileName(){
		return this.fileName;
	}
	
	/**
	 * 获得模板错误的字符串
	 * @return
	 */
	public String getTemplateText(){
		return this.context==null?null:this.context.getText();
	}

    public ParserRuleContext getContext() {
        return context;
    }

    public void setContext(ParserRuleContext context, String fileName) {
        if(this.context==null) {
            this.context = context;
        }
        if(this.fileName==null) {
            this.fileName = fileName;
        }
    }

    public void setOriginalMsg(String originalMsg) {
		this.originalMsg = originalMsg;
	}

	public TemplateException(String msg, ParserRuleContext context, String fileName) {
        super(msg);
        this.context = context;
        this.fileName = fileName;
    }

    @Override
    public String getMessage() {
        String message = null;
    	if(showUpperMessage){
    	   message = super.getMessage();
    	}
    	//关联的宏信息
    	if(macroList!=null){
    	   StringBuffer sb = new StringBuffer();
    	   for(Macro macro:macroList){
    		   sb.append("宏文件路径:").append(macro.getMacroPath()).append(",宏名称:").append(macro.getName()).append("\n");
    	   }
    	   if(message!=null){
    		   message = message+sb.toString();
    	   }else{
    		   message = sb.toString();
    	   }
    	}
        String pathInfo = fileName==null?"":"\n路径:" + fileName;
        
        if (context != null) {
            String contextMsg = pathInfo
                    + "\n位置[" + context.getStart().getLine() + "," + context.getStart().getCharPositionInLine() + "]-[" + context.getStop().getLine() + ","
                    + context.getStop().getCharPositionInLine() + "]\n"
                    + "===================================================================\n"
                    + context.getText() + "\n===================================================================\n";
            if (message != null) {
                return message + "\n" + contextMsg;
            } else {
                return contextMsg;
            }
        }
        
        if(message==null){
        	return pathInfo;
        }else if(message.startsWith("\n路径:")){
        	return message;
        }else{
        	return pathInfo+" "+message;
        }
    }

	public boolean isShowUpperMessage() {
		return showUpperMessage;
	}

	public void setShowUpperMessage(boolean showUpperMessage) {
		this.showUpperMessage = showUpperMessage;
	}
	
	public void addMacro(Macro macro){
		if(macroList == null){
		   macroList = new ArrayList<Macro>();	
		}
		if(!macroList.contains(macro)){
		   macroList.add(macro);
		}
	}
    
}
