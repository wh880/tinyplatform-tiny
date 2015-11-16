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

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Created by luoguo on 2014/6/4.
 */
public class TemplateException extends Exception {
    private ParserRuleContext context;
    private String fileName;
    
    private int line; //仅供Eclipse插件解析调用
    private int charPositionInLine; //仅供Eclipse插件解析调用

    public int getLine() {
    	if(context!=null && context.getStart()!=null){
    	   return context.getStart().getLine();
    	}
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getCharPositionInLine() {
		if(context!=null && context.getStart()!=null){
	    	   return context.getStart().getCharPositionInLine();
	    }
		return charPositionInLine;
	}

	public void setCharPositionInLine(int charPositionInLine) {
		this.charPositionInLine = charPositionInLine;
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

	public TemplateException() {

    }

    public TemplateException(Exception e, ParserRuleContext tree, String fileName) {
        super(e);
        this.fileName = fileName;
        this.context = tree;
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

    public TemplateException(String msg, ParserRuleContext context, String fileName) {
        super(msg);
        this.context = context;
        this.fileName = fileName;
    }

    public String getLocalizedMessage() {
        return getMessage();
    }

    public String getMessage() {
        String message = super.getMessage();
        
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

    public TemplateException(String msg) {
        super(msg);
        System.out.println();
    }

    public TemplateException(Exception e) {
        super(e);
    }
    
    public TemplateException(Throwable cause) {
        super(cause);
    }

}
