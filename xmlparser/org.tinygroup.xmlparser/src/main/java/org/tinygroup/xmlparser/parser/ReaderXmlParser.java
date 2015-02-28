package org.tinygroup.xmlparser.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.tinygroup.xmlparser.grammer.XMLLexer;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by luoguo on 2015/2/28.
 */
public class ReaderXmlParser extends XmlParser<Reader> {
    @Override
    protected TokenStream getTokenStream(Reader reader) throws IOException {
        ANTLRInputStream is = new ANTLRInputStream(reader);
        return new CommonTokenStream(new XMLLexer(is));
    }
}
