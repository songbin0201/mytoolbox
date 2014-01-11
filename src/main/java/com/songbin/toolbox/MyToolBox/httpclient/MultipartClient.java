package com.songbin.toolbox.MyToolBox.httpclient;

import java.io.File;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class MultipartClient {

	public void mockMultipart() throws ClientProtocolException, IOException{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost("");
		
		FileBody bin = new FileBody(new File("filename"));
		StringBody comment = new StringBody("A binary file of some kind");

        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("bin", bin);
        reqEntity.addPart("comment", comment);

        post.setEntity(reqEntity);

        System.out.println("executing request " + post.getRequestLine());
        HttpResponse response = httpClient.execute(post);
        HttpEntity resEntity = response.getEntity();

        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        if (resEntity != null) {
            System.out.println("Response content length: " + resEntity.getContentLength());
        }
        EntityUtils.consume(resEntity);
	}
	
	
}
