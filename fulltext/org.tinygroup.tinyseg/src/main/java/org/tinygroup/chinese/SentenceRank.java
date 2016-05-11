package org.tinygroup.chinese;

import java.util.List;

/**
 * 对句子进行评价
 * Created by luog on 15/4/20.
 */
public interface SentenceRank {
    void setWordParserManager(WordParserManager wordParserManager);
    int rank(List<Token> tokenList);
}
