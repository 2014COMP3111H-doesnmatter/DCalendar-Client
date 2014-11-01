package hkust.cse.calendar.gui.view;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

/**
 * Charles Zhang, your decorator pattern rocks!
 *
 */
public class HintTextFieldUI extends BasicTextFieldUI implements FocusListener {

	private String hintText;
	private boolean hideOnFocus;
	private Color color;
	private BasicTextFieldUI ui;
	public HintTextFieldUI(String hintText, boolean hideOnFocus, Color color, BasicTextFieldUI ui){
		this.hintText = hintText;
		this.hideOnFocus = hideOnFocus;
		this.color = color;
		this.ui = ui;
	}
	
	public HintTextFieldUI(String hintText, boolean hideOnFocus, Color color){
		this(hintText, hideOnFocus, color, new BasicTextFieldUI());
	}
	
	public HintTextFieldUI(String hintText, boolean hideOnFocus, BasicTextFieldUI ui){
		this(hintText, hideOnFocus, null, ui);
	}
	
	public HintTextFieldUI(String hintText, boolean hideOnFocus){
		this(hintText, hideOnFocus, null, new BasicTextFieldUI());
	}
	
	public HintTextFieldUI(String hintText, BasicTextFieldUI ui){
		this(hintText, false, ui);
	}
	
	public HintTextFieldUI(String hintText){
		this(hintText, false, new BasicTextFieldUI());
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
        JTextComponent comp = getComponent();
        ui.paint(g, comp);
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
	public void installUI(JComponent c) {
		ui.installUI(c);
		super.installUI(c);
	}
	
	@Override
	public void uninstallUI(JComponent c) {
		ui.uninstallUI(c);
		super.uninstallUI(c);
	}
	
	@Override
    protected void installListeners() {
        getComponent().addFocusListener(this);
    }
	
    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        getComponent().removeFocusListener(this);
    }
}
