package org.tinygroup.chinese;

/**
 * Created by luog on 15/4/13.
 */
public interface Token {
    /**
     * 返回类型
     * @return
     */
    TokenType getTokenType();

    /**
     * 返回文本
     * @return
     */
    String getWord();

}
