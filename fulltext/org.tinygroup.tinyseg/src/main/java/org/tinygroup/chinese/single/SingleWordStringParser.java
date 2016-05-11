package org.tinygroup.chinese.single;

import org.tinygroup.chinese.WordParser;
import org.tinygroup.chinese.WordParserManager;
import org.tinygroup.chinese.WordParserMode;
import org.tinygroup.chinese.WordParserType;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by luog on 15/4/13.
 */
public class SingleWordStringParser implements WordParser<SingleToken, String> {
    SingleWordReaderParser singleWordReaderParser=new SingleWordReaderParser() ;

    public void parse(WordParserManager wordParserManager, String string,WordParserType wordParserType, WordParserMode wordParserMode) {
        singleWordReaderParser.parse(wordParserManager, new CharArrayReader(string.toCharArray()), wordParserType, wordParserMode);
    }



    public Collection<SingleToken> tokens() throws IOException {
        return singleWordReaderParser.tokens();
    }

    public SingleToken nextToken() throws IOException {
        return singleWordReaderParser.nextToken();
    }

    public List<SingleToken> nextSentenceTokens() throws IOException {
        return singleWordReaderParser.nextSentenceTokens();
    }

}
