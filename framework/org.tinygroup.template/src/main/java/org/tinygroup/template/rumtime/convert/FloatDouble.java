package org.tinygroup.template.rumtime.convert;

import org.tinygroup.template.rumtime.Converter;


/**
 * Created by luoguo on 2014/6/5.
 */
public class FloatDouble implements Converter<Float,Double> {

    public Double convert(Float object) {
        return object.doubleValue();
    }


    public Class getSourceType() {
        return Float.class;
    }


    public Class getDestType() {
        return Double.class;
    }
}