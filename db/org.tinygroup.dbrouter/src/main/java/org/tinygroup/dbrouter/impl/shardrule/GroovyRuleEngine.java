package org.tinygroup.dbrouter.impl.shardrule;

import groovy.lang.GroovyClassLoader;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.dbrouter.exception.DbrouterRuntimeException;

import static org.tinygroup.logger.LogLevel.DEBUG;

import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

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
    private static final Pattern RETURN_WHOLE_WORD_PATTERN = Pattern.compile("\\breturn\\b",
            Pattern.CASE_INSENSITIVE); // 全字匹配
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("#.*?#");
    private static final String IMPORT_STATIC_METHOD = "import java.lang.util.*;";
    private static Logger LOGGER = LoggerFactory.getLogger(GroovyRuleEngine.class);
    private static Map<String, Object> ruleObjCache = new HashMap<String, Object>();

    public static boolean eval(String expression, Map map) {
        if (expression == null) {
            throw new DbrouterRuntimeException(new IllegalArgumentException("未指定 expression"));
        }
        return check(expression, map);
    }

    private static boolean check(String expression, Map map) {

        Object ruleObj = getGroovyRuleInstance(expression);
        try {
            // 获取方法
            Method m_routingRuleMap = getMethod(ruleObj.getClass(), "eval", Map.class);
            if (m_routingRuleMap == null) {
                throw new DbrouterRuntimeException(new IllegalArgumentException("规则方法没找到"));
            }
            m_routingRuleMap.setAccessible(true);
            return (Boolean) m_routingRuleMap.invoke(ruleObj, map);
        } catch (Throwable t) {
            throw new DbrouterRuntimeException(new IllegalArgumentException("执行表达式失败", t));
        }
    }

    private static Object getGroovyRuleInstance(String expression) {
        //先从缓存获取对象
        Object ruleObj = ruleObjCache.get(expression);
        if (ruleObj != null) {
            return ruleObj;
        }
        GroovyClassLoader loader = AccessController
                .doPrivileged(new PrivilegedAction<GroovyClassLoader>() {
                    public GroovyClassLoader run() {
                        return new GroovyClassLoader(Thread.currentThread().getContextClassLoader());
                    }
                });
        String groovyRule = getGroovyRule(StringUtil.trim(expression));
        try {
            // 新建类实例
            Class<?> c_groovy = loader.parseClass(groovyRule);
            ruleObj = c_groovy.newInstance();
            ruleObjCache.put(expression,ruleObj);//放入缓存
            return ruleObj;
        } catch (Throwable t) {
            throw new DbrouterRuntimeException("groovy对象获取失败,执行的class为:" + groovyRule, new IllegalArgumentException("代码解析或实例化失败", t));
        }
    }

    // Integer.valueOf(#userIdStr#.substring(0,1),16).intdiv(8)
    private static String getGroovyRule(String expression) {
        StringBuffer sb = new StringBuffer();
        sb.append(IMPORT_STATIC_METHOD);
        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
        sb.append("public class GroovyRule ").append("{");
        sb.append("public boolean eval(Map map){");
        int start = 0;

        Matcher returnMarcher = RETURN_WHOLE_WORD_PATTERN.matcher(expression);
        if (!returnMarcher.find()) {
            sb.append("return ");
        }

        while (matcher.find(start)) {
            String realParam = matcher.group();
            realParam = realParam.substring(1, realParam.length() - 1);

            sb.append(expression.substring(start, matcher.start()));
            sb.append("map.get(\"");
            // 替换成(map.get("key"));
            sb.append(realParam);
            sb.append("\")");
            start = matcher.end();
        }
        sb.append(expression.substring(start));
        sb.append(";");
        sb.append("}");
        sb.append("}");
        String fullGroovyStr = sb.toString();
        LOGGER.logMessage(DEBUG, "规则代码:{1}", fullGroovyStr);
        return fullGroovyStr;
    }


    private static Method getMethod(Class<?> c, String name, Class<?>... parameterTypes) {
        try {
            return c.getMethod(name, parameterTypes);
        } catch (SecurityException e) {
            throw new DbrouterRuntimeException(new IllegalArgumentException("实例化规则对象失败", e));
        } catch (NoSuchMethodException e) {
            throw new DbrouterRuntimeException(new IllegalArgumentException("没有这个方法" + name, e));
        }
    }

}
