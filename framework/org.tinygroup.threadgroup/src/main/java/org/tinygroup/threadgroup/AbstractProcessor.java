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
package org.tinygroup.threadgroup;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

/**
 * 抽象处理器
 *
 * @author luoguo
 */
public abstract class AbstractProcessor implements Processor {
    private static Logger logger = LoggerFactory.getLogger(AbstractProcessor.class);
    private String name;
    private MultiThreadProcessor multiThreadProcess;
    private ExceptionCallBack exceptionCallBack = null;

    protected abstract void action() throws Exception;

    public AbstractProcessor(String name) {
        this.name = name;
    }

    public void setExceptionCallBack(ExceptionCallBack exceptionCallBack) {
        this.exceptionCallBack = exceptionCallBack;
    }

    public String getName() {
        return name;
    }

    public void run() {
        try {
            logger.logMessage(LogLevel.DEBUG, "线程<{}-{}>运行开始...", multiThreadProcess.getName(), name);
            action();
            logger.logMessage(LogLevel.DEBUG, "线程<{}-{}>运行结束", multiThreadProcess.getName(), name);
        } catch (Throwable e) {
            logger.errorMessage(e.getMessage(), e);
            if (exceptionCallBack != null) {
                exceptionCallBack.callBack(this, e);
            }
        } finally {
            multiThreadProcess.threadDone();
        }
    }

    public void setMultiThreadProcess(MultiThreadProcessor multiThreadProcess) {
        this.multiThreadProcess = multiThreadProcess;
    }
}
