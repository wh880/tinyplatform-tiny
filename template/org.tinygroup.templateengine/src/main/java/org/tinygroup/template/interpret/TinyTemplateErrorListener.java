package org.tinygroup.template.interpret;

import java.util.BitSet;

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
    TemplateException exception=null;
    public TinyTemplateErrorListener(String fileName){
        this.fileName=fileName;
    }
    public void syntaxError(@NotNull Recognizer<?, ?> recognizer, @Nullable Object o, int i, int i1, @NotNull String s, @Nullable RecognitionException e) {
    	RuleContext ruleContext=e.getCtx();
    	if(ruleContext!=null && ruleContext instanceof ParserRuleContext){
    		exception=new TemplateException(e,(ParserRuleContext)ruleContext,fileName);
    	}else{
    		exception=new TemplateException(e, null,fileName);
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
