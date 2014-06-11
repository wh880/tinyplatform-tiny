package org.tinygroup.template.rumtime.convert;

import org.tinygroup.template.rumtime.Converter;

import java.math.BigDecimal;

/**
 * Created by luoguo on 2014/6/5.
 */
public class FloatBigDecimal implements Converter<Float,BigDecimal> {

    public BigDecimal convert(Float object) {
        return new BigDecimal(object);
    }

    public String getType() {
        return "java.lang.Floatjava.math.BigDecimal";
    }
}
