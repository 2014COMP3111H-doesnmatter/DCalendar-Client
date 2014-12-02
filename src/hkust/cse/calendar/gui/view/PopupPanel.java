package hkust.cse.calendar.gui.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class PopupPanel extends JPanel {
	final static private double MARGIN = 15;
	final static private double ARROW_SIZE = 10;
	
	private Color bgCol;
	private boolean isToBottom;
	private double realW, realH;
	private double arrX;
	private Component source;
	private JPanel contentP;
	Polygon tr = new Polygon();
	
	PopupPanel(Component source, JPanel contentP) {
		this.setLayout(new GridLayout(1, 1));
		this.setOpaque(false);

		final JRootPane rootPane = SwingUtilities.getRootPane(source);
		final JLayeredPane layeredPane = rootPane.getLayeredPane();
		
		this.source = source;
		this.contentP = contentP;
		
		adjustPosition();

		JScrollPane contentScroll = new JScrollPane(contentP);
		contentScroll.setBorder(null);
		contentScroll.setPreferredSize(new Dimension((int)realW, (int)realH));
		this.add(contentScroll);
		
		//Ensure the content panel is opaque
		contentP.setOpaque(true);
		bgCol = contentP.getBackground();
		
		layeredPane.add(this, JLayeredPane.POPUP_LAYER);
		
		final ComponentAdapter listener = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				adjustPosition();
			}
		};
		
		final KeyboardFocusManager focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		
		PropertyChangeListener focusListener = new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent e) {
	            String prop = e.getPropertyName();
	            Component c = (Component)e.getNewValue();
	            if(c == null) {
	            	return;
	            }
	            if (("focusOwner".equals(prop)) &&
	                  (!SwingUtilities.isDescendingFrom(c, PopupPanel.this))) {
	            	
	            	focusManager.removePropertyChangeListener(this);
					rootPane.removeComponentListener(listener);
					layeredPane.remove(PopupPanel.this);
					layeredPane.repaint();
	            }
				
			}
			
		};
		focusManager.addPropertyChangeListener("focusOwner", focusListener);
		rootPane.addComponentListener(listener);
		this.requestFocusInWindow();
	}
	
	private void adjustPosition() {
		JRootPane rootPane = SwingUtilities.getRootPane(source);

		contentP.validate();
		double w = contentP.getPreferredSize().getWidth(),
				h = contentP.getPreferredSize().getHeight();
		
		double pW = rootPane.getBounds().getWidth(),
				pH = rootPane.getBounds().getHeight();
		
		double cW = source.getBounds().getWidth(),
				cH = source.getBounds().getHeight();
		
		Point p = SwingUtilities.convertPoint(source, 0, 0, rootPane);
		
		double startX, startY, endX, endY;
		isToBottom = true;
		
		//First decide whether to draw to top or to bottom
		//We prefer bottom to top
		if(p.getY() + cH + ARROW_SIZE + h + MARGIN < pH) {
			startY = p.getY() + cH;
			endY = p.getY() + cH + ARROW_SIZE + h + MARGIN;
		}
		else if(p.getY() - ARROW_SIZE - h - MARGIN >= 0) {
			startY = p.getY() - ARROW_SIZE - h;
			endY = p.getY();
			isToBottom = false;
		}
		else{
			//Draw to bottom anyway, clip it later
			startY = p.getY() + cH;
			endY = h - MARGIN;
		}
		
		//Next we find the vertical position
		//We try to make the arrow centered
		endX = p.getX() + (cW / 2) + (w / 2);
		startX = p.getX() + (cW / 2) - (w / 2);
		
		if(endX >= pW - MARGIN) {
			//Move startY back
			startX -= endX + MARGIN - pW;
			endX = pW - MARGIN;
		}
		if(startX < MARGIN) {
			//Move endY forth
			endX += MARGIN - startY;
			startX = MARGIN;
		}
		if(endX >= pW - MARGIN) {
			//It is out of range, we have to clip it
			endX = pW - MARGIN;
		}
		
		//Now we have real size
		realW = endX - startX;
		realH = endY - startY - ARROW_SIZE;
		arrX = p.getX() + (cW / 2) - startX;
		
		Border bder;
		tr.reset();
		
		if(isToBottom) {
			bder = BorderFactory.createEmptyBorder((int)ARROW_SIZE, 0, 0, 0);
			tr.addPoint((int)(arrX - ARROW_SIZE), (int)ARROW_SIZE);
			tr.addPoint((int)arrX, 0);
			tr.addPoint((int)(arrX + ARROW_SIZE), (int)ARROW_SIZE);
		}
		else {
			bder = BorderFactory.createEmptyBorder(0, 0, (int)ARROW_SIZE, 0);
			tr.addPoint((int)(arrX - ARROW_SIZE), (int)realH);
			tr.addPoint((int)arrX, (int)(realH + ARROW_SIZE));
			tr.addPoint((int)(arrX + ARROW_SIZE), (int)realH);
		}
		
		this.setBorder(BorderFactory.createCompoundBorder(bder, BorderFactory.createLineBorder(Color.DARK_GRAY)));
		this.setBounds((int)startX, (int)startY, (int)(endX - startX + 6), (int)(endY - startY + ARROW_SIZE));
		
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(bgCol);
		g2d.fillPolygon(tr);
		g2d.drawLine(tr.xpoints[0], tr.ypoints[0], tr.xpoints[2], tr.ypoints[2]);
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawPolyline(tr.xpoints, tr.ypoints, tr.npoints);
	}
	
}