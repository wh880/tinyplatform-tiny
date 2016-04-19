package org.tinygroup.weixin.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.net.util.Base64;
import org.tinygroup.weixin.common.Client;
import org.tinygroup.weixin.exception.WeiXinException;

/**
 * AES加解密包装类
 * 
 * @author yancheng11334
 * 
 */
public class AesKeyWrapper {
	static Charset CHARSET = Charset.forName("utf-8");

	private static final String XML_FORMAT = "<xml>\n"
			+ "<Encrypt><![CDATA[%1$s]]></Encrypt>\n"
			+ "<MsgSignature><![CDATA[%2$s]]></MsgSignature>\n"
			+ "<TimeStamp>%3$s</TimeStamp>\n"
			+ "<Nonce><![CDATA[%4$s]]></Nonce>\n" + "</xml>";

	private String timestamp;
	private String nonce;
	private Client client;
	private byte[] aesKey;

	AesKeyWrapper(String timestamp, String nonce, Client client) {
		this.timestamp = timestamp;
		this.nonce = nonce;
		this.client = client;

		aesKey = Base64.decodeBase64(client.getEncodeAseKey() + "=");
	}

	/**
	 * 加密微信消息
	 */
	public String encryptMessage(String xml) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			// randomStr + networkBytesOrder + text + appId
			byte[] randomStrBytes = getRandomStr().getBytes(CHARSET);
			byte[] textBytes = xml.getBytes(CHARSET);
			byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
			byte[] appIdBytes = client.getAppId().getBytes(CHARSET);

			bos.write(randomStrBytes);
			bos.write(networkBytesOrder);
			bos.write(textBytes);
			bos.write(appIdBytes);

			// ... + pad: 使用自定义的填充方式对明文进行补位填充
			byte[] padBytes = WeiXinSignatureUtil.encode(bos.size());
			bos.write(padBytes);

			// 获得未加密的字节流
			byte[] unencrypted = bos.toByteArray();

			// 设置加密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

			// 加密
			byte[] encrypted = cipher.doFinal(unencrypted);
			
			// 转成BASE64位字符串
			String base64Encrypted = Base64.encodeBase64String(encrypted, false);

			// 生成签名
			String signature = WeiXinSignatureUtil.createSha1Algorithm(
					client.getToken(), timestamp, nonce, base64Encrypted);

			return String.format(XML_FORMAT, base64Encrypted, signature,
					timestamp, nonce);
		} catch (InvalidKeyException e) {
			throw new WeiXinException("JDK或JRE存在安全策略限制,请在官方网站下载JCE无限制权限策略文件进行替换", e);
		} catch (Exception e) {
			throw new WeiXinException("加密微信消息发生异常", e);
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				// 忽略流关闭异常
			}
		}
	}

	/**
	 * 解密微信消息
	 * 
	 * @param text
	 * @return
	 */
	public String decryptMsgMessage(String text) {
		try {
			// 设置解密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey,
					0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);

			// 使用BASE64对密文进行解码
			byte[] encrypted = Base64.decodeBase64(text);

			//解密
			byte[] original = cipher.doFinal(encrypted);
			
			// 去除补位字符
			byte[] bytes = WeiXinSignatureUtil.decode(original);
			
			byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);

			int xmlLength = recoverNetworkBytesOrder(networkOrder);
			
			return new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
		} catch (InvalidKeyException e) {
			throw new WeiXinException("JDK或JRE存在安全策略限制,请在官方网站下载JCE无限制权限策略文件进行替换", e);
		} catch (Exception e) {
			throw new WeiXinException("解密微信消息发生异常", e);
		} 
	}

	// 生成4个字节的网络字节序
	byte[] getNetworkBytesOrder(int sourceNumber) {
		byte[] orderBytes = new byte[4];
		orderBytes[3] = (byte) (sourceNumber & 0xFF);
		orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
		orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
		orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
		return orderBytes;
	}

	// 还原4个字节的网络字节序
	int recoverNetworkBytesOrder(byte[] orderBytes) {
		int sourceNumber = 0;
		for (int i = 0; i < 4; i++) {
			sourceNumber <<= 8;
			sourceNumber |= orderBytes[i] & 0xff;
		}
		return sourceNumber;
	}

	// 随机生成16位字符串
	String getRandomStr() {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

}
