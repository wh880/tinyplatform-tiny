package org.tinygroup.template.rumtime.impl.convert;

import org.tinygroup.template.rumtime.Converter;

import java.math.BigDecimal;

/**
 * Created by luoguo on 2014/6/5.
 */
public class DoubleBigDecimal implements Converter<Double,BigDecimal> {

    public BigDecimal convert(Double object) {
        return new BigDecimal(object);
    }

    public String getType() {
        return "DoubleBigDecimal";
    }
}
