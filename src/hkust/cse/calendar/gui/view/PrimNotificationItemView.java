package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.view.base.BaseNotificationItemView;
import hkust.cse.calendar.gui.view.base.BaseNotificationItemView.NotificationItemViewEvent;
import hkust.cse.calendar.utils.ImagePool;
import hkust.cse.calendar.utils.MousePropagator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class PrimNotificationItemView extends BaseNotificationItemView implements ActionListener {
	private JButton closeBtn;
	
	public PrimNotificationItemView(String title, String content) {
		super(title, content);
		initView(title, content, null);
	}
	
	public PrimNotificationItemView(String title, String content, String iconFile) {
		super(title, content, iconFile);
		initView(title, content, iconFile);
	}
	
	private void initView(String title, String content, String iconFile) {
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setSize(330, 80);
		
		final JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(70, 80));
		
		JLabel iconL;
		BufferedImage icon = ImagePool.getInstance().getImage(iconFile);
		if(icon != null) {
			iconL = new JLabel(new ImageIcon(icon.getScaledInstance(70, 70, BufferedImage.SCALE_SMOOTH)));
		}
		else {
			iconL = new JLabel();
		}
		iconL.setOpaque(false);
		iconL.setBackground(new Color(0, 0, 0, 0));
		iconL.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));
		left.add(iconL);
		
		JPanel right = new JPanel();
		right.setBackground(Color.WHITE);
		right.setPreferredSize(new Dimension(260, 80));
		right.setOpaque(true);
		right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
		JLabel titleL = new JLabel(title);
		titleL.setFont(titleL.getFont().deriveFont(Font.PLAIN, 18.0f));
		titleL.setAlignmentX(LEFT_ALIGNMENT);
		right.add(titleL);
		right.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		
	    JTextArea multi = new JTextArea(content);
	    multi.setWrapStyleWord(true);
	    multi.setLineWrap(true);
	    multi.setEditable(false);
	    multi.setAlignmentX(LEFT_ALIGNMENT);
	    multi.setMargin(new Insets(0, 0, 0, 10));
	    //Propagate all the mouse event;
	    multi.addMouseListener(new MousePropagator(bottom));
		right.add(multi);
		
		closeBtn = new JButton(new ImageIcon(ImagePool.getInstance().getImage("close.png").getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
		closeBtn.setUI(new BasicButtonUI());
		closeBtn.setOpaque(false);
		closeBtn.setBorder(new EmptyBorder(0,0,0,0));
		ImageIcon hover = new ImageIcon(ImagePool.getInstance().getImage("close_hover.png").getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH));
		closeBtn.setRolloverIcon(hover);
		closeBtn.setPressedIcon(hover);
		closeBtn.setSize(15, 15);
		layeredPane.add(closeBtn, Integer.valueOf(1));
		closeBtn.setLocation(310, 5);
		closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		closeBtn.addActionListener(this);
		
		bottom.add(left, BorderLayout.WEST);
		bottom.add(right, BorderLayout.EAST);
		bottom.setLocation(0, 0);
		bottom.setSize(350, 90);
		bottom.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				
				NotificationItemViewEvent ev = new NotificationItemViewEvent(PrimNotificationItemView.this, NotificationItemViewEvent.Command.ACTIVATE);
				PrimNotificationItemView.this.triggerNotificationItemViewEvent(ev);
			}
			
		});
		layeredPane.add(bottom, Integer.valueOf(0));
		this.setLayout(null);
		layeredPane.setLocation(2, 16);
		this.add(layeredPane);
		CompoundBorder bder = new CompoundBorder(new EmptyBorder(new Insets(15, 0, 0, 0)), new LineBorder(Color.LIGHT_GRAY));
		this.setBorder(bder);
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(333, 97));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == closeBtn) {
			NotificationItemViewEvent ev = new NotificationItemViewEvent(PrimNotificationItemView.this, NotificationItemViewEvent.Command.CLOSE);
			PrimNotificationItemView.this.triggerNotificationItemViewEvent(ev);
		}
		
	}

	
}
