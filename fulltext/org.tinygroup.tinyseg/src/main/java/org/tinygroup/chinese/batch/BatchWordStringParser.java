package org.tinygroup.chinese.batch;

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
public class BatchWordStringParser implements WordParser<BatchToken,String> {

    BatchWordReaderParser batchWordReaderParser =new BatchWordReaderParser();

    public void parse(WordParserManager wordParserManager, String string,WordParserType wordParserType, WordParserMode wordParserMode) throws IOException {
        batchWordReaderParser.parse(wordParserManager, new CharArrayReader(string.toCharArray()), wordParserType, wordParserMode);
    }

    public Collection<BatchToken> tokens() {
        return batchWordReaderParser.tokens();
    }

    public BatchToken nextToken() {
        return batchWordReaderParser.nextToken();
    }

    public List<BatchToken> nextSentenceTokens() throws IOException {
        return null;
    }
}
