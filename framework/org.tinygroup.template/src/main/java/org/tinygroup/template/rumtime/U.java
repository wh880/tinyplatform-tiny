package org.tinygroup.template.rumtime;

import org.apache.commons.beanutils.BeanUtils;
import org.tinygroup.commons.tools.ArrayUtil;
import org.tinygroup.commons.tools.Enumerator;
import org.tinygroup.context.Context;
import org.tinygroup.template.TemplateException;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 工具类，之所以起这么短是为了生成的代码短一些
 * Created by luoguo on 2014/6/4.
 */
public class U {
    public static Object p(Object object, Object name) throws TemplateException {
        try {
            return BeanUtils.getProperty(object, name.toString());
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }
    /**
     * 从上下文获取对应标识的值，如果找不到，则返回字符器
     *
     * @param context
     * @param key
     * @return
     */
    public static Object c(Context context, Object key) {
        Object object = context.get(key.toString());
        if (object != null) {
            return object;
        } else {
            return key;
        }
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
     * @param index  索引值
     * @return
     * @throws Exception
     */
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

}
