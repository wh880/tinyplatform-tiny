package org.tinygroup.chinese.batch;

import org.tinygroup.chinese.WordParser;
import org.tinygroup.chinese.WordParserManager;
import org.tinygroup.chinese.WordParserMode;
import org.tinygroup.chinese.WordParserType;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;

/**
 * Created by luog on 15/4/13.
 */
public class BatchWordInputStreamParser implements WordParser<BatchToken, InputStream> {

    private String charset = "UTF-8";
    BatchWordReaderParser batchWordReaderParser = new BatchWordReaderParser();

    public BatchWordInputStreamParser() {

    }

    public BatchWordInputStreamParser(String charset) {
        this.charset = charset;

    }

    public void parse(WordParserManager manager, InputStream inputStream, WordParserType wordParserType, WordParserMode wordParserMode) throws IOException {
        batchWordReaderParser.parse(manager, new InputStreamReader(inputStream, charset), wordParserType, wordParserMode);
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
