/**
 * jetbrick-template
 * http://subchen.github.io/jetbrick-template/
 *
 * Copyright 2010-2014 Guoqiang Chen. All rights reserved.
 * Email: subchen@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.template.parser;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang.StringEscapeUtils;
import org.tinygroup.template.parser.grammer.JetTemplateParser;
import org.tinygroup.template.parser.grammer.JetTemplateParserVisitor;

import java.util.List;

// Visitor 模式访问器，用来生成 Java 代码
public class JetTemplateCodeVisitor extends AbstractParseTreeVisitor<CodeBlock> implements JetTemplateParserVisitor<CodeBlock> {
    private CodeLet currentCodeLet = null;
    private CodeBlock currentCodeBlock;
    private CodeBlock tmpCodeBlock;
    private CodeLet tmpCodeLet;
    private CodeBlock initCodeBlock = null;
    private CodeBlock macroCodeBlock = null;

    public CodeBlock visitExpression_list(@NotNull JetTemplateParser.Expression_listContext ctx) {

        List<JetTemplateParser.ExpressionContext> expression_list = ctx.expression();
        int i = 0;
        for (JetTemplateParser.ExpressionContext expression : expression_list) {
            CodeLet exp = new CodeLet();
            pushCodeLet(exp);
            expression.accept(this);
            popCodeLet();
            if (i > 0) {
                currentCodeLet.code(",");
            }
            currentCodeLet.code(exp);
            i++;
        }
        return null;
    }

    public CodeBlock visitInvalid_directive(@NotNull JetTemplateParser.Invalid_directiveContext ctx) {
        return null;
    }

    public CodeBlock visitElse_directive(@NotNull JetTemplateParser.Else_directiveContext ctx) {
        CodeBlock elseBlock = new CodeBlock().subCode(new CodeLet().lineCode("}else{")).tabIndent(-1);
        currentCodeBlock.subCode(elseBlock);
        ctx.block().accept(this);
        return null;
    }

    public CodeBlock visitType(@NotNull JetTemplateParser.TypeContext ctx) {
        return null;
    }

    public CodeBlock visitExpr_hash_map(@NotNull JetTemplateParser.Expr_hash_mapContext ctx) {
        JetTemplateParser.Hash_map_entry_listContext hash_map_entry_list = ctx.hash_map_entry_list();
        if (hash_map_entry_list != null) {
            currentCodeLet.code("{").code(hash_map_entry_list.accept(this).toString()).code("}");
        }
        return null;
    }

    public CodeBlock visitContinue_directive(@NotNull JetTemplateParser.Continue_directiveContext ctx) {
        JetTemplateParser.ExpressionContext expression = ctx.expression();
        if (expression != null) {
            currentCodeLet = new CodeLet();
            expression.accept(this);
            CodeBlock ifCodeBlock = new CodeBlock().header(currentCodeLet.codeBefore("if(U.b(").lineCode(")){")).footer(new CodeLet().lineCode("}"));
            ifCodeBlock.subCode(new CodeLet().lineCode("return;"));
            currentCodeBlock.subCode(ifCodeBlock);
        } else {
            currentCodeBlock.subCode(new CodeLet().lineCode("return;"));
        }
        return null;
    }


    public CodeBlock visitExpr_field_access(@NotNull JetTemplateParser.Expr_field_accessContext ctx) {
        ctx.expression(0).accept(this);
        CodeLet oldCodeLet = currentCodeLet;
        currentCodeLet = new CodeLet();
        ctx.expression(1).accept(this);
        oldCodeLet.codeBefore("U.p(").code(",").code(currentCodeLet).code(")");
        currentCodeLet = oldCodeLet;
        return null;
    }

    public CodeBlock visitExpr_compare_condition(@NotNull JetTemplateParser.Expr_compare_conditionContext ctx) {
        CodeLet tmpCodeLet = currentCodeLet;
        CodeLet left = new CodeLet();
        currentCodeLet = left;
        ctx.expression(0).accept(this);
        CodeLet right = new CodeLet();
        currentCodeLet = right;
        ctx.expression(1).accept(this);
        String op = ctx.getChild(1).getText();
        tmpCodeLet.code(left).code(op).code(right);
        currentCodeLet = tmpCodeLet;
        return null;
    }


    public CodeBlock visitExpr_instanceof(@NotNull JetTemplateParser.Expr_instanceofContext ctx) {
        return null;
    }

    public CodeBlock visitExpr_function_call(@NotNull JetTemplateParser.Expr_function_callContext ctx) {
        return null;
    }

    public CodeBlock visitType_array_suffix(@NotNull JetTemplateParser.Type_array_suffixContext ctx) {
        return null;
    }

    public CodeBlock visitMacro_directive(@NotNull JetTemplateParser.Macro_directiveContext ctx) {
        String name = ctx.getChild(0).getText();
        name = name.substring(6, name.length() - 1).trim();
        initCodeBlock.subCode(new CodeLet().lineCode("getMacroMap().put(\"%s\", new %s());", name, name));
        //TODO
        CodeBlock macro = new CodeBlock();
        JetTemplateParser.Define_expression_listContext define_expression_list = ctx.define_expression_list();
        currentCodeLet = new CodeLet();
        if (define_expression_list != null) {
            define_expression_list.accept(this);
        }
        macro.header(new CodeLet().lineCode("class %s extends AbstractMacro {", name));
        macro.footer(new CodeLet().lineCode("}"));
        macro.subCode(constructMethod(name));
        CodeBlock render=new CodeBlock();
        macro.subCode(render);
        render.header("protected void renderTemplate(TemplateContext $context, Writer $writer) throws IOException,TemplateException {");
        render.footer("}");
        pushCodeBlock(render);
        ctx.block().accept(this);
        popCodeBlock();
        macroCodeBlock.subCode(macro);
        return null;
    }

    private CodeBlock constructMethod(String name) {
        CodeBlock block = new CodeBlock();
        block.header(CodeLet.lineCodeLet("public %s() {", name).endLine());
        block.subCode("String[] args = {" + currentCodeLet + "};");
        block.subCode("init(\"aaa\", args);");
        block.footer(CodeLet.lineCodeLet("}"));
        return block;
    }

    public CodeBlock visitExpr_compare_equality(@NotNull JetTemplateParser.Expr_compare_equalityContext ctx) {
        CodeLet tmpCodeLet = currentCodeLet;
        CodeLet left = new CodeLet();
        currentCodeLet = left;
        ctx.expression(0).accept(this);
        CodeLet right = new CodeLet();
        currentCodeLet = right;
        ctx.expression(1).accept(this);
        String op = ctx.getChild(1).getText();
        tmpCodeLet.code(left).code(op).code(right);
        currentCodeLet = tmpCodeLet;
        return null;
    }

    public CodeBlock visitValue(@NotNull JetTemplateParser.ValueContext ctx) {
        currentCodeLet = new CodeLet();
        ctx.expression().accept(this);
        Token token = ((TerminalNode) ctx.getChild(0)).getSymbol();
        if (token.getType() == JetTemplateParser.VALUE_ESCAPED_OPEN) {
            currentCodeLet.codeBefore("StringEscapeUtils.escapeHtml((").code(")+\"\")");
        }
        currentCodeLet.codeBefore("$writer.write(").lineCode(");");
        CodeBlock valueCodeBlock = new CodeBlock();
        valueCodeBlock.subCode(currentCodeLet);
        return valueCodeBlock;
    }

    public CodeBlock visitExpr_math_binary_bitwise(@NotNull JetTemplateParser.Expr_math_binary_bitwiseContext ctx) {
        currentCodeLet.code("O.e(\"").code(ctx.getChild(1).getText()).code("\",");
        ctx.expression(0).accept(this);
        currentCodeLet.code(",");
        ctx.expression(1).accept(this);
        currentCodeLet.code(")");
        return null;
    }


    public CodeBlock visitHash_map_entry_list(@NotNull JetTemplateParser.Hash_map_entry_listContext ctx) {
        List<JetTemplateParser.ExpressionContext> expression_list = ctx.expression();
        CodeLet keyPair = new CodeLet();
        CodeBlock result = new CodeBlock().subCode(keyPair);
        for (int i = 0; i < expression_list.size(); i += 2) {
            CodeBlock codeBlock = new CodeBlock();
            CodeLet keyCodeLet = new CodeLet();
            currentCodeLet = keyCodeLet;
            expression_list.get(i).accept(this);
            CodeLet valueCodeLet = new CodeLet();
            currentCodeLet = valueCodeLet;
            expression_list.get(i + 1).accept(this);
            codeBlock.subCode(new CodeLet().code(keyCodeLet).code(":").code(valueCodeLet));
            if (i > 0) {
                keyPair.code(",");
            }
            keyPair.code(keyCodeLet).code(":").code(valueCodeLet);
        }
        return result;
    }

    public CodeBlock visitDirective(@NotNull JetTemplateParser.DirectiveContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    public CodeBlock visitTemplate(@NotNull JetTemplateParser.TemplateContext ctx) {
        CodeBlock templateCodeBlock = getTemplateCodeBlock();
        CodeBlock classCodeBlock = getClassCodeBlock();
        templateCodeBlock.subCode(classCodeBlock);
        CodeBlock renderMethodCodeBlock = getRenderCodeBlock();
        currentCodeBlock = renderMethodCodeBlock;
        classCodeBlock.subCode(renderMethodCodeBlock);
        CodeBlock getTemplatePathMethod = getTemplatePathMethodCodeBlock();
        classCodeBlock.subCode(getTemplatePathMethod);
        renderMethodCodeBlock.subCode(ctx.block().accept(this));
        return templateCodeBlock;
    }

    private CodeBlock getRenderCodeBlock() {
        CodeBlock renderMethod = new CodeBlock();
        renderMethod.header(new CodeLet().lineCode("protected void renderTemplate(TemplateContext $context, Writer $writer) throws IOException, TemplateException{")).footer(new CodeLet().lineCode("}"));
        return renderMethod;
    }

    private CodeBlock getTemplatePathMethodCodeBlock() {
        CodeBlock renderMethod = new CodeBlock();
        renderMethod.header(new CodeLet().lineCode("public String getPath(){")).footer(new CodeLet().lineCode("}"));
        renderMethod.subCode(new CodeLet().lineCode("return \"abc\";"));
        return renderMethod;
    }

    private CodeBlock getClassCodeBlock() {
        CodeBlock templateClass = new CodeBlock();
        initCodeBlock = new CodeBlock().header(new CodeLet("{").endLine()).footer(new CodeLet("}").endLine());
        templateClass.header(new CodeLet().lineCode("public class A extends AbstractTemplate{"));
        templateClass.subCode(initCodeBlock);
        macroCodeBlock = new CodeBlock();
        templateClass.subCode(macroCodeBlock);
        templateClass.footer(new CodeLet().lineCode("}"));
        return templateClass;
    }

    private CodeBlock getTemplateCodeBlock() {
        CodeBlock codeBlock = new CodeBlock();
        codeBlock.subCode(new CodeLet().lineCode("package abc;"));
        codeBlock.subCode(new CodeLet().lineCode("import java.io.IOException;"));
        codeBlock.subCode(new CodeLet().lineCode("import java.io.Writer;"));
        codeBlock.subCode(new CodeLet().lineCode("import org.tinygroup.template.TemplateContext;"));
        codeBlock.subCode(new CodeLet().lineCode("import org.tinygroup.template.TemplateException;"));
        codeBlock.subCode(new CodeLet().lineCode("import org.tinygroup.template.impl.AbstractTemplate;"));
        codeBlock.subCode(new CodeLet().lineCode("import org.tinygroup.template.impl.AbstractMacro;"));
        return codeBlock;
    }

    public CodeBlock visitExpr_compare_not(@NotNull JetTemplateParser.Expr_compare_notContext ctx) {
        return null;
    }

    public CodeBlock visitText(@NotNull JetTemplateParser.TextContext ctx) {
        Token token = ((TerminalNode) ctx.getChild(0)).getSymbol();
        String text = token.getText();
        switch (token.getType()) {
            case JetTemplateParser.COMMENT_LINE:
            case JetTemplateParser.COMMENT_BLOCK1:
            case JetTemplateParser.COMMENT_BLOCK2:
                return null;
            case JetTemplateParser.TEXT_PLAIN:
                break;
            case JetTemplateParser.TEXT_CDATA:
                text = text.substring(3, text.length() - 3).trim();
                break;
            case JetTemplateParser.TEXT_ESCAPED_CHAR:
                text = text.substring(1);
                break;
        }
        CodeBlock textCodeBlock = new CodeBlock().header(new CodeLet().code("$writer.write(\"").code(StringEscapeUtils.escapeJava(text)).lineCode("\");"));
        return textCodeBlock;
    }

    public CodeBlock visitExpr_identifier(@NotNull JetTemplateParser.Expr_identifierContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        if (name.startsWith("$")) {
            name = name.substring(1);
        }
        currentCodeLet.code("U.c($context,\"" + name + "\")");
        return null;
    }

    public CodeBlock visitExpr_static_method_invocation(@NotNull JetTemplateParser.Expr_static_method_invocationContext ctx) {
        return null;
    }

    public CodeBlock visitIf_directive(@NotNull JetTemplateParser.If_directiveContext ctx) {
        CodeBlock ifCodeBlock = new CodeBlock();
        CodeBlock tmpCodeBlock = currentCodeBlock;
        currentCodeLet = new CodeLet();
        ctx.expression().accept(this);
        ifCodeBlock.header(currentCodeLet.codeBefore("if(U.b(").lineCode(")){"));
        ifCodeBlock.footer(new CodeLet().lineCode("}"));
        currentCodeBlock = ifCodeBlock;
        ctx.block().accept(this);
        List<JetTemplateParser.Elseif_directiveContext> elseif_directive_list = ctx.elseif_directive();
        for (JetTemplateParser.Elseif_directiveContext elseif_directive : elseif_directive_list) {
            elseif_directive.accept(this);
        }
        JetTemplateParser.Else_directiveContext else_directive = ctx.else_directive();
        if (else_directive != null) {
            else_directive.accept(this);
        }
        currentCodeBlock = tmpCodeBlock;
        return ifCodeBlock;
    }

    public CodeBlock visitExpr_math_unary_suffix(@NotNull JetTemplateParser.Expr_math_unary_suffixContext ctx) {
        return null;
    }

    public CodeBlock visitDefine_expression_list(@NotNull JetTemplateParser.Define_expression_listContext ctx) {
        if (currentCodeLet.length() > 0) {
            currentCodeLet.code(",");
        }
        currentCodeLet.code("\"%s\"", ctx.getChild(0).getText());
        return null;
    }

    public CodeBlock visitExpr_static_field_access(@NotNull JetTemplateParser.Expr_static_field_accessContext ctx) {
        return null;
    }

    public CodeBlock visitExpr_new_array(@NotNull JetTemplateParser.Expr_new_arrayContext ctx) {
        return null;
    }

    public CodeBlock visitType_name(@NotNull JetTemplateParser.Type_nameContext ctx) {
        return null;
    }

    public CodeBlock visitType_list(@NotNull JetTemplateParser.Type_listContext ctx) {
        return null;
    }

    public CodeBlock visitInclude_directive(@NotNull JetTemplateParser.Include_directiveContext ctx) {
        return null;
    }

    public CodeBlock visitExpr_array_get(@NotNull JetTemplateParser.Expr_array_getContext ctx) {
        ctx.expression(0).accept(this);
        currentCodeLet.codeBefore("U.a(").code(",");
        ctx.expression(1).accept(this);
        currentCodeLet.code(")");
        return null;
    }

    public CodeBlock visitBlock(@NotNull JetTemplateParser.BlockContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree node = ctx.children.get(i);
            CodeBlock codeBlock = node.accept(this);
            currentCodeBlock.subCode(codeBlock);
        }
        return null;
    }

    public CodeBlock visitExpr_compare_relational(@NotNull JetTemplateParser.Expr_compare_relationalContext ctx) {
        return null;
    }

    public CodeBlock visitType_arguments(@NotNull JetTemplateParser.Type_argumentsContext ctx) {
        return null;
    }


    public CodeBlock visitExpr_math_binary_basic(@NotNull JetTemplateParser.Expr_math_binary_basicContext ctx) {
        currentCodeLet.code("O.e(\"").code(ctx.getChild(1).getText()).code("\",");
        ctx.expression(0).accept(this);
        currentCodeLet.code(",");
        ctx.expression(1).accept(this);
        currentCodeLet.code(")");
        return null;
    }

    public CodeBlock visitSet_expression(@NotNull JetTemplateParser.Set_expressionContext ctx) {
        CodeBlock codeBlock = new CodeBlock();
        CodeLet codeLet = new CodeLet();
        codeBlock.header(codeLet);
        currentCodeLet = codeLet;
        ctx.getChild(2).accept(this);
        codeLet.codeBefore("$context.put(\"" + ctx.getChild(0).getText() + "\",").lineCode(");");
        return codeBlock;
    }

    public CodeBlock visitTerminal(@org.antlr.v4.runtime.misc.NotNull org.antlr.v4.runtime.tree.TerminalNode node) {
        currentCodeLet.code(node.getText());
        return null;
    }

    public CodeBlock visitTag_directive(@NotNull JetTemplateParser.Tag_directiveContext ctx) {
        return null;
    }

    public CodeBlock visitSet_directive(@NotNull JetTemplateParser.Set_directiveContext ctx) {
        List<JetTemplateParser.Set_expressionContext> set_expression_list = ctx.set_expression();
        for (JetTemplateParser.Set_expressionContext node : set_expression_list) {
            currentCodeBlock.subCode(node.accept(this));
        }
        return null;
    }

    public CodeBlock visitExpr_new_object(@NotNull JetTemplateParser.Expr_new_objectContext ctx) {
        return null;
    }

    public CodeBlock visitConstant(@NotNull JetTemplateParser.ConstantContext ctx) {
        return null;
    }

    public CodeBlock visitStatic_type_name(@NotNull JetTemplateParser.Static_type_nameContext ctx) {
        return null;
    }

    public CodeBlock visitPut_directive(@NotNull JetTemplateParser.Put_directiveContext ctx) {
        return null;
    }

    public CodeBlock visitExpr_array_list(@NotNull JetTemplateParser.Expr_array_listContext ctx) {
        ParseTree items = ctx.getChild(1);
        CodeLet tempCodeLet = currentCodeLet;
        for (int i = 0; i < items.getChildCount(); i++) {
            currentCodeLet = new CodeLet();
            items.getChild(i).accept(this);
            tempCodeLet.code(currentCodeLet.toString());
        }
        tempCodeLet.codeBefore("{").code("}");
        currentCodeLet = tempCodeLet;
        return null;
    }

    public CodeBlock visitExpr_conditional_ternary(@NotNull JetTemplateParser.Expr_conditional_ternaryContext ctx) {
        CodeLet condition = new CodeLet();
        pushCodeLet(condition);
        ctx.expression(0).accept(this);
        popCodeLet();
        CodeLet left = new CodeLet();
        pushCodeLet(left);
        ctx.expression(1).accept(this);
        popCodeLet();
        CodeLet right = new CodeLet();
        pushCodeLet(right);
        ctx.expression(2).accept(this);
        popCodeLet();
        currentCodeLet.code("U.b(").code(condition).code(")?").code(left).code(":").code(right);
        return null;
    }

    public CodeBlock visitExpr_method_invocation(@NotNull JetTemplateParser.Expr_method_invocationContext ctx) {
        return null;
    }

    public CodeBlock visitFor_expression(@NotNull JetTemplateParser.For_expressionContext ctx) {
        currentCodeLet = new CodeLet();
        String name = ctx.IDENTIFIER().getText();
        ctx.expression().accept(this);
        CodeLet code = new CodeLet().code("ForIterator $").code(name).code("For = new ForIterator(").code(currentCodeLet).lineCode(");");
        currentCodeBlock.subCode(code);
        currentCodeBlock.subCode(new CodeLet().code("$context.put(\"$").code(name).code("For\"，$").code(name).lineCode("For);"));
        currentCodeBlock.subCode(new CodeLet().code("while($").code(name).lineCode("For.hasNext()){"));
        CodeBlock assign = new CodeBlock().tabIndent(1);
        assign.footer(new CodeLet().code("$context.put(\"").code(name).code("\"，").code(name).lineCode(");")).tabIndent(1);
        currentCodeBlock.subCode(assign);
        return null;
    }

    public CodeBlock visitDefine_expression(@NotNull JetTemplateParser.Define_expressionContext ctx) {
        return null;
    }


    public CodeBlock visitStop_directive(@NotNull JetTemplateParser.Stop_directiveContext ctx) {
        JetTemplateParser.ExpressionContext expression = ctx.expression();
        if (expression != null) {
            currentCodeLet = new CodeLet();
            expression.accept(this);
            CodeBlock ifCodeBlock = new CodeBlock().header(currentCodeLet.codeBefore("if(U.b(").lineCode(")){")).footer(new CodeLet().lineCode("}"));
            ifCodeBlock.subCode(new CodeLet().lineCode("return;"));
            currentCodeBlock.subCode(ifCodeBlock);
        } else {
            currentCodeBlock.subCode(new CodeLet().lineCode("return;"));
        }
        return null;
    }

    public CodeBlock visitBreak_directive(@NotNull JetTemplateParser.Break_directiveContext ctx) {
        JetTemplateParser.ExpressionContext expression = ctx.expression();
        if (expression != null) {
            currentCodeLet = new CodeLet();
            expression.accept(this);
            CodeBlock ifCodeBlock = new CodeBlock().header(currentCodeLet.codeBefore("if(U.b(").lineCode(")){")).footer(new CodeLet().lineCode("}"));
            ifCodeBlock.subCode(new CodeLet().lineCode("break;"));
            currentCodeBlock.subCode(ifCodeBlock);
        } else {
            currentCodeBlock.subCode(new CodeLet().lineCode("break;"));
        }
        return null;
    }

    public CodeBlock visitFor_directive(@NotNull JetTemplateParser.For_directiveContext ctx) {
        String name = ctx.getChild(1).getChild(1).getText();
        ctx.for_expression().accept(this);
        CodeBlock forCodeBlock = new CodeBlock();
        currentCodeBlock.subCode(forCodeBlock);
        forCodeBlock.footer(new CodeLet().lineCode("}"));
        pushCodeBlock(forCodeBlock);
        ctx.block().accept(this);
        popCodeBlock();
        //添加清理处理
        currentCodeBlock.subCode(new CodeLet().code("$context.remove(\"$").code(name).lineCode("For\");"));
        currentCodeBlock.subCode(new CodeLet().code("$context.remove(\"").code(name).lineCode("\");"));

        return null;
    }

    void pushCodeBlock(CodeBlock codeBlock) {
        tmpCodeBlock = currentCodeBlock;
        currentCodeBlock = codeBlock;
    }

    CodeLet popCodeLet() {
        CodeLet pop = currentCodeLet;
        currentCodeLet = tmpCodeLet;
        return pop;
    }

    void pushCodeLet(CodeLet codeLet) {
        tmpCodeLet = currentCodeLet;
        currentCodeLet = codeLet;
    }

    CodeBlock popCodeBlock() {
        CodeBlock pop = currentCodeBlock;
        currentCodeBlock = tmpCodeBlock;
        return pop;
    }


    public CodeBlock visitElseif_directive(@NotNull JetTemplateParser.Elseif_directiveContext ctx) {
        currentCodeLet = new CodeLet();
        ctx.expression().accept(this);
        CodeBlock elseifBlock = new CodeBlock().header(currentCodeLet.codeBefore("}else if(U.b(").lineCode(")){")).tabIndent(-1);
        currentCodeBlock.subCode(elseifBlock);
        ctx.block().accept(this);
        return null;
    }

    public CodeBlock visitExpr_math_unary_prefix(@NotNull JetTemplateParser.Expr_math_unary_prefixContext ctx) {
        return null;
    }

    public CodeBlock visitExpr_group(@NotNull JetTemplateParser.Expr_groupContext ctx) {
        currentCodeLet.code("(");
        ctx.expression().accept(this);
        currentCodeLet.code(")");
        return null;
    }

    public CodeBlock visitExpr_constant(@NotNull JetTemplateParser.Expr_constantContext ctx) {
        currentCodeLet.code(ctx.getText());
        return null;
    }


}
