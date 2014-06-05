package org.tinygroup.template.impl.convert;

import org.tinygroup.template.Converter;

import java.math.BigDecimal;

/**
 * Created by luoguo on 2014/6/5.
 */
public class FloatBigDecimal implements Converter<Float,BigDecimal> {

    public BigDecimal convert(Float object) {
        return new BigDecimal(object);
    }

    public String getType() {
        return "BigDecimal2Float";
    }
}
