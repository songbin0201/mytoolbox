package com.songbin.toolbox.MyToolBox.endecry;
 
import java.util.Map;   

/** *//** 
*   
*   使用Junit4 来进行单元测试。
* @version 1.0 
* @since 1.0 
*/ 
public class RSACoderTest {   

	public static void testPub2Pri(Map<String, Object> keyMap) throws Exception{
		String publicKey = RSACoder.getPublicKey(keyMap);
		String privateKey = RSACoder.getPrivateKey(keyMap);
		System.err.println("公钥: \n\r" + publicKey);
		System.err.println("私钥： \n\r" + privateKey);
		System.err.println("公钥加密——私钥解密");
		String inputStr = "1";
		byte[] data = inputStr.getBytes();

		byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);

		byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData,
				privateKey);

		String outputStr = new String(decodedData);
		System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
	}
	
    public static void testPri2Pub(Map<String, Object> keyMap) throws Exception {
		String publicKey = RSACoder.getPublicKey(keyMap);
		String privateKey = RSACoder.getPrivateKey(keyMap);
		System.err.println("公钥: \n\r" + publicKey);
		System.err.println("私钥： \n\r" + privateKey);
        System.err.println("私钥加密——公钥解密");   
        String inputStr = "sign";   
        byte[] data = inputStr.getBytes();   

        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);   

        byte[] decodedData = RSACoder.decryptByPublicKey(encodedData, publicKey);   

        String outputStr = new String(decodedData);   
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);   

        System.err.println("私钥签名——公钥验证签名");   
        // 产生签名   这里的encodedData可以与下面的encodedData同时换成new int[]{2,45}
        String sign = RSACoder.sign(encodedData, privateKey); //数字签名只要公钥人拿到签名的sign对比
        //，自己公钥通过同样的byte[]运算得到签名是否一致。是到致代表这个公钥就是对的，就是为现在发私钥人服务的。
        System.err.println("签名:\r" + sign);   
        // 验证签名   
        boolean status = RSACoder.verify(encodedData, publicKey, sign);   
        System.err.println("状态:\r" + status);    

    }   
    
    public static void main(String args[]) throws Exception{
    	Map<String, Object> keyMap = RSACoder.initKey();
    	RSACoderTest.testPub2Pri(keyMap);
    } 

} 
