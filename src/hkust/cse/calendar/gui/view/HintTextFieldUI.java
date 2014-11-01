package hkust.cse.calendar.gui.view;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

public class HintTextFieldUI extends BasicTextFieldUI implements FocusListener {

	private String hintText;
	private boolean hideOnFocus;
	private Color color;
	
	public HintTextFieldUI(String hintText, boolean hideOnFocus, Color color){
		this.hintText = hintText;
		this.hideOnFocus = hideOnFocus;
		this.color = color;
	}
	
	public HintTextFieldUI(String hintText, boolean hideOnFocus){
		this(hintText, hideOnFocus, null);
	}
	
	public HintTextFieldUI(String hintText){
		this(hintText, false);
	}
	
	public String getHint(){
		return hintText;
	}
	
	public void setHint(String hintText){
		this.hintText = hintText;
		repaint();
	}
	
	public boolean isHideOnFocus(){
		return hideOnFocus;
	}
	
	public void setHideOnFocus(boolean hideOnFocus){
		this.hideOnFocus = hideOnFocus;
		repaint();
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color color){
		this.color = color;
		repaint();
	}
	
	public void repaint(){
		if (getComponent()!= null){
			getComponent().repaint();
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (hideOnFocus)
			repaint();
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (hideOnFocus)
			repaint();
	}
	
	@Override
	protected void paintSafely(Graphics g) {
		super.paintSafely(g);
        JTextComponent comp = getComponent();
        if(hintText!=null && comp.getText().length() == 0 && (!(hideOnFocus && comp.hasFocus()))){
            if(color != null) {
                g.setColor(color);
            } else {
                g.setColor(comp.getForeground().brighter().brighter().brighter());              
            }
            int padding = (comp.getHeight() - comp.getFont().getSize())/2;
            g.drawString(hintText, 2, comp.getHeight()-padding-1);          
        }
	}
	
	@Override
    protected void installListeners() {
        super.installListeners();
        getComponent().addFocusListener(this);
    }
	
    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        getComponent().removeFocusListener(this);
    }
}
