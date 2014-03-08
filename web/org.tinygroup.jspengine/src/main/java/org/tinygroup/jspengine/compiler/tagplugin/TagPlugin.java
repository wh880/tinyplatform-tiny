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


package org.tinygroup.jspengine.compiler.tagplugin;

/**
 * This interface is to be implemented by the plugin author, to supply
 * an alternate implementation of the tag handlers.  It can be used to
 * specify the Java codes to be generated when a tag is invoked.
 *
 * An implementation of this interface must be registered in a file
 * named "tagPlugins.xml" under WEB-INF.
 */

public interface TagPlugin {

    /**
     * Generate codes for a custom tag.
     * @param ctxt a TagPluginContext for accessing Jasper functions
     */
    void doTag(TagPluginContext ctxt);
}
