package org.tinygroup.template.parser;

import junit.framework.TestCase;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tinygroup.template.parser.grammer.JetTemplateLexer;
import org.tinygroup.template.parser.grammer.JetTemplateParser;

/**
 * Created by luoguo on 2014/6/4.
 */
public class TestParser extends TestCase {
    public String execute(String template) {
//        char[] source="#--aaa--##*aaa*#abc#*aaa*#aa#[[#*aaaaa*#]]#${abc}".toCharArray();
//        char[] source="${abc.name}".toCharArray();
        char[] source = template.toCharArray();
        ANTLRInputStream is = new ANTLRInputStream(source, source.length);
        is.name = "testname"; // set source file name, it will be displayed in error report.
        JetTemplateParser parser = new JetTemplateParser(new CommonTokenStream(new JetTemplateLexer(is)));
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(JetTemplateErrorListener.getInstance());
        parser.setErrorHandler(new JetTemplateErrorStrategy());

        JetTemplateParser.TemplateContext templateParseTree = parser.template();
        JetTemplateCodeVisitor visitor = new JetTemplateCodeVisitor();
        CodeBlock codeBlock = templateParseTree.accept(visitor);
        return codeBlock.toString();
    }

    public void testT1() throws Exception {
        String result = execute("");
        assertEquals(-1, result.indexOf("write($writer,"));
    }

