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
package org.tinygroup.template;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Created by luoguo on 2014/6/4.
 */
public class TemplateException extends Exception {
    private ParserRuleContext context;

    public TemplateException() {

    }

    public TemplateException(String msg, ParserRuleContext context) {
        super(msg);
        this.context = context;
    }
    public String getLocalizedMessage(){
        return getMessage();
    }
    public String getMessage() {
        String message = super.getMessage();
        if(context!=null){
            String contextMsg = "\n位置[" +context.getStart().getLine() + "," + context.getStart().getStartIndex() + "]-[" + context.getStop().getLine() + ","
                    + context.getStop().getStartIndex() + "]\n"
                    +"===================================================================\n"
                    +context.getText()+"\n===================================================================\n";
            if(message!=null){
                return message+"\n"+contextMsg;
            }else{
                return contextMsg;
            }
        }
        return message==null?"":message;
    }

    public TemplateException(String msg) {
        super(msg);
        System.out.println();
    }

    public TemplateException(Exception e) {
        super(e);
    }

}
