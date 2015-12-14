package org.tinygroup.template.trim;

import junit.framework.TestCase;
import org.tinygroup.commons.io.ByteArrayOutputStream;
import org.tinygroup.commons.tools.FileUtil;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.impl.TemplateContextDefault;
import org.tinygroup.template.impl.TemplateEngineDefault;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.FileObjectProcessor;
import org.tinygroup.vfs.VFS;
import org.tinygroup.vfs.impl.filter.FileNameFileObjectFilter;

/**
 * Created by BYSocket on 2015/12/1.
 */
public class TrimCommentsTest extends TestCase {

    final TemplateEngine engine = new TemplateEngineDefault();
    FileObjectResourceLoader trimPageRS =
            new FileObjectResourceLoader("page", null, null, "src/test/resources/trim");
    FileObject fileObject = VFS.resolveFile("src/test/resources/trim");

    public void setUp() {
        engine.setCompactMode(true);
        engine.addResourceLoader(trimPageRS);
    }

    /**
     * Trim 注释、Set指令和以下指令生成的多余\r\n:
     * #if #else #elseif #end #eol ${}
     * #for #foreach #while #break #continue #stop
     * #include #@call
     */
    public void testCommentAll() {
        fileObject.foreach(
                new FileNameFileObjectFilter("allnewline\\.page", true), new FileObjectProcessor() {
            public void process(FileObject fileObject) {
                try {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    engine.renderTemplate(fileObject.getPath(), new TemplateContextDefault(), outputStream);
                    String result = new String(outputStream.toByteArray().toByteArray(), "UTF-8");
                    //System.out.println(result);
                    String expectResult = FileUtil.readStreamContent(VFS.resolveFile(fileObject.getAbsolutePath() + ".txt").getInputStream(), "UTF-8");
                    assertEquals(result, expectResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
