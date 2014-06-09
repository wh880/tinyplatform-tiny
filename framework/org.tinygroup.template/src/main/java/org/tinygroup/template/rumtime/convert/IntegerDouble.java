package org.tinygroup.template.rumtime.convert;

import org.tinygroup.template.rumtime.Converter;

/**
 * Created by luoguo on 2014/6/5.
 */
public class IntegerDouble implements Converter<Integer,Double> {
    public Double convert(Integer object) {
        return object.doubleValue();
    }

    public String getType() {
        return "java.lang.Integerjava.lang.Double";
    }
}
