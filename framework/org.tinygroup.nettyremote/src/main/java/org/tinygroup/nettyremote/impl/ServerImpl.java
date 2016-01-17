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
package org.tinygroup.nettyremote.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.nettyremote.Server;

import java.io.IOException;

public class ServerImpl implements Server {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServerImpl.class);
//	private ServerThread serverThread = new ServerThread();
	private boolean start = false;
	private boolean startFailStop = false;
	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	private EventLoopGroup workerGroup = new NioEventLoopGroup();
	private int localPort;

	public ServerImpl(int localPort,boolean startFailStop) {
		this.localPort = localPort;
		this.startFailStop = startFailStop;

	}

	public void start() {
		LOGGER.logMessage(LogLevel.INFO, "启动服务端线程,端口:{1}", localPort);
		setStart(false);
		startRun();
	}

	protected void init(ServerBootstrap b) {
		b.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 100)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					public void initChannel(SocketChannel ch)
							throws IOException {
						ch.pipeline().addLast(
								new ObjectDecoder(ClassResolvers
										.cacheDisabled(null)));
						ch.pipeline().addLast("MessageEncoder",
								new ObjectEncoder());
						ch.pipeline().addLast(new ServerHandler());
					}
				});
	}

	private void bind() throws InterruptedException {
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup);
		init(b);
		// 绑定端口，同步等待成功
		b.bind(localPort).sync();
	}

	public void stop() {
		LOGGER.logMessage(LogLevel.INFO, "关闭服务端");
		setStart(false);
		try {
			bossGroup.shutdownGracefully();
		} catch (Exception e) {
			LOGGER.errorMessage("关闭服务端时发生异常", e);
		}
		try {
			workerGroup.shutdownGracefully();
		} catch (Exception e) {
			LOGGER.errorMessage("关闭服务端时发生异常", e);
		}
		LOGGER.logMessage(LogLevel.INFO, "关闭服务端完成");
	}

	public void startRun() {
		if (!start) {
			setStart(true);
			try {
				bind();
			} catch (Exception e) {
				LOGGER.errorMessage("服务端启动失败", e);
				stop();
				if(startFailStop){
					throw new RuntimeException("服务端启动失败", e);
				}
			}
		}

	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

}
