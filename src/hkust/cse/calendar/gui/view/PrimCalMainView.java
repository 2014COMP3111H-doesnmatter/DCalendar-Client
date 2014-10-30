package hkust.cse.calendar.gui.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import hkust.cse.calendar.gui.controller.CalMainControllerEvent;
import hkust.cse.calendar.gui.controller.LoginControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseApptListView;
import hkust.cse.calendar.gui.view.base.BaseCalMainView;
import hkust.cse.calendar.gui.view.base.BaseCalMonthView;
import hkust.cse.calendar.gui.view.base.BaseLoginView.LoginViewEvent;

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
public class PrimCalMainView extends BaseCalMainView implements ActionListener {
	private JSplitPane upperPane;
	private JSplitPane wholePane;
	
	private JMenuItem logoutMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem mannualScheduleMenuItem;
	private JMenuItem timeMachineMenuItem;
	
	public PrimCalMainView() {
		super();
		Container contentPane = this.getContentPane();
		
		
		upperPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		wholePane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		wholePane.setTopComponent(upperPane);
		
		contentPane.add(wholePane);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				CalMainViewEvent ev = new CalMainViewEvent(this, CalMainViewEvent.Command.EXIT);
				triggerCalMainViewEvent(ev);
			}
		});
		
		this.setJMenuBar(createMenuBar());
		
		pack();
		setVisible(false);
	}
	
	JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		//Begin of Access Menu
		JMenu accessMenu = new JMenu("Access");
		accessMenu.setMnemonic('A');
		accessMenu.getAccessibleContext().setAccessibleDescription(
				"Account Access Management");
		
		//Add Items
		logoutMenuItem = new JMenuItem("Logout");
		logoutMenuItem.setMnemonic('L');
		logoutMenuItem.getAccessibleContext().setAccessibleDescription("For user logout");
		logoutMenuItem.addActionListener(this);
		accessMenu.add(logoutMenuItem);
		
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setMnemonic('E');
		exitMenuItem.getAccessibleContext().setAccessibleDescription("Exit Program");
		exitMenuItem.addActionListener(this);
		accessMenu.add(exitMenuItem);
		
		menuBar.add(accessMenu);
		//End of Access Menu
		
		//Begin of Appointment Menu
		JMenu apptMenu = new JMenu("Appointment");
		apptMenu.setMnemonic('p');
		apptMenu.getAccessibleContext().setAccessibleDescription(
				"Appointment Management");
		
		//Add Items
		mannualScheduleMenuItem = new JMenuItem("Manual Scheduling");
		mannualScheduleMenuItem.setMnemonic('M');
		mannualScheduleMenuItem.getAccessibleContext().setAccessibleDescription(
				"Mannually add an appointment");
		mannualScheduleMenuItem.addActionListener(this);
		apptMenu.add(mannualScheduleMenuItem);
		
		menuBar.add(apptMenu);
		//End of Appointment Menu
		
		//Begin of Timemachine Menu
		JMenu timeMachineMenu = new JMenu("Time Machine");
		timeMachineMenu.setMnemonic('T');
		timeMachineMenu.getAccessibleContext().setAccessibleDescription(
				"Manage Time Machine");
		

		timeMachineMenuItem = new JMenuItem("Tune Time");
		timeMachineMenuItem.setMnemonic('u');
		timeMachineMenuItem.getAccessibleContext().setAccessibleDescription(
				"How time flies");
		timeMachineMenuItem.addActionListener(this);
		timeMachineMenu.add(timeMachineMenuItem);
		//End of Timemachine menu
		
		menuBar.add(timeMachineMenu);
		
		return menuBar;
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
	}

	@Override
	public void fireEvent(CalMainControllerEvent e) {
		CalMainControllerEvent.Command command = e.getCommand();
		if(command == CalMainControllerEvent.Command.UPDATE_INFO) {
			String username = e.getUsername();
			long selectedStamp = e.getSelectedDay();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			setTitle("DCalendar - " + username + " - " + format.format(new Date(selectedStamp)));
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
	
}