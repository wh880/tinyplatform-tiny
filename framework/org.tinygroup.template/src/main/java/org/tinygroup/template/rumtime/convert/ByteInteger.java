package org.tinygroup.template.rumtime.convert;

import org.tinygroup.template.rumtime.Converter;

/**
 * Created by luoguo on 2014/6/5.
 */
public class ByteInteger implements Converter<Byte, Integer> {

    public Integer convert(Byte object) {
        return (int) object.byteValue();
    }


    public Class getSourceType() {
        return Byte.class;
    }


    public Class getDestType() {
        return Integer.class;
    }
}
