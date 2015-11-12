package org.tinygroup.template.interpret;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.Nullable;
import org.tinygroup.template.TemplateException;

/**
 * Created by luoguo on 15/8/11.
 */
public class TinyTemplateErrorListener implements org.antlr.v4.runtime.ANTLRErrorListener {
    private final String fileName;
    List<TemplateException> exceptions = new ArrayList<TemplateException>();
    
    public TinyTemplateErrorListener(String fileName){
        this.fileName=fileName;
    }
    
    public List<TemplateException> getTemplateException(){
    	return this.exceptions;
    }
    
    public void syntaxError(@NotNull Recognizer<?, ?> recognizer, @Nullable Object offendingSymbol, int line, int charPositionInLine, @NotNull String msg, @Nullable RecognitionException e) {
    	ParserRuleContext parserRuleContext = null;
    	if(e!=null){
    		RuleContext ruleContext=e.getCtx();
        	if(ruleContext!=null && ruleContext instanceof ParserRuleContext){
        		parserRuleContext = (ParserRuleContext)ruleContext;
        	}
    	}
    	TemplateException exception=null;
    	if(parserRuleContext!=null){
    		//通过RecognitionException获取错误信息
    		exception=new TemplateException(e,parserRuleContext,fileName);
    	}else{
    		//通过Recognizer获取错误信息
            StringBuilder sb = new StringBuilder(128);
            sb.append("Template parse failed.\n");
            sb.append(recognizer.getInputStream().getSourceName());
            sb.append(':');
            sb.append(line);
            sb.append(':');
            sb.append(charPositionInLine);
            sb.append("\nmessage: ");
            sb.append(msg);
            sb.append('\n');
            
    		exception=new TemplateException(sb.toString());
    		exception.setContext(null, fileName);
    		exception.setLine(line);
    		exception.setCharPositionInLine(charPositionInLine);
    	}
    	if (exception != null) {
    		exceptions.add(exception);
		}
    	
        
    }

    public void reportAmbiguity(@NotNull Parser parser, @NotNull DFA dfa, int i, int i1, boolean b, @Nullable BitSet bitSet, @NotNull ATNConfigSet atnConfigSet) {
        //exception=new TemplateException("",parser.getRuleContext(),fileName);
    }

    public void reportAttemptingFullContext(@NotNull Parser parser, @NotNull DFA dfa, int i, int i1, @Nullable BitSet bitSet, @NotNull ATNConfigSet atnConfigSet) {
//        exception=new TemplateException("",parser.getRuleContext(),fileName);
    }

    public void reportContextSensitivity(@NotNull Parser parser, @NotNull DFA dfa, int i, int i1, int i2, @NotNull ATNConfigSet atnConfigSet) {
//        exception=new TemplateException("",parser.getRuleContext(),fileName);
    }
}
