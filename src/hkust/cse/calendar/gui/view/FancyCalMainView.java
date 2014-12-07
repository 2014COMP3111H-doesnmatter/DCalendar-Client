package hkust.cse.calendar.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import java.util.List;

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
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import org.jdesktop.swingx.HorizontalLayout;

import hkust.cse.calendar.Main.DCalendarApp;
import hkust.cse.calendar.collection.VenueCollection;
import hkust.cse.calendar.gui.controller.CalMainControllerEvent;
import hkust.cse.calendar.gui.controller.DetailsController;
import hkust.cse.calendar.gui.controller.LoginControllerEvent;
import hkust.cse.calendar.gui.controller.UserRemovalController;
import hkust.cse.calendar.gui.controller.VenueRemovalController;
import hkust.cse.calendar.gui.view.base.BaseApptListView;
import hkust.cse.calendar.gui.view.base.BaseCalMainView;
import hkust.cse.calendar.gui.view.base.BaseCalMonthView;
import hkust.cse.calendar.gui.view.base.BaseCalMainView.CalMainViewEvent;
import hkust.cse.calendar.gui.view.base.BaseLoginView.LoginViewEvent;
import hkust.cse.calendar.gui.view.base.BaseNotificationItemView.NotificationItemViewEvent;
import hkust.cse.calendar.gui.view.base.BaseMonthSelectorView;
import hkust.cse.calendar.gui.view.base.BaseNotificationItemView;
import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.model.Notification;
import hkust.cse.calendar.model.User;
import hkust.cse.calendar.model.Venue;
import hkust.cse.calendar.utils.GenListener;
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
	private JPanel notificationWrapper;
	
	private JButton timeMachineBtn;
	private JButton scheduleBtn;
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
		functionButton.addActionListener(this);
		
		notificationButton = new JButton();
		notificationButton.setUI(new ToolBarButtonUI("notification.png", "notificationBadging.png"));
		notificationButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		notificationButton.setOpaque(false);
		notificationButton.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		notificationButton.setText("0");
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
		
		functionPanel = new JPanel();
		functionPanel.setLayout(new GridLayout(0, 2, 5, 5));
		functionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		functionPanel.setBackground(Color.WHITE);

		adminFunctionPanel = new JPanel();
		adminFunctionPanel.setLayout(new GridLayout(0, 2, 5, 5));
		adminFunctionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		adminFunctionPanel.setBackground(Color.WHITE);
		
		timeMachineBtn = createFunctionBtn("timemachine.png");
		timeMachineBtn.setToolTipText("Tune Time");
		timeMachineBtn.setText("Time Machine");
		
		scheduleBtn = createFunctionBtn("add.png");
		scheduleBtn.setToolTipText("Add Appointment");
		scheduleBtn.setText("Mannual Schedule");
		
		functionPanel.add(timeMachineBtn);
		functionPanel.add(scheduleBtn);
		//adminFunctionPanel.add(timeMachineBtn);
		//adminFunctionPanel.add(scheduleBtn);

		notificationPanel = new JPanel();
		notificationPanel.setOpaque(true);
		notificationPanel.setLayout(new GridLayout(1, 1));
		notificationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		notificationWrapper = new JPanel();
		notificationWrapper.setLayout(new GridLayout(0, 1, 0, 10));
		JScrollPane scrollPane = new JScrollPane(notificationWrapper);
		scrollPane.setMaximumSize(new Dimension(350, 300));
		scrollPane.setBorder(null);
		notificationPanel.add(scrollPane);
		
	}
	
	private JButton createFunctionBtn(String filename) {
		ImageIcon scheduleIcon = new ImageIcon(ImagePool.getInstance().getImage(filename).getScaledInstance(60, 60, BufferedImage.SCALE_SMOOTH));
		JButton btn = new JButton(scheduleIcon);
		btn.setUI(new BasicButtonUI());
		btn.setVerticalTextPosition(SwingConstants.BOTTOM);
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.addActionListener(this);
		btn.setOpaque(false);
		
		return btn;
	}
	
	private void setUserInfo(User user) {
		userButton.setText(user.getUsername());
		usernameLabel.setText(user.getUsername());
	}
	
	private void generateNotificationList(List<Notification> aNotification) {
		int i;
		notificationWrapper.removeAll();
		for(i = 0; i < aNotification.size(); i++) {
			notificationWrapper.add(generateNotificationItem(aNotification.get(i)));
		}
		notificationButton.setText(String.valueOf(aNotification.size()));
	}
	
	private BaseNotificationItemView generateNotificationItem(Notification n) {
		String message;
		BaseNotificationItemView v;
		switch(n.getType()) {
		case "VenueRemovalInitiated":
			final Venue venue = (Venue)n.getBody();
			
			message = "Venue " + venue.getName() + " is to be removed";
			v = new PrimNotificationItemView("Removal of Venue", message, "venueremove.png", false);
			v.addNotificationItemEventListener(new GenListener<NotificationItemViewEvent>() {

				@Override
				public void fireEvent(NotificationItemViewEvent ev) {
					switch(ev.getCommand()) {
					case ACTIVATE:
						VenueRemovalController vCtrl = new VenueRemovalController(venue);
						vCtrl.start();
						break;
					}
				}
			});
			break;
		case "UserRemovalInitiated":
			final User user = (User)n.getBody();
			
			message = "You are to be removed.";
			v = new PrimNotificationItemView("Removal of User", message, "userremove.png", false);
			v.addNotificationItemEventListener(new GenListener<NotificationItemViewEvent>() {

					@Override
					public void fireEvent(NotificationItemViewEvent ev) {
						switch(ev.getCommand()) {
						case ACTIVATE:
							UserRemovalController vCtrl = new UserRemovalController(user);
							vCtrl.start();
							break;
						}
					}
			});
			break;
		default:
			v = null;
		}
		return v;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == userButton) {
			new PopupPanel((Component)obj, userPanel);
		}
		else if(obj == functionButton) {
			//TODO: admin or user
			new PopupPanel((Component)obj, functionPanel);
		}
		else if(obj == notificationButton) {
			String text = notificationButton.getText();
			int n = 0;
			try {
				n = Integer.parseInt(text);
			}
			catch(NumberFormatException ex) {
				
			}
			if(text.length() > 0 && n > 0)
				new PopupPanel((Component)obj, notificationPanel);
		}
		else if(obj == logoutBtn) {
			CalMainViewEvent ev = new CalMainViewEvent(this, CalMainViewEvent.Command.LOGOUT);
			triggerCalMainViewEvent(ev);
		}
		else if(obj == exitBtn) {
			CalMainViewEvent ev = new CalMainViewEvent(this, CalMainViewEvent.Command.EXIT);
			triggerCalMainViewEvent(ev);
		}
		else if(obj == scheduleBtn) {
			CalMainViewEvent ev = new CalMainViewEvent(this, CalMainViewEvent.Command.MANUAL_SCHEDULE);
			triggerCalMainViewEvent(ev);
		}
		else if(obj == timeMachineBtn) {
			CalMainViewEvent ev = new CalMainViewEvent(this, CalMainViewEvent.Command.TIME_MACHINE);
			triggerCalMainViewEvent(ev);
		}
	}

	@Override
	public void fireEvent(CalMainControllerEvent e) {
		CalMainControllerEvent.Command command = e.getCommand();
		if(command == CalMainControllerEvent.Command.UPDATE_INFO) {
			User user = e.getUser();
			long selectedStamp = e.getSelectedDay();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			setTitle("DCalendar - " + user.getUsername() + " - " + format.format(new Date(selectedStamp)));
			setUserInfo(user);
			generateNotificationList(e.getaNotification());
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