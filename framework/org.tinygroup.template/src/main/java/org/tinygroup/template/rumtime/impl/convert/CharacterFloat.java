package org.tinygroup.template.rumtime.impl.convert;

import org.tinygroup.template.rumtime.Converter;

/**
 * Created by luoguo on 2014/6/5.
 */
public class CharacterFloat implements Converter<Character,Float> {

    public Float convert(Character object) {
        return (float)object.charValue();
    }

    public String getType() {
        return "Float2Float";
    }
}