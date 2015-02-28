package org.tinygroup.xmlparser.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.tinygroup.xmlparser.grammer.XMLLexer;

/**
 * Created by luoguo on 2015/2/28.
 */
public class StringXmlParser extends XmlParser<String> {
    protected TokenStream getTokenStream(String string) {
        char[] source = string.toCharArray();
        ANTLRInputStream is = new ANTLRInputStream(source, source.length);
        return new CommonTokenStream(new XMLLexer(is));
    }
}
