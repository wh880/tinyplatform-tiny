package org.tinygroup.template;

/**
 * Created by luoguo on 2014/6/5.
 */
public interface Converter<S, D> {
    D convert(S object);
}
