package hkust.cse.calendar.gui.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.GraphicsDevice.WindowTranslucency;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import hkust.cse.calendar.gui.controller.DesktopNotificationControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseNotificationContainerView;
import hkust.cse.calendar.gui.view.base.BaseNotificationItemView;

public class PrimNotificationContainerView extends BaseNotificationContainerView {

	public PrimNotificationContainerView() {
		setUndecorated(true);
		setAlwaysOnTop(true);
		setVisible(false);
		Container pane = this.getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		setType(JFrame.Type.UTILITY);

        setBackground(new Color(0,0,0,0));
		
		pack();
		adjustPosition();
		
	}
	
	private void adjustPosition() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle rect = ge.getMaximumWindowBounds();
        int x = (int) rect.getMaxX() - getWidth() - 10;
        int y = (int) rect.getMaxY() - getHeight() - 10;
        setLocation(x, y);
	}
	
	@Override
	public void fireEvent(DesktopNotificationControllerEvent e) {
		Container pane = this.getContentPane();
		pane.removeAll();
		for(BaseNotificationItemView v: e.getViewList()) {
			pane.add(v);
		}
		pack();
		adjustPosition();
		
		if(e.getViewList().size() == 0) {
			this.setVisible(false);
		}
		else {
			this.setVisible(true);
		}
	}
	
}