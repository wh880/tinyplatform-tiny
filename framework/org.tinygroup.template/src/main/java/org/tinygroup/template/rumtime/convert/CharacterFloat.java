package org.tinygroup.template.rumtime.convert;

import org.tinygroup.template.rumtime.Converter;

/**
 * Created by luoguo on 2014/6/5.
 */
public class CharacterFloat implements Converter<Character,Float> {

    public Float convert(Character object) {
        return (float)object.charValue();
    }

    @Override
    public Class getSourceType() {
        return Character.class;
    }

    @Override
    public Class getDestType() {
        return Float.class;
    }
}
