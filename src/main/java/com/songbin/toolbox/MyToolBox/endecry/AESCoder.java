package com.songbin.toolbox.MyToolBox.endecry;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class AESCoder {

	private static final String algorithm = "AES";
	
	private static final String cipher_algorithm = "AES/ECB/PKCS5Padding";
	
	private static final String key = "xianfuisthebest!";//AES要求密钥长度为128位、192位或256位
	
	private static Key toKey() throws Exception{
		SecretKey secretKey = new SecretKeySpec(AESCoder.key.getBytes(),AESCoder.algorithm);
		return secretKey;
	}
	
	public static String encryptBase64(String data) throws Exception{
		Cipher cipher = Cipher.getInstance(AESCoder.cipher_algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, AESCoder.toKey());
		byte[] encryptData = cipher.doFinal(data.getBytes());
		return Base64.encodeBase64String(encryptData);
	}
	
	public static String decryptBase64(String data) throws Exception{
		Cipher cipher = Cipher.getInstance(AESCoder.cipher_algorithm);
		cipher.init(Cipher.DECRYPT_MODE, AESCoder.toKey());
		byte[] decryptData = cipher.doFinal(Base64.decodeBase64(data));
		return new String(decryptData);
	}
	
	
	public static void main(String args[]) throws Exception{
		String data = "123412341234";
		String encryptData = AESCoder.encryptBase64(data);
		System.out.println("加密前:"+data);
		System.out.println("加密后:"+encryptData);
		String decryptData = AESCoder.decryptBase64(encryptData);
		System.out.println("解密后:"+decryptData);
	}
}
