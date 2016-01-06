package org.tinygroup.dbrouter.impl.shardrule;

import groovy.lang.GroovyClassLoader;
import org.tinygroup.exception.BaseRuntimeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangwy11342 on 2016/1/6.
 */
public class GroovyRuleEngine {
    private Object ruleObj;
    private Method m_routingRuleMap;
    private static final Pattern RETURN_WHOLE_WORD_PATTERN = Pattern.compile("\\breturn\\b",
            Pattern.CASE_INSENSITIVE); // 全字匹配
    private static final Pattern DOLLER_PATTERN            = Pattern.compile("#.*?#");

    public boolean eval(String expression,Map map){
        try {
            return (Boolean)m_routingRuleMap.invoke(ruleObj,map);
        } catch (IllegalAccessException e) {
            throw new BaseRuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new BaseRuntimeException(e);
        }
    }

    protected void initInternal() {
        String expression = "println \"Hello\"; return #userid#>8";
        if (expression == null) {
            throw new IllegalArgumentException("未指定 expression");
        }
        GroovyClassLoader loader = AccessController
                .doPrivileged(new PrivilegedAction<GroovyClassLoader>() {
                    public GroovyClassLoader run() {
                        return new GroovyClassLoader(Thread.currentThread().getContextClassLoader());
                    }
                });
        String groovyRule = getGroovyRule(expression);
        Class<?> c_groovy = loader.parseClass(groovyRule);

        try {
            // 新建类实例
            ruleObj = c_groovy.newInstance();

            // 获取方法
            m_routingRuleMap = getMethod(c_groovy, "eval", Map.class);
            if (m_routingRuleMap == null) {
                throw new IllegalArgumentException("规则方法没找到");
            }
            m_routingRuleMap.setAccessible(true);

        } catch (Throwable t) {
            throw new IllegalArgumentException("实例化规则对象失败", t);
        }
    }

    // Integer.valueOf(#userIdStr#.substring(0,1),16).intdiv(8)
    protected String getGroovyRule(String expression) {
        StringBuffer sb = new StringBuffer();
//        sb.append(IMPORT_STATIC_METHOD);
        Matcher matcher = DOLLER_PATTERN.matcher(expression);
        sb.append("public class RULE ").append("{");
        sb.append("public boolean eval(Map map){");
        // StringBuffer sb = new StringBuffer();
        // 替换并组装advancedParameter
        int start = 0;

        Matcher returnMarcher = RETURN_WHOLE_WORD_PATTERN.matcher(expression);
        if (!returnMarcher.find()) {
            sb.append("return");
        }

        while (matcher.find(start)) {
            String realParam = matcher.group();
            realParam = realParam.substring(1, realParam.length() - 1);

            sb.append(expression.substring(start, matcher.start()));
            sb.append("(map.get(\"");
            // 替换成(map.get("key"));
            sb.append(realParam);
            sb.append("\"))");

            start = matcher.end();
        }
        sb.append(expression.substring(start));
        sb.append(";");
        sb.append("}");
        sb.append("}");
        System.out.println(sb);
        return sb.toString();
    }


    private static Method getMethod(Class<?> c, String name, Class<?>... parameterTypes) {
        try {
            return c.getMethod(name, parameterTypes);
        } catch (SecurityException e) {
            throw new IllegalArgumentException("实例化规则对象失败", e);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("没有这个方法" + name, e);
        }
    }

    public static void main(String[] args){
        GroovyRuleEngine test = new GroovyRuleEngine();
        Map map = new HashMap();
        map.put("userid", 2);
        test.eval("#userid#>0&#username#.indexOf(\"aa\")",map);
    }

}
