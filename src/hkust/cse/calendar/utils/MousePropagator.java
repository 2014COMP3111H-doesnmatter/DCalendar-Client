package hkust.cse.calendar.utils;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MousePropagator extends MouseAdapter {
	private Component c;
	
	public MousePropagator(Component c) {
		this.c = c;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		c.dispatchEvent(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		c.dispatchEvent(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		c.dispatchEvent(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		c.dispatchEvent(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		c.dispatchEvent(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		c.dispatchEvent(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		c.dispatchEvent(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		c.dispatchEvent(e);
	}
	
}