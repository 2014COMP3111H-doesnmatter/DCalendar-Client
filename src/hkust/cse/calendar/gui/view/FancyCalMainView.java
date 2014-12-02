package hkust.cse.calendar.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
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
	
	public FancyCalMainView() {
		super();
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		contentPane.add(createToolBar());
		
		JPanel lower = new JPanel();
		
		upperPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		wholePane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		wholePane.setTopComponent(upperPane);
		
		lower.add(wholePane);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				CalMainViewEvent ev = new CalMainViewEvent(this, CalMainViewEvent.Command.EXIT);
				triggerCalMainViewEvent(ev);
			}
		});
		
		contentPane.add(lower);
		
		pack();
		setVisible(false);
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
		
		panel.add(logoLabel);
		
		panel.add(Box.createHorizontalGlue());
		
		panel.add(userButton);
		panel.add(functionButton);
		panel.add(notificationButton);
		
		return panel;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == logoutMenuItem) {
			CalMainViewEvent ev = new CalMainViewEvent(this, CalMainViewEvent.Command.LOGOUT);
			triggerCalMainViewEvent(ev);
		}
		else if(obj == exitMenuItem) {
			CalMainViewEvent ev = new CalMainViewEvent(this, CalMainViewEvent.Command.EXIT);
			triggerCalMainViewEvent(ev);
		}
		else if(obj == mannualScheduleMenuItem) {
			CalMainViewEvent ev = new CalMainViewEvent(this, CalMainViewEvent.Command.MANUAL_SCHEDULE);
			triggerCalMainViewEvent(ev);
		}
		else if(obj == timeMachineMenuItem) {
			CalMainViewEvent ev = new CalMainViewEvent(this, CalMainViewEvent.Command.TIME_MACHINE);
			triggerCalMainViewEvent(ev);
		}
		else if(obj == venueMenuItem) {
			CalMainViewEvent ev = new CalMainViewEvent(this, CalMainViewEvent.Command.VENUE);
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