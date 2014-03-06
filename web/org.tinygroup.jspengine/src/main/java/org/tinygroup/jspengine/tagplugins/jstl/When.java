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


package org.tinygroup.jspengine.tagplugins.jstl;

import org.tinygroup.jspengine.compiler.tagplugin.*;

public final class When implements TagPlugin {

    public void doTag(TagPluginContext ctxt) {
	// Get the parent context to determine if this is the first <c:when>
	TagPluginContext parentContext = ctxt.getParentContext();
	if (parentContext == null) {
	    ctxt.dontUseTagPlugin();
	    return;
	}

	if ("true".equals(parentContext.getPluginAttribute("hasBeenHere"))) {
	    ctxt.generateJavaSource("} else if(");
	    // See comment below for the reason we generate the extra "}" here.
	}
	else {
	    ctxt.generateJavaSource("if(");
	    parentContext.setPluginAttribute("hasBeenHere", "true");
	}
	ctxt.generateAttribute("test");
	ctxt.generateJavaSource("){");
	ctxt.generateBody();

	// We don't generate the closing "}" for the "if" here because there
	// may be whitespaces in between <c:when>'s.  Instead we delay
	// generating it until the next <c:when> or <c:otherwise> or
	// <c:choose>
    }
}
