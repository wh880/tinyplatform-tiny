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
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Created by luoguo on 2014/6/4.
 */
public class TemplateException extends Exception {
    private ParserRuleContext context;
    private String fileName;

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
        this.context = context;
        this.fileName = fileName;
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
        if (context != null) {
            String contextMsg = "\n路径:" + fileName
                    + "\n位置[" + context.getStart().getLine() + "行" + context.getStart().getCharPositionInLine() + "列]-[" + context.getStop().getLine() + "行"
                    + context.getStop().getCharPositionInLine() + "列]\n"
                    + "===================================================================\n"
                    + context.getText() + "\n===================================================================\n";
            if (message != null) {
                return message + "\n" + contextMsg;
            } else {
                return contextMsg;
            }
        }
        return message == null ? "\n路径:"+fileName : message;
    }

    public TemplateException(String msg) {
        super(msg);
        System.out.println();
    }

    public TemplateException(Exception e) {
        super(e);
    }

}
