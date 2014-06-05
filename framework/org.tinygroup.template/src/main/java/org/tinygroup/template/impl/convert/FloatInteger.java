package org.tinygroup.template.impl.convert;

import org.tinygroup.template.Converter;

/**
 * Created by luoguo on 2014/6/5.
 */
public class FloatInteger implements Converter<Float,Integer> {

    public Integer convert(Float object) {
        return object.intValue();
    }

    public String getType() {
        return "IntegerFloat";
    }
}
