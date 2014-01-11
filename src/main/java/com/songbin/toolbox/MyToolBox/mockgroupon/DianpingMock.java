package com.songbin.toolbox.MyToolBox.mockgroupon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class DianpingMock {


	public static ThreadLocal<Map<String,String>> tl = new ThreadLocal<Map<String,String>>();
	static{
		Map<String,String> map = new HashMap<String,String>();
		tl.set(map);
	}
	
	public static void login(String name,String pwd) throws ClientProtocolException, IOException{
		String url = "http://e.dianping.com/account/loginsubmit";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(
				ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("userName", name));
		formparams.add(new BasicNameValuePair("password", pwd));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(entity);
		httppost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36");
		
		HttpResponse response = httpClient.execute(httppost);
		String redirectLocation = response.getHeaders("Location")[0].getValue();
		List<Cookie> cookies = httpClient.getCookieStore().getCookies();
		StringBuffer cookiestr = new StringBuffer(200);
		for (Cookie cookie:cookies) {
			cookiestr.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
		}

		System.out.println(cookiestr.toString());
		tl.get().put("Cookie", cookiestr.toString());
		
		DefaultHttpClient  httpClient1 = new DefaultHttpClient();
		httpClient1.getParams().setParameter(
				ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		HttpGet httpget = new HttpGet(redirectLocation);
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36");
		httpget.setHeader("Cookie", cookiestr.toString());
		
		HttpResponse response1 = httpClient1.execute(httpget);
		
		cookies = httpClient1.getCookieStore().getCookies();
		for (Cookie cookie:cookies) {
			cookiestr.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
		}
		
		tl.get().put("Cookie", cookiestr.toString());
		System.out.println(cookiestr.toString());
	}
	
	public static void verifycode(String couponcode) throws ClientProtocolException, IOException{
		String url = "http://e.dianping.com/tuangou/ajax/verify?serialNum="+couponcode;
		DefaultHttpClient httpClient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36");
		
		Map<String,String> map = tl.get();
		httpget.setHeader("Cookie", map.get("Cookie"));
		
		HttpResponse response = httpClient.execute(httpget);
		System.out.println(EntityUtils.toString(response.getEntity()));
	}
	
	public static void main(String args[]){
		try {
			DianpingMock.login("3257398750", "SPDEUTMJRX");
			DianpingMock.verifycode("1231212354531235");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
