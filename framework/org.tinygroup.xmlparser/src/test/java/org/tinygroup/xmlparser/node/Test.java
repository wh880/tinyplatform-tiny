package org.tinygroup.xmlparser.node;

import org.tinygroup.commons.file.IOUtils;
import org.tinygroup.xmlparser.parser.XmlStringParser;

import java.io.FileInputStream;

/**
 * Created by luoguo on 14-3-5.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        XmlNode root = new XmlStringParser().parse(IOUtils.readFromInputStream(new FileInputStream("E:\\test\\MyJavaBean.xml"), "UTF-8")).getRoot();
        StringBuffer stringBuffer=new StringBuffer("class ");
        stringBuffer.append(root.getNodeName()).append("{\n");
        for(XmlNode subNode:root.getSubNodes()){
            stringBuffer.append("\tString ").append(subNode.getNodeName()).append(";\n");
            //如果要生成get set，下面继续append
        }
        stringBuffer.append("}\n");
        System.out.println(stringBuffer);
    }
}
