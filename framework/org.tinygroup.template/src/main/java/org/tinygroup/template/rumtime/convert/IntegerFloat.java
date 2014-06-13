package org.tinygroup.template.rumtime.convert;

import org.tinygroup.template.rumtime.Converter;

/**
 * Created by luoguo on 2014/6/5.
 */
public class IntegerFloat implements Converter<Integer,Float> {
    public Float convert(Integer object) {
        return object.floatValue();
    }

    @Override
    public Class getSourceType() {
        return Integer.class;
    }

    @Override
    public Class getDestType() {
        return Float.class;
    }
}
