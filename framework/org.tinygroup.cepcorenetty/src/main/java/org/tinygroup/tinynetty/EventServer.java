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
package org.tinygroup.tinynetty;

import org.jboss.netty.channel.ChannelHandler;
import org.tinygroup.netty.Server;
import org.tinygroup.netty.ServerHandler;
import org.tinygroup.netty.coder.hessian.HessianDecoder;
import org.tinygroup.netty.coder.hessian.HessianEncoder;
import org.tinygroup.netty.coder.kryo.KryoDecoder;

public class EventServer extends Server {

	public EventServer(int port) {
		super(port);
	}

	public ChannelHandler getHandler() {
		ServerHandler serverHandler = new EventServerHandler();
		serverHandler.setServer(this);
		return serverHandler;
	}

	public ChannelHandler getEncoder() {
		// return new ObjectEncoder();
		 return new HessianEncoder();
//		return new KryoEncoder();
	}

	public ChannelHandler getDecoder() {
		// return new ObjectDecoder(ClassResolvers.cacheDisabled(null));
		// return new KryoDecoder();
		return new HessianDecoder();
	}

}