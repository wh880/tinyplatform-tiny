package org.tinygroup.template.rumtime.convert;

import org.tinygroup.template.rumtime.Converter;

/**
 * Created by luoguo on 2014/6/5.
 */
public class ByteCharacter implements Converter<Byte,Character> {

    public Character convert(Byte object) {
        return (char)object.byteValue();
    }

    public String getType() {
        return "java.lang.Bytejava.lang.Character";
    }
}
