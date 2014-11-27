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
package org.tinygroup.cepcorenetty.operator;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.tinynetty.remote.EventClientDaemonRunnable;
import org.tinygroup.tinynetty.remote.EventTrigger;
import org.tinygroup.tinynetty.remote.NettyEventProcessor;
import org.tinygroup.tinynetty.remote.NettyEventProcessorConatiner;

public class ArUnregTrigger implements EventTrigger {
	CEPCore core;
	NettyEventProcessor processor;

	public ArUnregTrigger(CEPCore core, NettyEventProcessor processor) {
		this.core = core;
		this.processor = processor;
	}

	EventClientDaemonRunnable runable;

	public void execute() {
		NettyEventProcessorConatiner.remove(processor, core);
	}

	public void setEventClientDaemonRunnable(EventClientDaemonRunnable runable) {
		this.runable = runable;
	}

}
