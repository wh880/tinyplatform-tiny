package org.tinygroup.template;

import org.tinygroup.commons.io.ByteArrayInputStream;

/**
 * Created by luoguo on 2014/5/8.
 */
public class TestSimple3 {
    public static void main(String[] args) throws ParseException {
//        TinyTemplateParser simple3=new TinyTemplateParser(new ByteArrayInputStream("abc \\#abc $aef # $ #{end}abcd 123 #[[$abc #abc ]]# #*#aaa $abc*# $abc #@abc #abc #{abc} #@{abc} #saa3".getBytes()));
//        TinyTemplateParser simple3=new TinyTemplateParser(new ByteArrayInputStream("#if(true||true) aaa#else bbb#end".getBytes()));
//        TinyTemplateParser simple3=new TinyTemplateParser(new ByteArrayInputStream("'a\\'\"bc' \"aaa'bb\"".getBytes()));
        TinyTemplateParser simple3=new TinyTemplateParser(new ByteArrayInputStream("$abc.aa.cc.$def".getBytes()));
        simple3.parse();
    }
}
