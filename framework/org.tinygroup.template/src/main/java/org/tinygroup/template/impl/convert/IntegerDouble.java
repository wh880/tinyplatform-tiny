package org.tinygroup.template.impl.convert;

import org.tinygroup.template.Converter;

/**
 * Created by luoguo on 2014/6/5.
 */
public class IntegerDouble implements Converter<Integer,Double> {
    public Double convert(Integer object) {
        return object.doubleValue();
    }

    public String getType() {
        return "IntegerDouble";
    }
}
