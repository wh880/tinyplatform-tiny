package org.tinygroup.htmlparser.parser;

import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.tinygroup.htmlparser.HtmlDocument;
import org.tinygroup.htmlparser.HtmlNodeType;
import org.tinygroup.htmlparser.document.HtmlDocumentImpl;
import org.tinygroup.htmlparser.grammer.HTMLParser;
import org.tinygroup.htmlparser.node.HtmlNode;
import org.tinygroup.parser.Parser;

import java.io.IOException;

/**
 * Created by luoguo on 2015/2/28.
 */
public abstract class HtmlParser<Source> implements
        Parser<HtmlNode, HtmlDocument, Source> {
    HtmlDocument document;
    Object currentObject;
    protected abstract TokenStream getTokenStream(Source source) throws IOException;

    private HtmlDocument parse(TokenStream tokenStream) {
        document = new HtmlDocumentImpl();
        currentObject = document;
        HTMLParser parser = new HTMLParser(tokenStream);
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        HTMLParser.HtmlDocumentContext templateParseTree = parser.htmlDocument();
        process(templateParseTree);
        return document;
    }

    private void process(ParseTree parseTree) {
        if (parseTree instanceof HTMLParser.HtmlElementContext) {
            HTMLParser.HtmlElementContext elementContext = (HTMLParser.HtmlElementContext) parseTree;
            if(!processElementContext(elementContext)){
                return;
            }
        } else if (parseTree instanceof HTMLParser.HtmlChardataContext) {
            if (!processChardataContext(parseTree.getText())) {
                return;
            }
        } else if (parseTree instanceof TerminalNode) {
            TerminalNode terminalNode = (TerminalNode) parseTree;
            if (terminalNode.getSymbol().getType() == HTMLParser.CDATA) {
                processCData(terminalNode.getText());
                return;
            } else {
                return;
            }
        }
        if (parseTree.getChildCount() > 0) {
            for (int i = 0; i < parseTree.getChildCount(); i++) {
                process(parseTree.getChild(i));
            }
        }
    }

    private boolean processCData(String content) {
        HtmlNode xmlNode = new HtmlNode(HtmlNodeType.CDATA);
        String cdata = content;
        cdata = cdata.substring(9, cdata.length() - 3).trim();
        xmlNode.setContent(cdata);
        addHtmlNode(xmlNode, false);
        return false;
    }


    private boolean processChardataContext(String text) {
        HtmlNode currentNode = ((HtmlNode) currentObject);
        if (currentNode.getSubNodes().size() > 0 && currentNode.getSubNodes().get(currentNode.getSubNodes().size() - 1).getNodeType() == HtmlNodeType.TEXT) {
            HtmlNode textNode = currentNode.getSubNodes().get(currentNode.getSubNodes().size() - 1);
            textNode.setContent(textNode.getContent() + currentNode.decode(text));
        } else {
            HtmlNode xmlNode = new HtmlNode(HtmlNodeType.TEXT);
            xmlNode.setContent(xmlNode.decode(text));
            addHtmlNode(xmlNode, false);
        }
        return false;
    }

    private boolean processAttributeContext(HTMLParser.HtmlAttributeContext attributeContext, HtmlNode node) {
        String name = attributeContext.htmlAttributeName().getText();
        if(attributeContext.htmlAttributeValue()==null){
            node.setSingleAttribute(name);
        }else {
            String value = attributeContext.htmlAttributeValue().getText();
            if (value.startsWith("\"") || value.startsWith("'")) {
                value = value.substring(1, value.length() - 1);
            }
            node.setAttribute(name, node.decode(value));
        }
        return false;
    }

    private boolean processElementContext(HTMLParser.HtmlElementContext elementContext) {
        if(elementContext.getChildCount()==0){
            return false;
        }
        Object p = currentObject;
        HtmlNode htmlNode = new HtmlNode(elementContext.getChild(1).getText());
        //处理属性
        for (HTMLParser.HtmlAttributeContext attribute : elementContext.htmlAttribute()) {
            processAttributeContext(attribute, htmlNode);
        }
        addHtmlNode(htmlNode, !htmlNode.isSingleNode());
        //如果有子节点就去处理

        if (elementContext.htmlContent() != null) {
            if (htmlNode.isSingleNode()) {
                //如果不是单标签，则继续处理子节点
                //否则给他爹处理
                currentObject = p;
            }
            process(elementContext.htmlContent());
        }
        currentObject = p;
        return false;
    }

    private void addHtmlNode(HtmlNode xmlNode, boolean changeCurrentNode) {
        if (currentObject instanceof HtmlNode) {
            ((HtmlNode) currentObject).addNode(xmlNode);
        } else {
            HtmlDocument htmlDocument = (HtmlDocument) currentObject;
            if (xmlNode.getNodeType().equals(HtmlNodeType.ELEMENT)) {
                if(htmlDocument.getRoot()==null) {
                    htmlDocument.setRoot(xmlNode);
                }else{
                    HtmlNode root=new HtmlNode(HtmlNodeType.ELEMENT);
                    root.getSubNodes().add(htmlDocument.getRoot());
                    root.addNode(xmlNode);
                    document.setRoot(root);
                }
            } else if (xmlNode.getNodeType().equals(HtmlNodeType.COMMENT)) {
                htmlDocument.addComment(xmlNode);
            } else if (xmlNode.getNodeType().equals(HtmlNodeType.DOCTYPE)) {
                htmlDocument.addDoctype(xmlNode);
            } else if (xmlNode.getNodeType().equals(HtmlNodeType.PROCESSING_INSTRUCTION)) {
                htmlDocument.addProcessingInstruction(xmlNode);
            }
        }
        if (changeCurrentNode) {
            currentObject = xmlNode;
        }
    }

    @Override
    public HtmlDocument parse(Source source) {
        TokenStream tokenStream= null;
        try {
            tokenStream = getTokenStream(source);
            return parse(tokenStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public HtmlNode parseNode(Source source) {
        return parse(source).getRoot();
    }
}
