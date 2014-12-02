package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.utils.ImagePool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

public class ToolBarButtonUI extends BasicButtonUI {
	ImageIcon normal, badging;
	
	
	PropertyChangeListener listener = new PropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent e) {
			if(e.getPropertyName().equals(AbstractButton.TEXT_CHANGED_PROPERTY)) {
				String text = (String)e.getNewValue();
				if(text.length() > 0) {
					((AbstractButton)e.getSource()).setIcon(badging);
				}
				else {
					((AbstractButton)e.getSource()).setIcon(normal);
				}
			}
		}
		
	};
	
	public ToolBarButtonUI(String normalIcon) {
		normal = new ImageIcon(ImagePool.getInstance().getImage(normalIcon));
		badging = null;
	}
	
	public ToolBarButtonUI(String normalIcon, String badgingIcon) {
		ImagePool pool = ImagePool.getInstance();
		normal = new ImageIcon(pool.getImage(normalIcon));
		badging = new ImageIcon(pool.getImage(badgingIcon));
	}
	


	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		
		JButton btn = (JButton)c;
		
		String text = btn.getText();
		
		if(text.length() > 0) {
			
			double baseX = c.getWidth() * 0.5;
			double baseY = c.getHeight() * 0.2;
			double radius = c.getWidth() * 0.25;
		
			Graphics2D g2d = (Graphics2D)g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
			Shape circle = new Ellipse2D.Double(baseX, baseY, radius, radius);
		
			g2d.setColor(Color.RED.darker());
			g2d.fill(circle);
			
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12)); 
			g2d.drawString(text, (int)baseX+ 5, (int)baseY + 12);
		}
				
	}

	@Override
	protected void paintText(Graphics arg0, AbstractButton arg1,
			Rectangle arg2, String arg3) {
		
	}

	@Override
	protected void paintText(Graphics arg0, JComponent arg1, Rectangle arg2,
			String arg3) {
		
	}

	@Override
	public void installUI(JComponent arg0) {
		super.installUI(arg0);
		
		((AbstractButton)arg0).setIcon(normal);
	}

	@Override
	protected void installListeners(AbstractButton b) {
		super.installListeners(b);
		if(badging != null)
			b.addPropertyChangeListener(listener);
	}

	@Override
	protected void uninstallListeners(AbstractButton b) {
		super.uninstallListeners(b);

		if(badging != null)
			b.removePropertyChangeListener(listener);
	}
	
	
	
	
}