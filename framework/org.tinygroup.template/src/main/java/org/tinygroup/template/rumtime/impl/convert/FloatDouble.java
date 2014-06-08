package org.tinygroup.template.rumtime.impl.convert;

import org.tinygroup.template.rumtime.Converter;

/**
 * Created by luoguo on 2014/6/5.
 */
public class FloatDouble implements Converter<Float,Double> {

    public Double convert(Float object) {
        return object.doubleValue();
    }

    public String getType() {
        return "Double2Float";
    }
}