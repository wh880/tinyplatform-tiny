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
package org.tinygroup.nettyremote.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.nettyremote.Client;
import org.tinygroup.nettyremote.DisconnectCallBack;
import org.tinygroup.nettyremote.Exception.TinyRemoteConnectException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientImpl implements Client {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientImpl.class);
	private ScheduledExecutorService executor = Executors
			.newScheduledThreadPool(1);
	private EventLoopGroup group = new NioEventLoopGroup();
	private int remotePort;// 需要连接的远程端口
	private String remoteHost;// 需要连接的远程地址
	private boolean ready;// 是否已完成连接
	private boolean start = false;// 是否已开始启动
	private boolean reConnect = false;// 连接断开后,是否需要进行重连
	private int reConnectInterval = 10; // 单位:秒
	private DisconnectCallBack callBack;

	private ClientThread clientThread = new ClientThread();
	private ChannelFuture future;

	public ClientImpl(int remotePort, String remoteHost, boolean reConnect) {
		this.remotePort = remotePort;
		this.remoteHost = remoteHost;
		this.reConnect = reConnect;
	}

	public void start() {
		LOGGER.logMessage(LogLevel.INFO, "启动客户端线程连接服务端{0}:{1}", remoteHost,
				remotePort);
		start = false;
		clientThread.start();

	}

	public void write(Object o) {
		future.channel().writeAndFlush(o);
	}

	private void reConnect() {
		if(executor.isShutdown()){
			return;
		}
		// 所有资源释放完成之后，清空资源，再次发起重连操作
		executor.execute(new Runnable() {
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(reConnectInterval);
					LOGGER.logMessage(LogLevel.INFO, "开始重连服务端{0}:{1}",
							remoteHost, remotePort);
					connect(remotePort, remoteHost);// 发起重连操作
				} catch (InterruptedException e) {
					//do nothing
				}
			}
		});
	}

	private void connect(int port, String host) {
		// 配置客户端NIO线程组
		try {
			Bootstrap b = new Bootstrap();
			b.group(group);
			init(b);
			// 发起异步连接操作
			future = b.connect(host, port).sync();
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			LOGGER.errorMessage("连接服务端{0}:{1}发生异常", e, remoteHost, remotePort);
			if (!reConnect) {
				throw new TinyRemoteConnectException("连接服务端" + remoteHost + ":"
						+ remotePort + "发生异常", e);
			}
		} finally {
			if (reConnect&&start) {
				reConnect();
			}
		}
		if(callBack!=null){
			callBack.call();
		}
	}

	protected void init(Bootstrap b) {
		b.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(
								new ObjectDecoder(ClassResolvers
										.cacheDisabled(null)));
						ch.pipeline().addLast("MessageEncoder",
								new ObjectEncoder());
						ch.pipeline().addLast(new ClientHandler());
					}
				});

	}

	class ClientThread extends Thread {
		public void run() {
			if (!start) {
				start = true;
				LOGGER.logMessage(LogLevel.INFO, "开始连接服务端{0}:{1}", remoteHost,
						remotePort);
				connect(remotePort, remoteHost);
			}

		}
	}

	public void stop() {
		LOGGER.logMessage(LogLevel.INFO, "关闭客户端");
		if (reConnect == true) {
			executor.shutdown();
		}
		start = false;
		group.shutdownGracefully();
		setReady(false);
	}

	public void doReady() {
		setReady(true);
		LOGGER.logMessage(LogLevel.INFO, "服务端{0}:{1}已连接", remoteHost,
				remotePort);
	}

	public boolean isReady() {
		if (future == null || future.channel() == null) {
			return false;
		}
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	
	public void setCallBack(DisconnectCallBack callBack) {
		this.callBack = callBack;
	}
	
	
}
