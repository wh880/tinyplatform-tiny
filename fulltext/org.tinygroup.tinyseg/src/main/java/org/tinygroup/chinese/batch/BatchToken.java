package org.tinygroup.chinese.batch;

import org.tinygroup.chinese.Token;
import org.tinygroup.chinese.TokenType;
import org.tinygroup.chinese.single.SingleToken;

/**
 * Created by luog on 15/4/13.
 */
public class BatchToken implements Token {
    /**
     * 出现次数
     */
    private int count;
    SingleToken singleToken;

    public BatchToken(SingleToken singleToken) {
        this.singleToken = singleToken;
        this.count = 1;
    }

    public BatchToken(SingleToken singleToken, int count) {
        this.singleToken = singleToken;
        this.count = count;
    }

    public SingleToken getSingleToken() {
        return singleToken;
    }

    public void setSingleToken(SingleToken singleToken) {
        this.singleToken = singleToken;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public TokenType getTokenType() {
        return singleToken.getTokenType();
    }

    public String getWord() {
        return singleToken.getWord();
    }
}
