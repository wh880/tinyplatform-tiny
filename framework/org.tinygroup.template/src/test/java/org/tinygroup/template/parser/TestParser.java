package org.tinygroup.template.parser;

import junit.framework.TestCase;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tinygroup.template.parser.grammer.TinyTemplateLexer;
import org.tinygroup.template.parser.grammer.TinyTemplateParser;

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
        TinyTemplateParser parser = new TinyTemplateParser(new CommonTokenStream(new TinyTemplateLexer(is)));
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(TinyTemplateErrorListener.getInstance());
        parser.setErrorHandler(new TinyTemplateErrorStrategy());

        TinyTemplateParser.TemplateContext templateParseTree = parser.template();
        TinyTemplateCodeVisitor visitor = new TinyTemplateCodeVisitor(parser);
        CodeBlock codeBlock = templateParseTree.accept(visitor);
        return codeBlock.toString();
    }


    public void testT2() throws Exception {
        String result = execute("abc");
        assertTrue(result.indexOf("write($writer,\"abc\");") > 0);
    }


    public void testT2_200() throws Exception {
        String result = execute("#bodyContent");
        System.out.println(result);
        assertTrue(result.indexOf("$macro.render($template,$context,$writer);") > 0);
    }
    public void testT2_21() throws Exception {
        String result = execute("$!{abc}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.escapeHtml((U.v($context,\"abc\"))));") > 0);
    }
    public void testT2_22() throws Exception {
        String result = execute("${b?.c}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.sp(U.v($context,\"b\"),\"c\"));") > 0);
    }
    public void testT2_23() throws Exception {
        String result = execute("${b.abc()}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.c($template,U.v($context,\"b\"),\"abc\"));") > 0);
    }
    public void testT2_24() throws Exception {
        String result = execute("${b.abc()}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.c($template,U.v($context,\"b\"),\"abc\"));") > 0);
    }
    public void testT2_10() throws Exception {
        String result = execute("${2++}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"l++\",2));") > 0);
    }

    public void testT2_13() throws Exception {
        String result = execute("${2>>1}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\">>\",2,1));") > 0);
    }
    public void testT2_13_1() throws Exception {
        String result = execute("${2<<1}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"<<\",2,1));") > 0);
    }
    public void testT2_13_2() throws Exception {
        String result = execute("${2>>>1}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\">>>\",2,1));") > 0);
    }

    public void testT2_14() throws Exception {
        String result = execute("${2?:1}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"?:\",2,1));") > 0);
    }

    public void testT2_15() throws Exception {
        String result = execute("${abc(2)}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,getTemplateEngine().executeFunction(\"abc\",2));") > 0);
    }

    public void testT2_16() throws Exception {
        String result = execute("${aa()}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,getTemplateEngine().executeFunction(\"aa\"));") > 0);
    }

    public void testT2_18() throws Exception {
        String result = execute("${aa(bb(1))}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,getTemplateEngine().executeFunction(\"aa\",getTemplateEngine().executeFunction(\"bb\",1)));") > 0);
    }

    public void testT2_19() throws Exception {
        String result = execute("${abc.aa(bb(1))}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.c($template,U.v($context,\"abc\"),\"aa\",getTemplateEngine().executeFunction(\"bb\",1)));") > 0);
    }
   public void testT2_20() throws Exception {
        String result = execute("${format('this is %s',2)}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,getTemplateEngine().executeFunction(\"format\",\"this is %s\",2));") > 0);
    }

    public void testT2_17() throws Exception {
        String result = execute("$${abc.bb}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.getI18n($template.getTemplateEngine().getI18nVistor(),$context,\"abc.bb\"));") > 0);
    }

    public void testT2_11() throws Exception {
        String result = execute("${2--}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"l--\",2));") > 0);
    }

    public void testT2_12() throws Exception {
        String result = execute("${map[\"id\"]}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.a(U.v($context,\"map\"),\"id\"));") > 0);
    }

    public void testT2_1() throws Exception {
        String result = execute("${aa.bb}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.p(U.v($context,\"aa\"),\"bb\"));") > 0);
    }
   public void testT3_30() throws Exception {
        String result = execute("${itemFor.index%2}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"%\",U.p(U.v($context,\"itemFor\"),\"index\"),2));") > 0);
    }

    public void testT2_2() throws Exception {
        String result = execute("#include(\"aaa/bb/cc/dd\",{a:2,b:3,c:4})");
        System.out.println(result);
        assertTrue(result.indexOf("$newContext.putAll(new TemplateMap().putItem(U.v($context,\"a\"),2).putItem(U.v($context,\"b\"),3).putItem(U.v($context,\"c\"),4));") > 0);
        assertTrue(result.indexOf("$context.putSubContext(\"$newContext\",$newContext);") > 0);
        assertTrue(result.indexOf("getTemplateEngine().renderTemplate(U.getPath(getPath(),\"aaa/bb/cc/dd\"),$newContext,$writer);") > 0);
        assertTrue(result.indexOf("$context.removeSubContext(\"$newContext\");") > 0);
    }

    public void testT2_3() throws Exception {
        String result = execute("#include(\"aaa/bb/cc/dd\")");
        System.out.println(result);
        assertTrue(result.indexOf("$context.putSubContext(\"$newContext\",$newContext);") > 0);
        assertTrue(result.indexOf("getTemplateEngine().renderTemplate(U.getPath(getPath(),\"aaa/bb/cc/dd\"),$newContext,$writer);") > 0);
        assertTrue(result.indexOf("$context.removeSubContext(\"$newContext\");") > 0);
    }

    public void testT2_4() throws Exception {
        String result = execute("<a>b</c>");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,\"<a>b</c>\");") > 0);
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
        assertTrue(result.indexOf("write($writer,U.p(U.v($context,\"a\"),\"b\"));") > 0);
    }

    public void testT12() throws Exception {
        String result = execute("${a+1}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"+\",U.v($context,\"a\"),1));") > 0);
    }

    public void testT13() throws Exception {
        String result = execute("${a[1]}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.a(U.v($context,\"a\"),1));") > 0);
    }

    public void testT14() throws Exception {
        String result = execute("${a[$b]}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.a(U.v($context,\"a\"),U.v($context,\"b\")));") > 0);
    }

    public void testT15() throws Exception {
        String result = execute("${a+b}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,O.e(\"+\",U.v($context,\"a\"),U.v($context,\"b\")));") > 0);
    }

    public void testT16() throws Exception {
        String result = execute("${a.b}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.p(U.v($context,\"a\"),\"b\"));") > 0);
    }

    public void testT17() throws Exception {
        String result = execute("$!{\"<abc\"}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.escapeHtml((\"<abc\")));") > 0);
    }

    public void testT18() throws Exception {
        String result = execute("#set(arraylist = [\"a\",\"b\",\"v\",\"d\"])");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"arraylist\",new Object[]{\"a\",\"b\",\"v\",\"d\"});") > 0);
    }

    public void testT19() throws Exception {
        String result = execute("#set(arraylist = [\"a\",aa,\"v\",\"d\"])");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"arraylist\",new Object[]{\"a\",U.v($context,\"aa\"),\"v\",\"d\"});") > 0);
    }

    public void testT20() throws Exception {
        String result = execute("#set(arraylist = [\"a\",$aa,\"v\",\"d\"])");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"arraylist\",new Object[]{\"a\",U.v($context,\"aa\"),\"v\",\"d\"});") > 0);
    }

    public void testT21() throws Exception {
        String result = execute("${a(b+1)}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,getTemplateEngine().executeFunction(\"a\",O.e(\"+\",U.v($context,\"b\"),1)));") > 0);
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
        assertTrue(result.indexOf("write($writer,O.e(\"==\",U.v($context,\"aa\"),O.e(\"*\",2,3)));") > 0);
    }

    public void testT29() throws Exception {
        String result = execute("#set(arraylist = {\"a\":[1,aa,3],\"b\":1} )");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"arraylist\",new TemplateMap().putItem(\"a\",new Object[]{1,U.v($context,\"aa\"),3}).putItem(\"b\",1));") > 0);
    }

    public void testT30() throws Exception {
        String result = execute("#if(true)abc#end");
        System.out.println(result);
        assertTrue(result.indexOf("if(U.b(true)){") > 0);
        assertTrue(result.indexOf("write($writer,\"abc\");") > 0);
    }

    public void testT30_1() throws Exception {
        String result = execute("${aa.bb(1,2,bb)}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.c($template,U.v($context,\"aa\"),\"bb\",1,2,U.v($context,\"bb\")));") > 0);
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
        assertTrue(result.indexOf("$context.put(\"iFor\",new ForIterator(new Object[]{1,2,3,4,5}));") > 0);
        assertTrue(result.indexOf("while(((ForIterator)$context.get(\"iFor\")).hasNext()){") > 0);
        assertTrue(result.indexOf("$context.put(\"i\",((ForIterator)$context.get(\"iFor\")).next());") > 0);
    }

    public void testT36() throws Exception {
        String result = execute("${true||false}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.b(true)||U.b(false));") > 0);
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
        assertTrue(result.indexOf("write($writer,U.b(U.v($context,\"a\"))?1:2);") > 0);
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
        assertTrue(result.indexOf(" $context.put(\"iFor\",new ForIterator(new Object[]{1,2,3,4,5}));") > 0);
        assertTrue(result.indexOf("$context.put(\"i\",((ForIterator)$context.get(\"iFor\")).next());") > 0);
    }

    public void testT44_1() throws Exception {
        String result = execute("#for(i : [1,2,3,4,5])#{else}ddd#end");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"iFor\",new ForIterator(new Object[]{1,2,3,4,5}));") > 0);
        assertTrue(result.indexOf("if(U.b(((ForIterator)$context.get(\"iFor\")).getSize()>0)){") > 0);
        assertTrue(result.indexOf("$context.put(\"i\",((ForIterator)$context.get(\"iFor\")).next());") > 0);
    }

    public void testT44_2() throws Exception {
        String result = execute("#for(i : [])ddd#end");
        System.out.println(result);
        assertTrue(result.indexOf("$context.put(\"iFor\",new ForIterator(new Object[]{}));") > 0);
        assertTrue(result.indexOf("$context.put(\"i\",((ForIterator)$context.get(\"iFor\")).next());") > 0);
    }

    public void testT44_3() throws Exception {
        String result = execute("#for(i : {})ddd#end");
        System.out.println(result);
        assertTrue(result.indexOf("while(((ForIterator)$context.get(\"iFor\")).hasNext()){") > 0);
        assertTrue(result.indexOf("$context.put(\"i\",((ForIterator)$context.get(\"iFor\")).next());") > 0);
    }

    public void testT45() throws Exception {
        String result = execute("#test(aa)");
        System.out.println(result);
        assertTrue(result.indexOf("$newContext.put($macro.getParameterNames()[0],U.v($context,\"aa\"));") > 0);
        assertTrue(result.indexOf("$macro=getTemplateEngine().findMacro(\"test\",$template,$context);") > 0);
    }

    public void testT46() throws Exception {
        String result = execute("#test(aa=1,bb=2,3)");
        System.out.println(result);
        assertTrue(result.indexOf("$newContext.put($macro.getParameterNames()[2],3);") > 0);
        assertTrue(result.indexOf("$newContext.put(\"aa\",1);") > 0);
        assertTrue(result.indexOf("$newContext.put(\"bb\",2);") > 0);
        assertTrue(result.indexOf("$macro.render($template,$context,$writer);") > 0);
    }

    public void testT46_0() throws Exception {
        String result = execute("#call(\"test\",aa=1,bb=2,3)");
        System.out.println(result);
        assertTrue(result.indexOf("$newContext.put($macro.getParameterNames()[2],3);") > 0);
        assertTrue(result.indexOf("$newContext.put(\"aa\",1);") > 0);
        assertTrue(result.indexOf("$newContext.put(\"bb\",2);") > 0);
        assertTrue(result.indexOf("$macro.render($template,$context,$writer);") > 0);
    }

    public void testT46_1() throws Exception {
        String result = execute("#call(\"test\")#end");
        System.out.println(result);
        assertTrue(result.indexOf("$macro.render($template,$context,$writer);") > 0);
    }

    public void testT47() throws Exception {
        String result = execute("#@test(aa=1,bb=2,3) aaa  #end");
        System.out.println(result);
        assertTrue(result.indexOf("$newContext.put(\"aa\",1);") > 0);
        assertTrue(result.indexOf("$newContext.put(\"bb\",2);") > 0);
        assertTrue(result.indexOf("$newContext.put($macro.getParameterNames()[2],3);") > 0);
        assertTrue(result.indexOf("$newContext.put($macro.getParameterNames()[2],3);") > 0);
    }

    public void testT47_1() throws Exception {
        String result = execute("#@call('test',aa=1,bb=2,3) aaa  #end");
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
        String result = execute("${a1.b.v}");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.p(U.p(U.v($context,\"a1\"),\"b\"),\"v\"));") > 0);
    }

    public void testT52() throws Exception {
        String result = execute("#macro aab()aa : ${aa}#end");
        System.out.println(result);
        assertTrue(result.indexOf("write($writer,U.v($context,\"aa\"));") > 0);
    }
}
