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
package org.tinygroup.template.rumtime;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.tinygroup.commons.tools.ArrayUtil;
import org.tinygroup.commons.tools.Enumerator;
import org.tinygroup.context.Context;
import org.tinygroup.template.*;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.net.URI;
import java.util.*;

/**
 * 工具类，之所以起这么短是为了生成的代码短一些
 * Created by luoguo on 2014/6/4.
 */
public final class TemplateUtil {
    private static Map<Class, Map<String, Method>> methodCache = new HashMap<Class, Map<String, Method>>();
    private static Map<Class, Map<String, Field>> fieldCache = new HashMap<Class, Map<String, Field>>();
    private static PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
    private static String[] tabCache = new String[31];
    private static boolean safeVariable = false;

    static {
        tabCache[0] = "";
        for (int i = 1; i < tabCache.length; i++) {
            tabCache[i] = String.format("%" + i * 4 + "s", "");
        }
    }

    private TemplateUtil() {
    }

    public static boolean isSafeVariable() {
        return safeVariable;
    }

    public static void setSafeVariable(boolean safeVariable) {
        TemplateUtil.safeVariable = safeVariable;
    }

    /**
     * 获取属性
     *
     * @param object
     * @param name
     * @return
     * @throws TemplateException
     */
    public static Object getAttribute(Object object, Object name) throws TemplateException {
        try {
            if (object instanceof Map) {
                Object value = ((Map) object).get(name);
                if (value != null) {
                    return value;
                }
            }
            String fieldName = name.toString();
            Map<String, Method> stringMethodMap = methodCache.get(object.getClass());
            Method method = null;
            if (stringMethodMap != null) {
                method = stringMethodMap.get(fieldName);
            }
            if (method == null) {
                PropertyDescriptor descriptor =
                        propertyUtilsBean.getPropertyDescriptor(object, fieldName);
                if (descriptor != null && descriptor.getReadMethod() != null) {
                    method = object.getClass().getMethod(descriptor.getReadMethod().getName());
                    method.setAccessible(true);
                    if (stringMethodMap == null) {
                        stringMethodMap = new HashMap<String, Method>();
                        methodCache.put(object.getClass(), stringMethodMap);
                    }
                    stringMethodMap.put(fieldName, method);
                }
            }
            if (method != null) {
                return method.invoke(object);
            }
            Map<String, Field> stringFieldMap = fieldCache.get(fieldName);
            Field field = null;
            if (stringFieldMap != null) {
                field = stringFieldMap.get(fieldName);
            }
            if (field == null) {
                field = object.getClass().getField(fieldName);
                if (field != null) {
                    if ((field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC) {
                        field.setAccessible(true);
                        if (stringFieldMap == null) {
                            stringFieldMap = new HashMap<String, Field>();
                            fieldCache.put(object.getClass(), stringFieldMap);
                        }
                        stringFieldMap.put(fieldName, field);
                    } else {
                        field = null;
                    }
                }
            }
            if (field != null) {
                return field.get(object);
            }
            throw new TemplateException(object.getClass().getName() + "中不能找到" + fieldName + "的键值、属性。");
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }

    public static Object sp(Object object, Object name) throws TemplateException {
        if (object != null) {
            try {
                return getAttribute(object, name);
            } catch (TemplateException e) {
                if (e.getCause().getClass() == NullPointerException.class || e.getCause().getClass() == NoSuchFieldException.class || e.getMessage().indexOf("中不能找到") > 0) {
                    return null;
                } else {
                    throw e;
                }
            }
        }
        return null;
    }

    /**
     * 获取
     *
     * @param i18nVistor
     * @param key
     * @return
     */
    public static String getI18n(I18nVisitor i18nVistor, TemplateContext context, String key) {
        if (key == null) {
            return null;
        }
        if (i18nVistor == null) {
            return key;
        } else {
            return i18nVistor.getI18nMessage(context, key);
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
    public static Object callMethod(Template template, TemplateContext context, Object object, String methodName, Object... parameters) throws TemplateException {
        try {
            TemplateFunction function = template.getTemplateEngine().getTemplateFunction(object, methodName);
            if (function != null) {
                return executeExtendFunction(template, context, object, function, parameters);
            } else {
                //如果有缓冲，则用缓冲方式调用之
                return executeClassMethod(object, methodName, parameters);
            }
        } catch (Exception e) {
        	//反射异常返回真实的目标异常
        	if(e instanceof InvocationTargetException){
        		InvocationTargetException e1 =(InvocationTargetException) e;
        		throw new TemplateException(e1.getTargetException());
        	}
            throw new TemplateException(e);
        }
    }

    public static Object executeClassMethod(Object object, String methodName, Object[] parameters) throws TemplateException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = getMethodByName(object, methodName, parameters);
        //如果有缓冲，则用缓冲方式调用
        if (method != null) {
            return method.invoke(object, parameters);
        }
        if (object instanceof Class) {
            return MethodUtils.invokeStaticMethod((Class) object, methodName, parameters);
        } else {
            return MethodUtils.invokeMethod(object, methodName, parameters);
        }
    }

    private static Method getMethodByName(Object object, String methodName, Object[] parameters) {
        Method method = null;
        Map<String, Method> stringMethodMap = methodCache.get(object.getClass());
        if (stringMethodMap != null) {
            method = stringMethodMap.get(methodName);
        }
        if (method == null) {
            List<Method> methods = getMethodList(object.getClass(), methodName);
            if (methods.size() == 1) {
                method = methods.get(0);
                if (stringMethodMap == null) {
                    stringMethodMap = new HashMap<String, Method>();
                    methodCache.put(object.getClass(), stringMethodMap);
                }
                stringMethodMap.put(methodName, method);
            } else {
                for (Method method1 : methods) {
                    if (method1.getParameterTypes().length != parameters.length) {
                        continue;
                    }
                    int count = 0;
                    for (int i = 0; i < method1.getParameterTypes().length; i++) {
                        if (parameters[i] == null || parameters[i].getClass().equals(method1.getParameterTypes()[i].getClass())) {
                            count++;
                        }
                    }
                    if (count == method1.getParameterTypes().length) {
                        return method1;
                    }
                }
            }
        }
        return method;
    }

    private static List<Method> getMethodList(Class clz, String methodName) {
        List<Method> methods = new ArrayList<Method>();
        for (Method method : clz.getMethods()) {
            if (method.getName().equals(methodName)) {
                methods.add(method);
            }
        }
        return methods;
    }

    private static Object executeExtendFunction(Template template, TemplateContext context, Object object, TemplateFunction function, Object[] parameters) throws TemplateException {
        Object[] newParameters = new Object[(parameters == null ? 1 : parameters.length) + 1];
        newParameters[0] = object;
        if (parameters != null && parameters.length > 0) {
            System.arraycopy(parameters, 0, newParameters, 1, parameters.length);
        }
        return function.execute(template, context, newParameters);
    }

    /**
     * 安全方法调用
     *
     * @param object
     * @param methodName
     * @param parameters
     * @return
     * @throws TemplateException
     */
    public static Object safeCallMethod(Template template, TemplateContext context, Object object, String methodName, Object... parameters) throws TemplateException {
        if (object != null) {
            try {
                return callMethod(template, context, object, methodName, parameters);
            } catch (TemplateException e) {
                if (e.getCause().getClass() == NoSuchMethodException.class) {
                    return null;
                } else {
                    throw e;
                }
            }
        }
        return null;
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
     * 从上下文获取对应标识的值
     *
     * @param context
     * @param key
     * @return
     */
    public static Object getValueFromContext(Context context, Object key) {
        return context.get(key.toString());
    }


    /**
     * 根据当前路径，计算新路径的绝对路径
     *
     * @param currentPath
     * @param newPath
     * @return
     */
    public static String getPath(String currentPath, String newPath) {
        String path = newPath;
        URI uri = URI.create(currentPath);
        path = path.replaceAll("[\\\\]", "/");
        URI newUri = uri.resolve(path);
        return newUri.getPath();
    }

    public static void dent(TemplateContext context) {
        Boolean compactMode = context.get("$compactMode");
        //如果没有设置或并且是紧凑模式
        if (compactMode != null && compactMode) {
            return;
        }
        Integer tab = context.get("$tab");
        if (tab != null) {
            context.put("$tab", tab + 1);
        } else {
            context.put("$tab", 1);
        }
    }

    public static void indent(TemplateContext context) {
        //如果没有设置或并且是紧凑模式
        Boolean compactMode = context.get("$compactMode");
        if (compactMode != null && compactMode) {
            return;
        }
        Integer tab = context.get("$tab");
        if (tab != null) {
            context.put("$tab", tab - 1);
        } else {
            context.put("$tab", 0);
        }
    }

    public static String getBlanks(TemplateContext context) {
        //如果没有设置或并且是紧凑模式
        Boolean compactMode = context.get("$compactMode");
        if (compactMode != null && compactMode) {
            return null;
        }
        Integer tab = context.get("$tab");
        if (tab != null) {
            if (tab < tabCache.length) {
                return tabCache[tab];
            } else {
                return tabCache[tabCache.length - 1];
            }
        }
        return null;
    }

    /**
     * 进行Html转义
     *
     * @param object
     * @return
     */
    public static String escapeHtml(Object object) {
        if (object == null) {
            return null;
        }
        return StringUtils.replaceEach(object.toString(), new String[]{"&", "\"", "<", ">"}, new String[]{"&amp;", "&quot;", "&lt;", "&gt;"});

    }

    /**
     * 判断布尔值是否成立
     *
     * @param object
     * @return
     */
    public static boolean getBooleanValue(Object object) {
        if (object == null) {
            return false;
        }
        if (object.getClass() == Boolean.class) {
            return ((Boolean) object).booleanValue();
        } else if (object.getClass() == String.class) {
            return ((String) object).length() > 0;
        } else if (object instanceof Collection) {
            return ((Collection) object).size() > 0;
        } else if (object.getClass().isArray()) {
            return ArrayUtil.arrayLength(object) > 0;
        } else if (object instanceof Iterator) {
            return ((Iterator) object).hasNext();
        } else if (object instanceof Enumerator) {
            Enumerator e = (Enumerator) object;
            return e.hasMoreElements();
        } else if (object instanceof Map) {
            Map e = (Map) object;
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
    public static Object getSafeArrayValue(Object object, Object indexObject) throws TemplateException {
        if (object == null) {
            return null;
        } else {
            try {
                return getArrayValue(object, indexObject);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static Object getArrayValue(Object object, Object indexObject) throws TemplateException {
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
            Iterator it = c.iterator();
            while (i < c.size()) {
                Object o = it.next();
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

