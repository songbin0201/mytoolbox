package com.songbin.toolbox.MyToolBox.mockgroupon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class RandomcodeMock {

	public static void changecode() throws ClientProtocolException, IOException{
		String url = "http://sp.lashou.com/code.php?"+System.currentTimeMillis();
		DefaultHttpClient httpClient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36");
		
		HttpResponse response = httpClient.execute(httpget);
		
		File storeFile = new File("/usr/local/2013lashou.jpg");    
        FileOutputStream output = new FileOutputStream(storeFile);    
        //得到网络资源的字节数组,并写入文件    
        output.write(EntityUtils.toByteArray(response.getEntity()));
        output.close();   
	}
	
	public static void main(String args[]) throws IOException{
		try {
			RandomcodeMock.changecode();
			System.out.println(System.currentTimeMillis());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
