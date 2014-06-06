package org.tinygroup.template.parser;

import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.Template;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
public class A implements Template {
    public void render(TemplateContext context, Writer writer) throws IOException{
        writer.write("abc\ndef");
    }
    public String getPath(){
        return "abc";
    }

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        Writer writer=new OutputStreamWriter(outputStream);
        A a=new A();
        a.render(null, writer);
        writer.close();
        System.out.println(outputStream.toString());
    }
}