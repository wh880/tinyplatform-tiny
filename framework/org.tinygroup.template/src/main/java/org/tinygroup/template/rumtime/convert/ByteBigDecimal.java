package org.tinygroup.template.rumtime.convert;

import org.tinygroup.template.rumtime.Converter;

import java.math.BigDecimal;

/**
 * Created by luoguo on 2014/6/5.
 */
public class ByteBigDecimal implements Converter<Byte,BigDecimal> {

    public BigDecimal convert(Byte object) {
        return new BigDecimal((int)object.byteValue());
    }

    public String getType() {
        return "FloatDouble";
    }
}
