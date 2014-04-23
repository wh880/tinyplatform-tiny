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
package org.tinygroup.bundle;

/**
 * 针对级别的操作接口
 *
 * @author luoguo
 */
public interface BundleLevelManager {
    /**
     * 初始化指定级别以上的版本
     *
     * @param level
     */
    void init(int level);

    /**
     * 组装指定级别以上的版本
     *
     * @param level
     */
    void assemble(int level);

    /**
     * 开始指定级别以上的版本
     *
     * @param level
     */
    void start(int level);

    /**
     * 暂停指定级别以下的版本
     *
     * @param level
     */
    void pause(int level);

    /**
     * 停止指定级别以下的版本
     *
     * @param level
     */
    void stop(int level);

    /**
     * 卸载指定级别以下的版本
     *
     * @param level
     */
    void disassemble(int level);

    /**
     * 销毁指定级别以下的版本
     *
     * @param level
     */
    void destroy(int level);

}