    public void testT2() throws Exception {
        String result = execute("abc");
        assertTrue(result.indexOf("write($writer,\"abc\");") > 0);
    }
    public void testT2_1() throws Exception {
        String result = execute("${aa.bb}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.p(U.c($context,\"aa\"),U.c($context,\"bb\")));") > 0);
    }

    public void testT3() throws Exception {
        String result = execute("a#--abc--#b");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,\"a\");") > 0);
        assertTrue(result.indexOf("write($writer,\"b\");") > 0);
    }
    public void testT3_1() throws Exception {
        String result = execute("${-123}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"l-\",123));") > 0);
    }
    public void testT3_2() throws Exception {
        String result = execute("${'abc\"def'}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,\"abc\\\"def\");") > 0);
    }
    public void testT3_3() throws Exception {
        String result = execute("#set(a=1>2)");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"a\",O.e(\">\",1,2));") > 0);
    }
    public void testT3_4() throws Exception {
        String result = execute("${1==2}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"==\",1,2));") > 0);
    }
    public void testT3_5() throws Exception {
        String result = execute("#if(true)aaa#end");
        System.out.println(result);
        assertTrue(result.indexOf("if(U.b(true)){") > 0);
        assertTrue(result.indexOf("write($writer,\"aaa\");") > 0);
    }
    public void testT3_6() throws Exception {
        String result = execute("#if(true)bbb#{else}aaa#end");
        System.out.println(result);
        assertTrue(result.indexOf("if(U.b(true)){") > 0);
        assertTrue(result.indexOf("write($writer,\"aaa\");") > 0);
        assertTrue(result.indexOf("write($writer,\"bbb\");") > 0);
    }
    public void testT4() throws Exception {
        String result = execute("a#*abc*#b");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,\"a\");") > 0);
        assertTrue(result.indexOf("write($writer,\"b\");") > 0);
    }

    public void testT4_2() throws Exception {
        String result = execute("ab##cd");
        assertTrue(result.indexOf("write($writer,\"ab\");") > 0);
    }

    public void testT5() throws Exception {
        String result = execute("##abc");
        System.out.println(result);
        assertTrue(result.indexOf("$writer.write") < 0);
    }

    public void testT6() throws Exception {
        String result = execute("${123}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,123);") > 0);
    }

    public void testT7() throws Exception {
        String result = execute("${123}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,123);") > 0);
    }

    public void testT8() throws Exception {
        String result = execute("${\"123\"}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,\"123\");") > 0);
    }

    public void testT9() throws Exception {
        String result = execute("${\"123\"}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,\"123\");") > 0);
    }

    public void testT10() throws Exception {
        String result = execute("${1+2}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"+\",1,2));") > 0);
    }

    public void testT11() throws Exception {
        String result = execute("${a.b}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.p(U.c($context,\"a\"),U.c($context,\"b\")));") > 0);
    }

    public void testT12() throws Exception {
        String result = execute("${a+1}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"+\",U.c($context,\"a\"),1));") > 0);
    }

    public void testT13() throws Exception {
        String result = execute("${a[1]}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.a(U.c($context,\"a\"),1));") > 0);
    }

    public void testT14() throws Exception {
        String result = execute("${a[$b]}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.a(U.c($context,\"a\"),U.c($context,\"b\")));") > 0);
    }

    public void testT15() throws Exception {
        String result = execute("${a+b}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"+\",U.c($context,\"a\"),U.c($context,\"b\")));") > 0);
    }

    public void testT16() throws Exception {
        String result = execute("${a.$b}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.p(U.c($context,\"a\"),U.c($context,\"b\")));") > 0);
    }
    public void testT17() throws Exception {
        String result = execute("$!{\"<abc\"}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,StringEscapeUtils.escapeHtml((\"<abc\")+\"\"));") > 0);
    }
    public void testT18() throws Exception {
        String result = execute("#set(arraylist = [\"a\",\"b\",\"c\",\"d\"])");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"arraylist\",new Object[]{\"a\",\"b\",\"c\",\"d\"});") > 0);
    }
    public void testT19() throws Exception {
        String result = execute("#set(arraylist = [\"a\",aa,\"c\",\"d\"])");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"arraylist\",new Object[]{\"a\",U.c($context,\"aa\"),\"c\",\"d\"});") > 0);
    }
    public void testT20() throws Exception {
        String result = execute("#set(arraylist = [\"a\",$aa,\"c\",\"d\"])");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"arraylist\",new Object[]{\"a\",U.c($context,\"aa\"),\"c\",\"d\"});") > 0);
    }
    public void testT21() throws Exception {
        String result = execute("${a.($b+1)}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.p(U.c($context,\"a\"),(O.e(\"+\",U.c($context,\"b\"),1))));") > 0);
    }
    public void testT22() throws Exception {
        String result = execute("#set(arraylist = 123)");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"arraylist\",123);") > 0);
    }
    public void testT23() throws Exception {
        String result = execute("#set(arraylist = \"124a\")");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"arraylist\",\"124a\");") > 0);
    }
    public void testT24() throws Exception {
        //TODO 类型判定处理
        String result = execute("#set(arraylist = \"124a\",a=3,b=4)");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"arraylist\",\"124a\");") > 0);
        assertTrue(result.indexOf("$context.put(\"a\",3);") > 0);
        assertTrue(result.indexOf("$context.put(\"b\",4);") > 0);
    }
    public void testT25() throws Exception {
        String result = execute("#set(arraylist = {\"a\":1,\"b\":1} )");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"arraylist\",new TemplateMap().putItem(\"a\",1).putItem(\"b\",1));") > 0);
    }
    public void testT26() throws Exception {
        String result = execute("${1==2}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"==\",1,2));") > 0);
    }
    public void testT27() throws Exception {
        String result = execute("${1==2*3}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"==\",1,O.e(\"*\",2,3)));") > 0);
    }
    public void testT28() throws Exception {
        String result = execute("${aa==2*3}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"==\",U.c($context,\"aa\"),O.e(\"*\",2,3)));") > 0);
    }
    public void testT29() throws Exception {
        String result = execute("#set(arraylist = {\"a\":[1,aa,3],\"b\":1} )");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"arraylist\",new TemplateMap().putItem(\"a\",new Object[]{1,U.c($context,\"aa\"),3}).putItem(\"b\",1));") > 0);
    }
    public void testT30() throws Exception {
        String result = execute("#if(true)abc#end");
        System.out.println(result);
        assertTrue(result.indexOf("if(U.b(true)){") > 0);
        assertTrue(result.indexOf("write($writer,\"abc\");") > 0);
    }
    public void testT31() throws Exception {
        String result = execute("#if(true)abc#if(false)def#end#end");
        System.out.println(result);
        assertTrue(result.indexOf("if(U.b(true)){") > 0);
        assertTrue(result.indexOf("if(U.b(false)){") > 0);
        assertTrue(result.indexOf("write($writer,\"abc\");") > 0);
        assertTrue(result.indexOf("write($writer,\"def\");") > 0);
    }
    public void testT32() throws Exception {
        String result = execute("#if(true)abc#{end}def");
        System.out.println(result);
        assertTrue(result.indexOf("if(U.b(true)){") > 0);
        assertTrue(result.indexOf("write($writer,\"abc\");") > 0);
        assertTrue(result.indexOf("write($writer,\"def\");") > 0);
    }
    public void testT33() throws Exception {
        String result = execute("#if(true)abc#{else}def#end");
        System.out.println(result);
        assertTrue(result.indexOf("if(U.b(true)){") > 0);
        assertTrue(result.indexOf("}else{") > 0);
        assertTrue(result.indexOf("write($writer,\"abc\");") > 0);
        assertTrue(result.indexOf("write($writer,\"def\");") > 0);
    }

    public void testT34() throws Exception {
        String result = execute("#if(true)abc#elseif(true)ddd #{else}def#end");
        System.out.println(result);
        assertTrue(result.indexOf("if(U.b(true)){") > 0);
        assertTrue(result.indexOf("}else if(U.b(true)){") > 0);
        assertTrue(result.indexOf("}else{") > 0);
        assertTrue(result.indexOf("write($writer,\"abc\");") > 0);
        assertTrue(result.indexOf("write($writer,\"def\");") > 0);
    }

    public void testT35() throws Exception {
        String result = execute("#for(i: [1,2,3,4,5])#if(true)abc${i}#break(true||false)#end#end");
        System.out.println(result);
        assertTrue(result.indexOf("ForIterator $iFor = new ForIterator(new Object[]{1,2,3,4,5});") > 0);
        assertTrue(result.indexOf("$context.put(\"i\",$iFor.next());") > 0);
    }

    public void testT36() throws Exception {
        String result = execute("${true||false}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,true||false);") > 0);
    }
    public void testT36_1() throws Exception {
        String result = execute("#set(a={\"aa\":1})");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"a\",new TemplateMap().putItem(\"aa\",1));") > 0);
    }

    public void testT37() throws Exception {
        String result = execute("${true? 1 : 2 }");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.b(true)?1:2);") > 0);
    }
    public void testT38() throws Exception {
        String result = execute("#set(a=true? 1 : 2 )");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"a\",U.b(true)?1:2);") > 0);
    }
    public void testT39() throws Exception {
        String result = execute("${4|6|6}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"|\",O.e(\"|\",4,6),6));") > 0);
    }
    public void testT40() throws Exception {
        String result = execute("${4+6+6^3|3}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"|\",O.e(\"^\",O.e(\"+\",O.e(\"+\",4,6),6),3),3));") > 0);
    }
    public void testT41() throws Exception {
        String result = execute("#set(a=1)");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"a\",1);") > 0);
    }
    public void testT42() throws Exception {
        String result = execute("${a?1:2}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.b(U.c($context,\"a\"))?1:2);") > 0);
    }
    public void testT43_1() throws Exception {
        String result = execute("#macro test(aa)abc #end");
        System.out.println(result);
        assertTrue(result.indexOf("String[] args = {\"aa\"};") > 0);
        assertTrue(result.indexOf("class $TEMPLATE_CLASS_NAME extends AbstractTemplate{") > 0);
        assertTrue(result.indexOf(" protected void renderTemplate(TemplateContext $context, Writer $writer) throws IOException, TemplateException{") > 0);
        assertTrue(result.indexOf("write($writer,\"abc \");") > 0);
    }

    public void testT43() throws Exception {
        String result = execute("#macro test(aa)abc #end#macro test1(bb)def #end");
        System.out.println(result);
        assertTrue(result.indexOf("class test1 extends AbstractMacro {") > 0);
        assertTrue(result.indexOf("class test extends AbstractMacro {") > 0);
        assertTrue(result.indexOf("write($writer,\"abc \");") > 0);
        assertTrue(result.indexOf("write($writer,\"def \");") > 0);
    }
    public void testT44() throws Exception {
        String result = execute("#for(i : [1,2,3,4,5])#end");
        System.out.println(result);
        assertTrue(result.indexOf("ForIterator $iFor = new ForIterator(new Object[]{1,2,3,4,5});") > 0);
        assertTrue(result.indexOf("$context.put(\"i\",$iFor.next());") > 0);
    }
    public void testT44_1() throws Exception {
        String result = execute("#for(i : [1,2,3,4,5])#{else}ddd#end");
        System.out.println(result);
        assertTrue(result.indexOf("ForIterator $iFor = new ForIterator(new Object[]{1,2,3,4,5});") > 0);
        assertTrue(result.indexOf("if(U.b($iFor.getSize()>0)){") > 0);
        assertTrue(result.indexOf("$context.put(\"i\",$iFor.next());") > 0);
    }
    public void testT45() throws Exception {
        String result = execute("#test(aa)");
        System.out.println(result);
        assertTrue(result.indexOf("$newContext.put($macro.getParameterNames()[0],U.c($context,\"aa\"));") > 0);
        assertTrue(result.indexOf("$macro=getTemplateEngine().findMacro(\"test\",$template,$context);") > 0);
    }
    public void testT46() throws Exception {
        String result = execute("#test(aa=1,bb=2,3)");
        System.out.println(result);
        assertTrue(result.indexOf("$newContext.put($macro.getParameterNames()[2],3);") > 0);
        assertTrue(result.indexOf("$newContext.put(\"aa\",1);") > 0);
        assertTrue(result.indexOf("$newContext.put(\"bb\",2);") > 0);
        assertTrue(result.indexOf("getTemplateEngine().renderMacro($macro, $template, $newContext, $writer);") > 0);
    }
    public void testT47() throws Exception {
        String result = execute("#@test(aa=1,bb=2,3) aaa  #end");
        System.out.println(result);
        assertTrue(result.indexOf("$newContext.put(\"aa\",1);") > 0);
        assertTrue(result.indexOf("$newContext.put(\"bb\",2);") > 0);
        assertTrue(result.indexOf("$newContext.put($macro.getParameterNames()[2],3);") > 0);
        assertTrue(result.indexOf("$newContext.put($macro.getParameterNames()[2],3);") > 0);
    }
    public void testT48() throws Exception {
        String result = execute("#@test(aa=1,bb=2,3) aaa #bbb  #end");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,\"#bbb\");") > 0);
    }
    public void testT48_1() throws Exception {
        String result = execute("#@test(aa=1,bb=2,3) aaa #bbb(1)  #end");
        System.out.println(result);
        assertTrue(result.indexOf("$macro=getTemplateEngine().findMacro(\"bbb\",$template,$context);") > 0);
        assertTrue(result.indexOf("$newContext.put($macro.getParameterNames()[0],1);") > 0);
    }
    public void testT49() throws Exception {
        String result = execute("#@test(aa=1,bb=2,3) aaa #@bbb(9)bb#end  #end");
        System.out.println(result);
        assertTrue(result.indexOf("$newContext.put($macro.getParameterNames()[0],9);") > 0);
        assertTrue(result.indexOf("$newContext.put($macro.getParameterNames()[2],3);") > 0);
    }
    public void testT50() throws Exception {
        String result = execute("#macro test(aaa)ddd#end #@test(aa=1,bb=2,3) aaa #@bbb(9)bb#end  #end");
        System.out.println(result);
        assertTrue(result.indexOf("$newContext.put($macro.getParameterNames()[0],9);") > 0);
        assertTrue(result.indexOf("$newContext.put($macro.getParameterNames()[2],3);") > 0);
    }
    public void testT51() throws Exception {
        String result = execute("${a1.b.c}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.p(U.p(U.c($context,\"a1\"),U.c($context,\"b\")),U.c($context,\"c\")));") > 0);
    }
    public void testT52() throws Exception {
        String result = execute("#macro aab()aa : ${aa}#end");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.c($context,\"aa\"));") > 0);
    }
}
