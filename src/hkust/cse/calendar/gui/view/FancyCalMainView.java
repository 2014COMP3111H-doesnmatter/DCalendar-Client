package hkust.cse.calendar.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import org.jdesktop.swingx.HorizontalLayout;

import hkust.cse.calendar.gui.controller.CalMainControllerEvent;
import hkust.cse.calendar.gui.controller.LoginControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseApptListView;
import hkust.cse.calendar.gui.view.base.BaseCalMainView;
import hkust.cse.calendar.gui.view.base.BaseCalMonthView;
import hkust.cse.calendar.gui.view.base.BaseCalMainView.CalMainViewEvent;
import hkust.cse.calendar.gui.view.base.BaseLoginView.LoginViewEvent;
import hkust.cse.calendar.gui.view.base.BaseMonthSelectorView;
import hkust.cse.calendar.utils.ImagePool;

/**
 * Display the main window with menu bar.
 * 
 * The main layout by this view is<pre>
 * *******************
 * *******************
 * * select * select *
 * * month  * day    *
 * *******************
 * *                 *
 * *   Appointment   *
 * *   List          *
 * *******************
 * </pre>
 * @author john
 *
 */
public class FancyCalMainView extends BaseCalMainView implements ActionListener {
	private JSplitPane upperPane;
	private JSplitPane wholePane;
	
	private JMenuItem logoutMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem mannualScheduleMenuItem;
	private JMenuItem timeMachineMenuItem;
	private JMenuItem venueMenuItem;
	
	private JButton userButton, functionButton, notificationButton;
	
	private JPanel functionPanel, adminFunctionPanel, userPanel, notificationPanel;
	
	private JButton timeMachineBtn;
	private JButton logoutBtn;
	private JButton exitBtn;
	private JLabel usernameLabel;
	
	public FancyCalMainView() {
		super();
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		contentPane.add(createToolBar());
		
		upperPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		wholePane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		wholePane.setTopComponent(upperPane);
		wholePane.setAlignmentX(LEFT_ALIGNMENT);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				CalMainViewEvent ev = new CalMainViewEvent(this, CalMainViewEvent.Command.EXIT);
				triggerCalMainViewEvent(ev);
			}
		});
		
		contentPane.add(wholePane);
		
		pack();
		setVisible(false);
		initMenuPanels();
	}
	
	JPanel createToolBar() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.setBorder(new EmptyBorder(new Insets(10, 20, 10, 20)));
		
		panel.setPreferredSize(new Dimension(500, 60));
		panel.setBackground(new Color(220,220,220));
		
		panel.setOpaque(true);
		
		JLabel logoLabel = new JLabel();

		logoLabel.setIcon(new ImageIcon(ImagePool.getInstance().getImage("logo.png")));
		logoLabel.setOpaque(false);
		
		
		userButton = new JButton();
		userButton.setUI(new BasicButtonUI());
		userButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		userButton.setOpaque(false);
		userButton.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		userButton.addActionListener(this);
		
		functionButton = new JButton();
		functionButton.setUI(new ToolBarButtonUI("function.png"));
		functionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		functionButton.setOpaque(false);
		functionButton.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		
		notificationButton = new JButton();
		notificationButton.setUI(new ToolBarButtonUI("notification.png", "notificationBadging.png"));
		notificationButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		notificationButton.setOpaque(false);
		notificationButton.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		notificationButton.addActionListener(this);
		
		panel.add(logoLabel);
		
		panel.add(Box.createHorizontalGlue());
		
		panel.add(userButton);
		panel.add(functionButton);
		panel.add(notificationButton);
		panel.setAlignmentX(LEFT_ALIGNMENT);
		
		return panel;
	}
	
	private void initMenuPanels() {
		userPanel = new JPanel();
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.PAGE_AXIS));
		userPanel.setBackground(Color.WHITE);
		
		//userPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JPanel upperP = new JPanel();
		upperP.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		upperP.setOpaque(false);
		usernameLabel = new JLabel("username");
		usernameLabel.setHorizontalAlignment(JLabel.LEFT);
		usernameLabel.setAlignmentX(LEFT_ALIGNMENT);
		upperP.setPreferredSize(new Dimension(120, 80));
		upperP.add(usernameLabel);
		upperP.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel lowerP = new JPanel();
		lowerP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		lowerP.setBackground(new Color(245, 245, 245));
		lowerP.setLayout(new BoxLayout(lowerP, BoxLayout.X_AXIS));
		logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(this);
		lowerP.add(logoutBtn);
		Dimension minSize = new Dimension(80, 1);
		Dimension prefSize = new Dimension(80, 1);
		Dimension maxSize = new Dimension(Short.MAX_VALUE, 1);
		lowerP.add(new Box.Filler(minSize, prefSize, maxSize));
		exitBtn = new JButton("Exit");
		exitBtn.addActionListener(this);
		lowerP.add(exitBtn);
		lowerP.setAlignmentX(LEFT_ALIGNMENT);
		
		userPanel.add(upperP);
		userPanel.add(lowerP);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == userButton) {
			new PopupPanel((Component)obj, userPanel);
		}
		else if(obj == logoutBtn) {
			CalMainViewEvent ev = new CalMainViewEvent(this, CalMainViewEvent.Command.LOGOUT);
			triggerCalMainViewEvent(ev);
		}
		else if(obj == exitBtn) {
			CalMainViewEvent ev = new CalMainViewEvent(this, CalMainViewEvent.Command.EXIT);
			triggerCalMainViewEvent(ev);
		}
	}

	@Override
	public void fireEvent(CalMainControllerEvent e) {
		CalMainControllerEvent.Command command = e.getCommand();
		if(command == CalMainControllerEvent.Command.UPDATE_INFO) {
			String username = e.getUsername();
			long selectedStamp = e.getSelectedDay();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			setTitle("DCalendar - " + username + " - " + format.format(new Date(selectedStamp)));
			
			userButton.setText(username);
			usernameLabel.setText(username);
		}
		else if(command == CalMainControllerEvent.Command.START) {
			setVisible(true);
		}
		
	}

	@Override
	public void setCalMonthView(BaseCalMonthView monthView) {
		upperPane.setRightComponent(monthView);
		pack();
		repaint();
	}

	@Override
	public void setApptListView(BaseApptListView apptListView) {
		wholePane.setBottomComponent(apptListView);
		pack();
		repaint();
	}

	@Override
	public void setMonthSelectView(BaseMonthSelectorView monthSelectorView) {
		upperPane.setLeftComponent(monthSelectorView);
		pack();
		repaint();
	}
	
}