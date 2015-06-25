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
package org.tinygroup.nettyremote.codec.serialization;

import com.caucho.hessian.io.HessianInput;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolver;

import java.io.StreamCorruptedException;

public class HessianDecoder extends LengthFieldBasedFrameDecoder {

	private final ClassResolver classResolver;

	/**
	 * Creates a new decoder whose maximum object size is {@code 1048576} bytes.
	 * If the size of the received object is greater than {@code 1048576} bytes,
	 * a {@link StreamCorruptedException} will be raised.
	 * 
	 * @param classResolver
	 *            the {@link ClassResolver} to use for this decoder
	 */
	public HessianDecoder(ClassResolver classResolver) {
		this(1048576, classResolver);
	}

	/**
	 * Creates a new decoder with the specified maximum object size.
	 * 
	 * @param maxObjectSize
	 *            the maximum byte length of the serialized object. if the
	 *            length of the received object is greater than this value,
	 *            {@link StreamCorruptedException} will be raised.
	 * @param classResolver
	 *            the {@link ClassResolver} which will load the class of the
	 *            serialized object
	 */
	public HessianDecoder(int maxObjectSize, ClassResolver classResolver) {
		super(maxObjectSize, 0, 4, 0, 4);
		this.classResolver = classResolver;
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in)
			throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		if (frame == null) {
			return null;
		}

		HessianInput hin = new HessianInput(new ByteBufInputStream( frame));
		return hin.readObject();
		// ObjectInputStream is = new CompactObjectInputStream(new
		// ByteBufInputStream(frame), classResolver);
		// Object result = is.readObject();
		// is.close();
		// return result;
	}

	@Override
	protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer,
			int index, int length) {
		return buffer.slice(index, length);
	}
}
