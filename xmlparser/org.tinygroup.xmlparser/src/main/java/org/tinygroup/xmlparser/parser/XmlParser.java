package org.tinygroup.xmlparser.parser;

import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.tinygroup.parser.Parser;
import org.tinygroup.xmlparser.XmlDocument;
import org.tinygroup.xmlparser.XmlNodeType;
import org.tinygroup.xmlparser.document.XmlDocumentImpl;
import org.tinygroup.xmlparser.grammer.XMLParser;
import org.tinygroup.xmlparser.node.XmlNode;

import java.io.IOException;

/**
 * Created by luoguo on 2015/2/28.
 */
public abstract class XmlParser<Source> implements
        Parser<XmlNode, XmlDocument, Source> {
    XmlDocument document;
    Object currentObject;
    protected abstract TokenStream getTokenStream(Source source) throws IOException;

    private XmlDocument parse(TokenStream tokenStream) {
        document = new XmlDocumentImpl();
        currentObject = document;
        XMLParser parser = new XMLParser(tokenStream);
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        XMLParser.DocumentContext templateParseTree = parser.document();
        process(templateParseTree);
        return document;
    }

    private void process(ParseTree parseTree) {
        if (parseTree instanceof XMLParser.ElementContext) {
            XMLParser.ElementContext elementContext = (XMLParser.ElementContext) parseTree;
            processElementContext(elementContext);
            return;
        } else if (parseTree instanceof XMLParser.ChardataContext || parseTree instanceof XMLParser.ReferenceContext) {
            if (!processChardataContext(parseTree.getText())) {
                return;
            }
        } else if (parseTree instanceof XMLParser.PrologContext) {
            XMLParser.PrologContext prologContext = (XMLParser.PrologContext) parseTree;
            if (!processPrologContext(prologContext)) {
                return;
            }
        } else if (parseTree instanceof XMLParser.DeclareContext) {
            XMLParser.DeclareContext declareContext = (XMLParser.DeclareContext) parseTree;
            if (!processDeclareContext(declareContext)) {
                return;
            }
        } else if (parseTree instanceof TerminalNode) {
            TerminalNode terminalNode = (TerminalNode) parseTree;
            if (terminalNode.getSymbol().getType() == XMLParser.CDATA) {
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

    private boolean processPrologContext(XMLParser.PrologContext prologContext) {
        XmlNode xmlNode = new XmlNode(XmlNodeType.XML_DECLARATION);
        addXmlNode(xmlNode, true);
        for (int i = 0; i < prologContext.getChildCount(); i++) {
            ParseTree child = prologContext.getChild(i);
            if (child instanceof XMLParser.AttributeContext) {
                XMLParser.AttributeContext attributeContext = (XMLParser.AttributeContext) child;
                processAttributeContext(attributeContext,xmlNode);
            }
        }
        currentObject = document;
        return false;
    }

    private boolean processDeclareContext(XMLParser.DeclareContext declareContext) {
        XmlNode xmlNode = new XmlNode(XmlNodeType.PROCESSING_INSTRUCTION);
        addXmlNode(xmlNode, true);
        for (int i = 0; i < declareContext.getChildCount(); i++) {
            ParseTree child = declareContext.getChild(i);
            if (child instanceof XMLParser.AttributeContext) {
                XMLParser.AttributeContext attributeContext = (XMLParser.AttributeContext) child;
                processAttributeContext(attributeContext,xmlNode);
            }
        }
        currentObject = document;
        return false;
    }

    private boolean processCData(String content) {
        XmlNode xmlNode = new XmlNode(XmlNodeType.CDATA);
        String cdata = content;
        cdata = cdata.substring(9, cdata.length() - 3).trim();
        xmlNode.setContent(cdata);
        addXmlNode(xmlNode, false);
        return false;
    }


    private boolean processChardataContext(String text) {
        XmlNode currentNode = ((XmlNode) currentObject);
        if (currentNode.getSubNodes().size() > 0&&currentNode.getSubNodes().get(currentNode.getSubNodes().size()-1).getNodeType()==XmlNodeType.TEXT) {
            XmlNode textNode = currentNode.getSubNodes().get(currentNode.getSubNodes().size() - 1);
            textNode.setContent(textNode.getContent()+currentNode.decode(text));
        } else {
            XmlNode xmlNode = new XmlNode(XmlNodeType.TEXT);
            xmlNode.setContent(xmlNode.decode(text));
            addXmlNode(xmlNode, false);
        }
        return false;
    }

    private boolean processAttributeContext(XMLParser.AttributeContext elementContext,XmlNode xmlNode) {
        String value = elementContext.getChild(2).getText();
        value = value.substring(1, value.length() - 1);
        xmlNode.setAttribute(elementContext.getChild(0).getText(), xmlNode.decode(value));
        return false;
    }

    private boolean processElementContext(XMLParser.ElementContext elementContext) {
            Object p = currentObject;
            XmlNode xmlNode = new XmlNode(elementContext.getChild(1).getText());
            //处理属性
            for (XMLParser.AttributeContext attribute : elementContext.attribute()) {
                processAttributeContext(attribute, xmlNode);
            }
            addXmlNode(xmlNode, !xmlNode.isSingleNode());
            //如果有子节点就去处理

            if (elementContext.content()!= null) {
                process(elementContext.content());
            }
            currentObject = p;
            return false;
    }

    private void addXmlNode(XmlNode xmlNode, boolean changeCurrentNode) {
        if (currentObject instanceof XmlNode) {
            ((XmlNode) currentObject).addNode(xmlNode);
        } else {
            if (xmlNode.getNodeType().equals(XmlNodeType.ELEMENT)) {
                ((XmlDocument) currentObject).setRoot(xmlNode);
            } else if (xmlNode.getNodeType().equals(XmlNodeType.COMMENT)) {
                ((XmlDocument) currentObject).addComment(xmlNode);
            } else if (xmlNode.getNodeType().equals(XmlNodeType.DOCTYPE)) {
                ((XmlDocument) currentObject).addDoctype(xmlNode);
            } else if (xmlNode.getNodeType().equals(XmlNodeType.PROCESSING_INSTRUCTION)) {
                ((XmlDocument) currentObject).addProcessingInstruction(xmlNode);
            } else if (xmlNode.getNodeType().equals(XmlNodeType.XML_DECLARATION)) {
                ((XmlDocument) currentObject).setXmlDeclaration(xmlNode);
            }
        }
        if (changeCurrentNode) {
            currentObject = xmlNode;
        }
    }

    @Override
    public XmlDocument parse(Source source) {
        TokenStream tokenStream= null;
        try {
            tokenStream = getTokenStream(source);
            return parse(tokenStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public XmlNode parseNode(Source source) {
       return parse(source).getRoot();
    }
}
