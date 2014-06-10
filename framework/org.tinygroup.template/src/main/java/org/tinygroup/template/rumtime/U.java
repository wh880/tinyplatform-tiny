package org.tinygroup.template.rumtime;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.tinygroup.commons.tools.ArrayUtil;
import org.tinygroup.commons.tools.Enumerator;
import org.tinygroup.context.Context;
import org.tinygroup.template.I18nVistor;
import org.tinygroup.template.TemplateException;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 工具类，之所以起这么短是为了生成的代码短一些
 * Created by luoguo on 2014/6/4.
 */
public class U {
    /**
     * 获取属性
     *
     * @param object
     * @param name
     * @return
     * @throws TemplateException
     */
    public static Object p(Object object, Object name) throws TemplateException {
        try {
            return BeanUtils.getProperty(object, name.toString());
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }

    public static Object sp(Object object, Object name) throws TemplateException {
        if (object != null) {
            return p(object, name);
        }
        return null;
    }

    public static String getI18n(I18nVistor i18nVistor, String key) {
        if (i18nVistor == null) {
            return key;
        } else {
            return i18nVistor.getI18nMessage(key);
        }
    }

    /**
     * 进行方法调用
     *
     * @param object
     * @param methodName
     * @param parameters
     * @return
     * @throws TemplateException
     */
    public static Object c(Object object, String methodName, Object... parameters) throws TemplateException {
        try {
            if (parameters == null) {
                return invokeMethod(object, methodName, parameters, getParameterTypes(object.getClass(), methodName));
            }
            for (Object para : parameters) {
                if (para == null) {
                    return invokeMethod(object, methodName, parameters, getParameterTypes(object.getClass(), methodName));
                }
            }
            return MethodUtils.invokeMethod(object, methodName, parameters);
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }

    public static Object sc(Object object, String methodName, Object... parameters) throws TemplateException {
        if (object != null) {
            return c(object, methodName, parameters);
        }
        return null;
    }

    private static Object invokeMethod(Object object, String methodName, Object[] parameters, Class<?>[] parameterTypes) throws TemplateException {
        if (parameters == null && parameterTypes.length > 0) {
            parameters = new Object[parameterTypes.length];
        }
        try {
            return MethodUtils.invokeMethod(object, methodName, parameters, parameterTypes);
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }

    public static Class<?>[] getParameterTypes(Class clazz, String methodName) throws TemplateException {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(methodName)) {
                return method.getParameterTypes();
            }
        }
        throw new TemplateException(clazz.getName() + "中找不到方法:" + methodName);
    }

    /**
     * 从上下文获取对应标识的值，如果找不到，则返回字符器
     *
     * @param context
     * @param key
     * @return
     */
    public static Object v(Context context, Object key) {
        Object object = context.get(key.toString());
        if (object != null) {
            return object;
        } else {
            return key;
        }
    }

    /**
     * 根据当前路径，计算新路径的绝对路径
     *
     * @param currentPath
     * @param newPath
     * @return
     */
    public static String getPath(String currentPath, String newPath) {
        URI uri = URI.create(currentPath);
        newPath = newPath.replaceAll("[\\\\]", "/");
        URI newUri = uri.resolve(newPath);

        return newUri.getPath();
    }

    /**
     * 进行Html转义
     *
     * @param object
     * @return
     */
    public static String escapeHtml(Object object) {
        return StringEscapeUtils.escapeHtml(object.toString());
    }

    /**
     * 判断布尔值是否成立
     *
     * @param o
     * @return
     */
    public static boolean b(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Boolean) {
            Boolean b = (Boolean) o;
            return b.booleanValue();
        } else if (o instanceof String) {
            return ((String) o).length() > 0;
        } else if (o instanceof Collection) {
            return ((Collection) o).size() > 0;
        } else if (o.getClass().isArray()) {
            return ArrayUtil.arrayLength(o) > 0;
        } else if (o instanceof Iterator) {
            Iterator i = (Iterator) o;
            return ((Iterator) o).hasNext();
        } else if (o instanceof Enumerator) {
            Enumerator e = (Enumerator) o;
            return e.hasMoreElements();
        } else if (o instanceof Map) {
            Map e = (Map) o;
            return e.size() > 0;
        }
        return true;
    }

    /**
     * 访问数组类型的内容
     *
     * @param object
     * @param indexObject 索引值
     * @return
     * @throws Exception
     */
    public static Object a(Object object, Object indexObject) throws TemplateException {
        int index;
        if (object instanceof Map) {
            Map map = (Map) object;
            return map.get(indexObject.toString());
        }
        if (indexObject instanceof Integer) {
            index = ((Integer) indexObject).intValue();
        } else if (indexObject instanceof Long) {
            index = ((Long) indexObject).intValue();
        } else if (indexObject instanceof Double) {
            index = ((Double) indexObject).intValue();
        } else if (indexObject instanceof Float) {
            index = ((Float) indexObject).intValue();
        } else if (indexObject instanceof Byte) {
            index = ((Byte) indexObject).intValue();
        } else if (indexObject instanceof BigDecimal) {
            index = ((BigDecimal) indexObject).intValue();
        } else {
            index = Integer.parseInt(indexObject.toString());
        }
        if (object.getClass().isArray()) {
            return Array.get(object, index);
        } else if (object instanceof Collection) {
            Collection c = (Collection) object;
            int i = 0;
            while (i < c.size()) {
                Object o = c.iterator().next();
                if (i == index) {
                    return o;
                }
                i++;
            }
        } else {
            String o = object.toString();
            return o.charAt(index);
        }

        throw new TemplateException(object.getClass().getName() + "不可以用下标方式取值。");
    }

}
