/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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
 */
package org.tinygroup.jspengine.el.parser;

public interface ELParserTreeConstants
{
  int JJTCOMPOSITEEXPRESSION = 0;
  int JJTLITERALEXPRESSION = 1;
  int JJTDEFERREDEXPRESSION = 2;
  int JJTDYNAMICEXPRESSION = 3;
  int JJTVOID = 4;
  int JJTCHOICE = 5;
  int JJTOR = 6;
  int JJTAND = 7;
  int JJTEQUAL = 8;
  int JJTNOTEQUAL = 9;
  int JJTLESSTHAN = 10;
  int JJTGREATERTHAN = 11;
  int JJTLESSTHANEQUAL = 12;
  int JJTGREATERTHANEQUAL = 13;
  int JJTPLUS = 14;
  int JJTMINUS = 15;
  int JJTMULT = 16;
  int JJTDIV = 17;
  int JJTMOD = 18;
  int JJTNEGATIVE = 19;
  int JJTNOT = 20;
  int JJTEMPTY = 21;
  int JJTVALUE = 22;
  int JJTDOTSUFFIX = 23;
  int JJTBRACKETSUFFIX = 24;
  int JJTIDENTIFIER = 25;
  int JJTFUNCTION = 26;
  int JJTTRUE = 27;
  int JJTFALSE = 28;
  int JJTFLOATINGPOINT = 29;
  int JJTINTEGER = 30;
  int JJTSTRING = 31;
  int JJTNULL = 32;


  String[] jjtNodeName = {
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
