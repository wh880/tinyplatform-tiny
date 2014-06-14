package org.tinygroup.template.rumtime.convert;

import org.tinygroup.template.rumtime.Converter;

/**
 * Created by luoguo on 2014/6/5.
 */
public class CharacterInteger implements Converter<Character,Integer> {

    public Integer convert(Character object) {
        return (int)object.charValue();
    }


    public Class getSourceType() {
        return Character.class;
    }


    public Class getDestType() {
        return Integer.class;
    }
}
