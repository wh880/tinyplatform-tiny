package org.tinygroup.tinyspider;

import java.io.*;

/**
 * Created by luoguo on 2014/5/28.
 */
public class OSSiteMapGen {
    public static void main(String[] args) throws IOException {
        File file=new File(OSchinaSpider.outoutPath);
        OutputStream out=new FileOutputStream(OSchinaSpider.outoutPath+"sitemap.xml");
        for(File f:file.listFiles()){
            out.write("<url>\n".getBytes());
            out.write(("<loc>http://www.tinygroup.org/tinysite/tinysite/topics/"+f.getName()+"</loc>\n").getBytes());
            out.write("<lastmod>2014-05-28</lastmod>\n".getBytes());
            out.write("<changefreq>weekly</changefreq>\n".getBytes());
            out.write("<priority>0.8</priority>\n".getBytes());
            out.write("</url>\n".getBytes());
        }
        out.close();
    }
}
