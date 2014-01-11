package com.songbin.toolbox.MyToolBox.zookeeper.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestGraphic2 {

	/**
	 * 根据提供的文字生成jpg图片
	 * 
	 * @param s
	 *            String 文字
	 * @param align
	 *            文字位置（left,right,center）
	 * @param y   
	 * 			  y坐标
	 * @param width 
	 * 			    图片宽度
	 * @param height
	 * 			    图片高度
	 * @param bgcolor
	 *            Color 背景色
	 * @param fontcolor
	 *            Color 字色
	 * @param font
	 *            Font 字体字形字号
	 * @param jpgname
	 *            String jpg图片名
	 * @return
	 */
	private static boolean createJpgByFontAndAlign(String s, String align, int y, int width, int height,
			Color bgcolor, Color fontcolor, Font font, String jpgname) {
		try { // 宽度 高度
			BufferedImage bimage = new BufferedImage(width,
					height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bimage.createGraphics();
			g.setColor(bgcolor); // 背景色
			g.fillRect(0, 0, width, height); // 画一个矩形
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON); // 去除锯齿(当设置的字体过大的时候,会出现锯齿)
			g.setColor(fontcolor); // 字的颜色
			g.setFont(font); // 字体字形字号
			
			int size = font.getSize();	//文字大小
			int x = 5;
			if(align.equals("left")){
				x = 5;
			} else if(align.equals("right")){
				x = width - size * s.length() - 5;
			} else if(align.equals("center")){
				x = (width - size * s.length())/2;
			}
			g.drawString(s, x, y); // 在指定坐标除添加文字
			g.dispose();
			FileOutputStream out = new FileOutputStream(jpgname); // 指定输出文件
			com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(out);
			com.sun.image.codec.jpeg.JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
			param.setQuality(50f, true);
			encoder.encode(bimage, param); // 存盘
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println("createJpgByFont Failed!");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static Font loadFont(String fontFileName, float fontSize)  //第一个参数是外部字体名，第二个是字体大小
	{
		try
		{
			File file = new File(fontFileName);
			FileInputStream aixing = new FileInputStream(file);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, aixing);
			Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
			aixing.close();
			return dynamicFontPt;
		}
		catch(Exception e)//异常处理
		{
			e.printStackTrace();
			return new java.awt.Font("宋体", Font.PLAIN, 14);
		}
	}
	public static java.awt.Font Font(){
		//String root=System.getProperty("user.dir");//项目根目录路径
		Font font = TestGraphic2.loadFont("load_font/wawa.ttf", 18f);//调用
		return font;//返回字体
	}
	
	public static void main(String[] args) throws FontFormatException, IOException {
//		createJpgByFontAndAlign("生成图片", "right", 40, 100, 75, Color.white, Color.black, 
//				new Font(null, Font.BOLD, 12), "right.jpg");
		for(Font f:java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()){
			System.out.println(f.getName());
		}
		createJpgByFontAndAlign("生成图片", "center", 47, 100, 75, Color.white, Color.black, 
				new Font("Arial-BoldItalicMT",Font.BOLD,20), "auto_image/center.jpg");
		
//		createJpgByFontAndAlign("生成图片", "left", 47, 100, 75, Color.white, Color.black, 
//		new Font("Wawa-SC-Regular-stub", Font.BOLD, 26), "auto_image/sysfont_center.jpg");
		
//		createJpgByFontAndAlign("生成图片", "left", 35, 100, 75, Color.white, Color.black, 
//				new Font(null, Font.BOLD, 12), "left.jpg");
	}
}
