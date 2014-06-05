package org.tinygroup.template;

import org.apache.commons.beanutils.BeanUtils;
import org.tinygroup.commons.tools.ArrayUtil;
import org.tinygroup.commons.tools.Enumerator;
import org.tinygroup.context.Context;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by luoguo on 2014/6/4.
 */
public class U {
    public static Object p(Object object, String name) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return BeanUtils.getProperty(object, name);
    }

    public static Object c(Context context, String key) {
        Object object = context.get(key);
        if (object != null) {
            return object;
        } else {
            return key;
        }
    }

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
        } else if(o instanceof Iterator){
            Iterator i= (Iterator) o;
            return ((Iterator) o).hasNext();
        } else if(o instanceof Enumerator){
            Enumerator e= (Enumerator) o;
            return e.hasMoreElements();
        } else if(o instanceof Map){
            Map e= (Map )o;
            return e.size()>0;
        }
        return true;
    }

    public static Object a(Object object, int index) throws Exception {
        if (object.getClass().isArray()) {
            return Array.get(object, index);
        } else if (object instanceof Map) {
            Map map = (Map) object;
            return a(map.entrySet(), index);
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

        throw new Exception(object.getClass().getName() + "不可以用下标方式取值。");
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> map=new HashMap<String, Object>();
        Object o=map.put("a",1);
    }

    private static void processArray(Object abc) {
        System.out.println(ArrayUtil.arrayLength(abc));
    }
}
