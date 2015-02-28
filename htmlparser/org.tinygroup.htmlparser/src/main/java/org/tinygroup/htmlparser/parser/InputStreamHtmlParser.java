package org.tinygroup.htmlparser.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.tinygroup.htmlparser.grammer.HTMLLexer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by luoguo on 2015/2/28.
 */
public class InputStreamHtmlParser extends HtmlParser<InputStream> {
    @Override
    protected TokenStream getTokenStream(InputStream inputStream) throws IOException {
        ANTLRInputStream is = new ANTLRInputStream(inputStream);
        return new CommonTokenStream(new HTMLLexer(is));
    }
}
