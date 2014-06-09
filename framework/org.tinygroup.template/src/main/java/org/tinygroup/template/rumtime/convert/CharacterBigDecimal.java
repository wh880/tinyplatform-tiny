package org.tinygroup.template.rumtime.convert;

import org.tinygroup.template.rumtime.Converter;

import java.math.BigDecimal;

/**
 * Created by luoguo on 2014/6/5.
 */
public class CharacterBigDecimal implements Converter<Character,BigDecimal> {

    public BigDecimal convert(Character object) {
        return new BigDecimal((int)object.charValue());
    }

    public String getType() {
        return "FloatDouble";
    }
}
