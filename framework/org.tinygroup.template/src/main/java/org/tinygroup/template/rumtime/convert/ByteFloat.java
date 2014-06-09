package org.tinygroup.template.rumtime.convert;

import org.tinygroup.template.rumtime.Converter;

/**
 * Created by luoguo on 2014/6/5.
 */
public class ByteFloat implements Converter<Byte, Float> {

    public Float convert(Byte object) {
        return (float) object.byteValue();
    }

    public String getType() {
        return "java.lang.Bytejava.lang.Float";
    }
}
