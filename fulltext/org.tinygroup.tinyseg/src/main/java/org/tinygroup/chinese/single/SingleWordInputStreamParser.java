package org.tinygroup.chinese.single;

import org.tinygroup.chinese.WordParser;
import org.tinygroup.chinese.WordParserManager;
import org.tinygroup.chinese.WordParserMode;
import org.tinygroup.chinese.WordParserType;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

/**
 * Created by luog on 15/4/13.
 */
public class SingleWordInputStreamParser implements WordParser<SingleToken, InputStream> {
    private String charset="UTF-8";
    SingleWordReaderParser singleWordReaderParser = new SingleWordReaderParser();

    public SingleWordInputStreamParser() {

    }

    public SingleWordInputStreamParser(String charset) {
        this.charset = charset;

    }

    public void parse(WordParserManager manager, InputStream inputStream, WordParserType wordParserType, WordParserMode wordParserMode) throws UnsupportedEncodingException {
        singleWordReaderParser.parse(manager, new InputStreamReader(inputStream, charset), wordParserType, wordParserMode);
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
