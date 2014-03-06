/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.jspengine.el.parser;

public interface ELParserTreeConstants
{
  public int JJTCOMPOSITEEXPRESSION = 0;
  public int JJTLITERALEXPRESSION = 1;
  public int JJTDEFERREDEXPRESSION = 2;
  public int JJTDYNAMICEXPRESSION = 3;
  public int JJTVOID = 4;
  public int JJTCHOICE = 5;
  public int JJTOR = 6;
  public int JJTAND = 7;
  public int JJTEQUAL = 8;
  public int JJTNOTEQUAL = 9;
  public int JJTLESSTHAN = 10;
  public int JJTGREATERTHAN = 11;
  public int JJTLESSTHANEQUAL = 12;
  public int JJTGREATERTHANEQUAL = 13;
  public int JJTPLUS = 14;
  public int JJTMINUS = 15;
  public int JJTMULT = 16;
  public int JJTDIV = 17;
  public int JJTMOD = 18;
  public int JJTNEGATIVE = 19;
  public int JJTNOT = 20;
  public int JJTEMPTY = 21;
  public int JJTVALUE = 22;
  public int JJTDOTSUFFIX = 23;
  public int JJTBRACKETSUFFIX = 24;
  public int JJTIDENTIFIER = 25;
  public int JJTFUNCTION = 26;
  public int JJTTRUE = 27;
  public int JJTFALSE = 28;
  public int JJTFLOATINGPOINT = 29;
  public int JJTINTEGER = 30;
  public int JJTSTRING = 31;
  public int JJTNULL = 32;


  public String[] jjtNodeName = {
    "CompositeExpression",
    "LiteralExpression",
    "DeferredExpression",
    "DynamicExpression",
    "void",
    "Choice",
    "Or",
    "And",
    "Equal",
    "NotEqual",
    "LessThan",
    "GreaterThan",
    "LessThanEqual",
    "GreaterThanEqual",
    "Plus",
    "Minus",
    "Mult",
    "Div",
    "Mod",
    "Negative",
    "Not",
    "Empty",
    "Value",
    "DotSuffix",
    "BracketSuffix",
    "Identifier",
    "Function",
    "True",
    "False",
    "FloatingPoint",
    "Integer",
    "String",
    "Null",
  };
}
