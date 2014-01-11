package com.songbin.toolbox.MyToolBox.zookeeper.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestGraphic1 {

	public static void main(String[] args) {
		JFrame app = new JFrame();

		BufferedImage bi = new BufferedImage(10, 10,

		BufferedImage.TYPE_INT_ARGB);

		// Graphics g = bi.getGraphics();

		// Color oldColor = g.getColor();

		//

		// g.setColor(Color.red);

		// g.fillRect(0, 0, width, height);

		// g.setColor(oldColor);

		app.setSize(800, 600);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

		app.setLocation(dimension.width / 4, dimension.height / 4);

		saveImage();

		app.add(new TestGraphic1().new ImagePanel());

		app.setVisible(true);

	}

	static String s1[] = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };

	static String s2[] = { "00:00", "01:00", "02:00", "03:00", "04:00",

	"05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00",
			"13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00",
			"20:00", "21:00", "22:00", "23:00" };

	public static void saveImage() {

		int width = 1000;

		int height = 1000;

		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		// g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		// g.drawString("123", 12, 12);

		Graphics2D gg = (Graphics2D) g;

		gg.setBackground(Color.white);

		gg.setColor(Color.red);

		// gg.drawLine(3, 3, 100, 3);

		// gg.drawString("123", 120, 10);

		final int xxx = 10, yyy = 10;// 开头空点

		final int height1 = 20, width1 = 50;// 表格行高，列宽

		final int yy = height1 + yyy;

		int x = width1 + xxx;

		for (int i = 0; i < 7; i++) {

			gg.drawString(s1[i], x, yy);

			x += width1;

		}

		int y = yyy + 2 * height1;// 从第二行开始画

		for (int j = 0; j < 24; j++) {

			gg.drawString(s2[j], xxx, y);

			y += height1;

		}

		int lastx = 0, lasty = 0;

		int curx, cury;

		Random random = new Random();

		for (int i = 0; i < 7; i++) {

			// gg.drawString(s1[i], x, yy);

			// x += width1;

			for (int j = 0; j < 24; j++) {

				if (!random.nextBoolean())

					continue;

				curx = xxx + (i + 1) * width1;

				cury = yyy + (j + 2) * height1;

				gg.drawString("∞", curx, cury);

				if (lastx != 0)

					gg.drawLine(lastx, lasty, curx, cury);

				lastx = curx;

				lasty = cury;

			}

		}

		// gg.drawImage(bi, 200, 200, Color.white, null);

		try {

			ImageIO.write(bi, "png", new File("2.png"));

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

	class ImagePanel extends JPanel {

		private BufferedImage image;

		public ImagePanel() {

			try {

				image = ImageIO.read(new File("C:/2.png"));

			} catch (IOException ex) {

				// handle exception...

			}

		}

		@Override
		public void paintComponent(Graphics g) {

			g.drawImage(image, 0, 0, null);

		}

	}

}
