package org.tinygroup.template.impl.convert;

import org.tinygroup.template.Converter;

/**
 * Created by luoguo on 2014/6/5.
 */
public class ByteCharacter implements Converter<Byte,Character> {

    public Character convert(Byte object) {
        return (char)object.byteValue();
    }

    public String getType() {
        return "FloatDouble";
    }
}
