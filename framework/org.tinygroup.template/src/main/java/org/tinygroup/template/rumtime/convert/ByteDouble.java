package org.tinygroup.template.rumtime.convert;

import org.tinygroup.template.rumtime.Converter;

/**
 * Created by luoguo on 2014/6/5.
 */
public class ByteDouble implements Converter<Byte, Double> {

    public Double convert(Byte object) {
        return (double) object.byteValue();
    }


    public Class getSourceType() {
        return Byte.class;
    }


    public Class getDestType() {
        return Double.class;
    }
}
