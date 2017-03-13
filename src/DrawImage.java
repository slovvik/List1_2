import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class DrawImage extends Canvas {
	
	private Image resizeImage(Image image) {
	   int sourceWidth = image.getWidth();
	   int sourceHeight = image.getHeight();
	   int screenWidth = getWidth();
	   int screenHeight = getHeight();
	  
	   Image thumb = Image.createImage(screenWidth, screenHeight);
	   Graphics g = thumb.getGraphics();
	  
	   for (int y = 0; y < screenHeight; y++) {
	      for (int x = 0; x < screenWidth; x++) {
	        g.setClip(x, y, 1, 1);
	        int dx = x * sourceWidth / screenWidth;
	        int dy = y * sourceHeight / screenHeight;
	        g.drawImage(image, x - dx, y - dy, Graphics.TOP | Graphics.LEFT);
	      }
	   }	  
	   Image immutableThumb = Image.createImage(thumb);	  
	   return immutableThumb;
	}

	protected void paint(Graphics g) {
		 try {
			Image image = Image.createImage("/todolist.png");
			Image resizeImage = resizeImage(image);
			g.drawImage(resizeImage, 0, 0, Graphics.TOP | Graphics.LEFT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
