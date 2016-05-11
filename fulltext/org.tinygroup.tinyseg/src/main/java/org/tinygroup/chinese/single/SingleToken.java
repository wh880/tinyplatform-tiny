package org.tinygroup.chinese.single;

import org.tinygroup.chinese.Token;
import org.tinygroup.chinese.TokenType;

/**
 * BatchToken 用于在分词时返回Token的情况
 * Created by luog on 15/4/13.
 */
public class SingleToken implements Token {
    /**
     * 行
     */
    int line;
    /**
     * 开始列
     */
    int start;
    /**
     * 结束列
     */
    int end;

    /**
     * 文本内容
     */
    String word;
    /**
     * 类型
     */
    TokenType tokenType;

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getWord() {
        return word;
    }

    public SingleToken(String word, TokenType tokenType, int line, int start, int end) {
        this.word = word;
        this.tokenType = tokenType;
        this.line = line;
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getLine() {
        return line;
    }

    public String toString() {
        return word + ":" + tokenType;
    }
}
