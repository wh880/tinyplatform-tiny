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

import org.tinygroup.jspengine.el.lang.EvaluationContext;

import javax.el.ELException;
import javax.el.MethodInfo;


/* All AST nodes must implement this interface.  It provides basic
   machinery for constructing the parent and child relationships
   between nodes. */

/**
 * @author Jacob Hookom [jacob@hookom.net]
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: tcfujii $
 */
public interface Node {

  /** This method is called after the node has been made the current
    node.  It indicates that child nodes can now be added to it. */
  void jjtOpen();

  /** This method is called after all the child nodes have been
    added. */
  void jjtClose();

  /** This pair of methods are used to inform the node of its
    parent. */
  void jjtSetParent(Node n);
  Node jjtGetParent();

  /** This method tells the node to add its argument to the node's
    list of children.  */
  void jjtAddChild(Node n, int i);

  /** This method returns a child node.  The children are numbered
     from zero, left to right. */
  Node jjtGetChild(int i);

  /** Return the number of children the node has. */
  int jjtGetNumChildren();
  
  String getImage();
  
  Object getValue(EvaluationContext ctx) throws ELException;
  void setValue(EvaluationContext ctx, Object value) throws ELException;
  Class getType(EvaluationContext ctx) throws ELException;
  boolean isReadOnly(EvaluationContext ctx) throws ELException;
  void accept(NodeVisitor visitor) throws ELException;
  MethodInfo getMethodInfo(EvaluationContext ctx, Class[] paramTypes) throws ELException;
  Object invoke(EvaluationContext ctx, Class[] paramTypes, Object[] paramValues) throws ELException;

  boolean equals(Object n);
  int hashCode();
}
