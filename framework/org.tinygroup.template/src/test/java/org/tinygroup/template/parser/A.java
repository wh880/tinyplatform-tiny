package org.tinygroup.template.parser;

import org.tinygroup.template.JetContext;
import org.tinygroup.template.JetTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
public class A implements JetTemplate{
    public void render(Writer writer, JetContext context) throws IOException{
        writer.write("abc\ndef");
    }
    public String getTemplatePath(){
        return "abc";
    }

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        Writer writer=new OutputStreamWriter(outputStream);
        A a=new A();
        a.render(writer,null);
        writer.close();
        System.out.println(outputStream.toString());
    }
}