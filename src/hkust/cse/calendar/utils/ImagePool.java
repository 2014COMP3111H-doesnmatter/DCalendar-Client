package hkust.cse.calendar.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImagePool {
	static private ImagePool instance;
	private Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
	
	private ImagePool() {
		
	}
	
	static public ImagePool getInstance() {
		if(instance == null) {
			instance = new ImagePool();
		}
		return instance;
	}
	
	public BufferedImage getImage(String filename) {
		if(map.containsKey(filename)) {
			return map.get(filename);
		}
		BufferedImage img = readImage(filename);
		map.put(filename, img);
		return img;
	}
	
	private BufferedImage readImage(String fileName) {
		BufferedImage logo = null;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream(fileName);
			logo = ImageIO.read(input);
		} catch (Throwable e1) {
		}
		
		return logo;
	}
}