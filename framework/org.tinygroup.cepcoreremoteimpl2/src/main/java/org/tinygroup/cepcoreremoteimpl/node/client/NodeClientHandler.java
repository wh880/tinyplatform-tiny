package org.tinygroup.cepcoreremoteimpl.node.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcoreremoteimpl.node.ResponseManager;
import org.tinygroup.event.Event;
import org.tinygroup.event.central.Node;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class NodeClientHandler extends SimpleChannelInboundHandler<Event> {
	private static Logger logger = LoggerFactory
			.getLogger(NodeClientHandler.class);
	
	private NodeClientImpl client;

	public NodeClientHandler(NodeClientImpl client, Node node, CEPCore core) {
		this.client = client;
	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 设置客户端为就绪状态
		client.doReady();
		// 20150506当前连接的是另一个Node
		// // 如果当前客户端连接的目标是SC，则发起注册
		// if (RemoteCepCoreUtil.checkSc(client.getRemotePort(),
		// client.getRemoteHost())) {
		// nodeEventHandler.regToSc(ctx);
		// }

	}

	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Event event = (Event) msg;
		String serviceId = event.getServiceRequest().getServiceId();
		logger.logMessage(LogLevel.INFO, "接收到请求,id:{},type:", serviceId,
				event.getType());
		boolean isResponse = (Event.EVENT_TYPE_RESPONSE == event.getType());
		// if (CEPCoreEventHandler.NODE_RE_REG_TO_SC_REQUEST.equals(serviceId)
		// && isResponse) {// 向SC重新注册时SC的返回
		// nodeEventHandler.dealNodeRegResponse(event);
		// ResponseManager.updateResponse(event.getEventId(), event);
		// } else if
		// (CEPCoreEventHandler.NODE_REG_TO_SC_REQUEST.equals(serviceId)
		// && isResponse) {// 向SC注册时SC的返回
		// nodeEventHandler.dealNodeRegResponse(event);
		// } else if (CEPCoreEventHandler.NODE_UNREG_TO_SC_REQUEST
		// .equals(serviceId) && isResponse) {// 向SC注销时SC的返回
		// nodeEventHandler.dealNodeUnregResponse(event);
		// } else if (CEPCoreEventHandler.SC_REG_NODE_TO_NODE_REQUEST
		// .equals(serviceId)) {// SC向AR发起的注册AR请求
		// nodeEventHandler.dealScRegNodeToNode(event, ctx);
		// } else if (CEPCoreEventHandler.SC_UNREG_NODE_TO_NODE_REQUEST
		// .equals(serviceId)) {// SC向AR发起的注销AR请求
		// nodeEventHandler.dealScUnregNodeToNode(event, ctx);
		// } else
		if (isResponse) {
			processResult(event, ctx); // 处理服务的返回结果
		} else {
			logger.errorMessage("客户端收到未知请求" + serviceId);

		}
	}

	protected void processResult(Object response, ChannelHandlerContext ctx)
			throws Exception {
		Event event = (Event) response;
		String eventId = event.getEventId();
		logger.logMessage(LogLevel.INFO, "接收到Event:{0}的请求响应,请求id:{1}", eventId,
				event.getServiceRequest().getServiceId());
		ResponseManager.updateResponse(eventId, event);
	}

	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// if (!NettyCepCoreUtil.checkSc(client.getRemotePort(),
		// client.getRemoteHost())) {
		// nodeEventHandler.unregToSc(ctx);
		// }
	}

	protected void channelRead0(ChannelHandlerContext ctx, Event msg)
			throws Exception {

	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.errorMessage("客户端收到异常", cause);
		ctx.fireExceptionCaught(cause);
	}
}
