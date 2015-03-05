package org.tinygroup.urlrestful;

/**
 * Created by luoguo on 2015/3/5.
 */
public interface ValueParser {
    boolean isMatch(String parameter);

    String[] parse(String parameter);
}
