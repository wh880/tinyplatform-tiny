package org.tinygroup.template.impl.convert;

import org.tinygroup.template.Converter;

/**
 * Created by luoguo on 2014/6/5.
 */
public class CharacterDouble implements Converter<Character,Double> {

    public Double convert(Character object) {
        return (double)object.charValue();
    }

    public String getType() {
        return "FloatDouble";
    }
}
