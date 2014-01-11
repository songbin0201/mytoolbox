package com.songbin.toolbox.MyToolBox.charset;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class Demo {

	public static void main(String args[]) throws UnsupportedEncodingException{
		String a = "人";
		System.out.println(Charset.defaultCharset());
		System.out.println(a.getBytes().length);
		
		System.out.println(new String(a.getBytes(),"unicode"));
	}
}
