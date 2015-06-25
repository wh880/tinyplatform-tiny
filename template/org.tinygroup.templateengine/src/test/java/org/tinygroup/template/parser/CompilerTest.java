/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.template.parser;


import org.tinygroup.template.compiler.MemorySource;
import org.tinygroup.template.compiler.MemorySourceCompiler;

/**
 * Created by luoguo on 2014/6/7.
 */
public class CompilerTest {
    public static void main(String[] args)  {
        MemorySourceCompiler compiler=new MemorySourceCompiler();
        MemorySource source=new MemorySource("org.tinygroup.template.parser.Context1","package org.tinygroup.template.parser;import org.tinygroup.context.impl.ContextImpl;public class Context1 extends ContextImpl{}\n");
        compiler.compile("abc",source);
    }
}
