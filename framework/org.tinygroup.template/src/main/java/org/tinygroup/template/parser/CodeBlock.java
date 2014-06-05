package org.tinygroup.template.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoguo on 2014/6/3.
 */
public class CodeBlock {
    private CodeBlock parentCodeBlock;
    private CodeLet headerCodeLet;
    private CodeLet footerCodeLet;
    private List<CodeBlock> subCodeBlocks;
    private int tabIndent = 0;

    public CodeBlock getParentCodeBlock() {
        return parentCodeBlock;
    }

    public int getTabIndent() {
        return tabIndent;
    }

    public CodeBlock tabIndent(int tabIndent) {
        this.tabIndent = tabIndent;
        return this;
    }
    public CodeBlock setTabIndent(int tabIndent) {
        this.tabIndent = tabIndent;
        return this;
    }
    public CodeBlock setParentCodeBlock(CodeBlock parentCodeBlock) {
        this.parentCodeBlock = parentCodeBlock;
        return this;
    }


    public CodeBlock setHeaderCodeLet(CodeLet headerCodeLet) {
        this.headerCodeLet = headerCodeLet;
        return this;
    }

    public CodeBlock setFooterCodeLet(CodeLet footerCodeLet) {
        this.footerCodeLet = footerCodeLet;
        return this;
    }

    public CodeBlock setSubCodeBlocks(List<CodeBlock> subCodeBlocks) {
        this.subCodeBlocks = subCodeBlocks;
        for (CodeBlock subCodeBlock : subCodeBlocks) {
            subCodeBlock.setParentCodeBlock(this);
        }
        return this;
    }

    public List<CodeBlock> getSubCodeBlocks() {
        return subCodeBlocks;
    }

    public CodeBlock addSubCodeBlock(CodeBlock codeBlock) {
        if (codeBlock != null) {
            if (subCodeBlocks == null) {
                subCodeBlocks = new ArrayList<CodeBlock>();
            }
            subCodeBlocks.add(codeBlock);
            codeBlock.setParentCodeBlock(this);
        }
        return this;
    }

    public CodeBlock addSubCodeLet(CodeLet codeLet) {
        if (codeLet != null) {
            CodeBlock codeBlock = new CodeBlock();
            codeBlock.setHeaderCodeLet(codeLet);
            return addSubCodeBlock(codeBlock);
        }
        return this;
    }

    public CodeLet getHeaderCodeLet() {
        return headerCodeLet;
    }

    public CodeLet getFooterCodeLet() {
        return footerCodeLet;
    }

    public CodeBlock write(Writer writer) throws IOException {
        return write(writer, 0);
    }

    public CodeBlock write(Writer writer, int tab) throws IOException {
        int blockIndent = tab + tabIndent;
        if (headerCodeLet != null) {
            writePreBlank(writer, blockIndent);
            headerCodeLet.write(writer);
        }
        if (subCodeBlocks != null) {
            for (CodeBlock codeBlock : subCodeBlocks) {
                if (headerCodeLet == null && footerCodeLet == null) {
                    codeBlock.write(writer, blockIndent);
                } else {
                    codeBlock.write(writer, blockIndent + 1);
                }
            }
        }
        if (footerCodeLet != null) {
            writePreBlank(writer, blockIndent);
            footerCodeLet.write(writer);
        }
        return this;
    }

    private void writePreBlank(Writer writer, int tab) throws IOException {
        if (tab > 0) {
            writer.write(String.format("%" + tab * 4 + "s", ""));
        }
    }

    public String toString() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(outputStream);
        try {
            write(writer);
            writer.close();
        } catch (IOException e) {
            //DoNothing
        }
        return outputStream.toString();
    }

}
