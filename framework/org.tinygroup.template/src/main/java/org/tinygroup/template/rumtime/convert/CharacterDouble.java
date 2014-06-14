package org.tinygroup.template.rumtime.convert;

import org.tinygroup.template.rumtime.Converter;


/**
 * Created by luoguo on 2014/6/5.
 */
public class CharacterDouble implements Converter<Character,Double> {

    public Double convert(Character object) {
        return (double)object.charValue();
    }


    public Class getSourceType() {
        return Character.class;
    }


    public Class getDestType() {
        return Double.class;
    }
}
