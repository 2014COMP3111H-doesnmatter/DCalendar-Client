package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.controller.ApptSchedulerControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseApptSchedulerView;
import hkust.cse.calendar.model.Appointment;
import hkust.cse.calendar.model.Venue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class PrimApptSchedulerView extends BaseApptSchedulerView implements ActionListener {
	private JLabel yearL;
	private JTextField yearF;
	private JLabel monthL;
	private JComboBox<String> monthBox;
	private JLabel dayL;
	private JTextField dayF;
	private JComboBox<String> sTimeHBox;
	private JComboBox<String> sTimeMBox;
	private JComboBox<String> eTimeHBox;
	private JComboBox<String> eTimeMBox;
	
	private JComboBox<String> repeatBox;
	private JRadioButton tillDayBtn;
	private JRadioButton foreverBtn;
	private JLabel tillYearL;
	private JTextField tillYearF;
	private JLabel tillMonthL;
	private JComboBox<String> tillMonthBox;
	private JLabel tillDayL;
	private JTextField tillDayF;
	
	private JCheckBox needReminder;
	private JTextField reminderF;
	
	private JComboBox<Venue> venueBox;
	private JTextField titleField;

	private JButton saveBtn;
	private JButton cancelBtn;

	private JTextArea detailArea;
	
	private final static String[] months = { "Jan", "Feb", "Mar", "Apr",
		"May", "Jun", "Jul", "Aug", "Sep", "Oct",
		"Nov", "Dec" };
	private final static int FIELD_HEIGHT = 25;
	private final static String[] aFrequency = { "Once", "Daily", "Weekly", "Monthly" };
	
	public PrimApptSchedulerView() {
		this.setAlwaysOnTop(true);
		this.setModal(false);
		
		Container contentPane;
		contentPane = getContentPane();
		
		JPanel pDate = new JPanel();
		Border dateBorder = new TitledBorder(null, "DATE");
		pDate.setBorder(dateBorder);
		
		yearL = new JLabel("YEAR: ");
		pDate.add(yearL);
		yearF = getYearField();
		pDate.add(yearF);
		monthL = new JLabel("MONTH: ");
		pDate.add(monthL);
		monthBox = getMonthBox();
		pDate.add(monthBox);
		dayL = new JLabel("DAY: ");
		pDate.add(dayL);
		dayF = getDayField();
		pDate.add(dayF);
		
		JPanel psTime = new JPanel();
		Border stimeBorder = new TitledBorder(null, "START TIME");
		psTime.setBorder(stimeBorder);
		sTimeHBox = getHourBox();
		psTime.add(sTimeHBox);
		psTime.add(new JLabel(" : "));
		sTimeMBox = getMinuteBox();
		psTime.add(sTimeMBox);
		
		JPanel peTime = new JPanel();
		Border etimeBorder = new TitledBorder(null, "END TIME");
		peTime.setBorder(etimeBorder);
		eTimeHBox = getHourBox();
		peTime.add(eTimeHBox);
		peTime.add(new JLabel(" : "));
		eTimeMBox = getMinuteBox();
		peTime.add(eTimeMBox);

		JPanel pTime = new JPanel();
		pTime.setLayout(new GridLayout(1, 2));
		pTime.add(psTime);
		pTime.add(peTime);
		
		JPanel pFreq = new JPanel(new BorderLayout());
		Border freqBorder = new TitledBorder(null, "REPEAT");
		pFreq.setBorder(freqBorder);
		
		repeatBox = new JComboBox<String>();
		repeatBox.setPreferredSize(new Dimension(80, FIELD_HEIGHT));
		for(int i = 0; i < 4; i++) {
			repeatBox.addItem(aFrequency[i]);
		}
		repeatBox.addActionListener(this);
		
		JPanel pTill = new JPanel(new GridLayout(2, 1));
		ButtonGroup tillBtnGp = new ButtonGroup();
		JPanel pTillDay = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		tillDayBtn = new JRadioButton("TILL: ");
		pTillDay.add(tillDayBtn);
		tillBtnGp.add(tillDayBtn);
		tillDayBtn.addActionListener(this);
		
		tillYearL = new JLabel("YEAR: ");
		pTillDay.add(tillYearL);
		tillYearF = getYearField();
		pTillDay.add(tillYearF);
		tillMonthL = new JLabel("MONTH: ");
		pTillDay.add(tillMonthL);
		tillMonthBox = getMonthBox();
		pTillDay.add(tillMonthBox);
		tillDayL = new JLabel("DAY: ");
		pTillDay.add(tillDayL);
		tillDayF = getDayField();
		pTillDay.add(tillDayF);
		
		pTill.add(pTillDay);
		
		JPanel pUseless = new JPanel(new FlowLayout(FlowLayout.LEFT));
		foreverBtn = new JRadioButton("FOREVER");
		foreverBtn.addActionListener(this);
		pUseless.add(foreverBtn);
		tillBtnGp.add(foreverBtn);
		
		pTill.add(pUseless);
		
		pFreq.add(repeatBox, BorderLayout.NORTH);
		pFreq.add(pTill, BorderLayout.SOUTH);
		
		JPanel pReminder = new JPanel();
		Border reminderBorder = new TitledBorder(null, "REMINDER");
		pReminder.setBorder(reminderBorder);
		
		needReminder = new JCheckBox();
		pReminder.add(needReminder);
		pReminder.add(new JLabel("Remind me "));
		reminderF = new JTextField(4);
		reminderF.setPreferredSize(new Dimension(80, FIELD_HEIGHT));
		pReminder.add(reminderF);
		pReminder.add(new JLabel(" minutes ahead"));
		
		
		JPanel pVenue = new JPanel();
		Border venueBorder = new TitledBorder(null, "VENUE");
		pVenue.setBorder(venueBorder);
		pVenue.add(new JLabel("AT: "));
		venueBox = new JComboBox<Venue>();
		venueBox.setPreferredSize(new Dimension(100, FIELD_HEIGHT));
		pVenue.add(venueBox);
		

		JPanel pDetail = new JPanel();
		pDetail.setLayout(new BorderLayout());
		Border detailBorder = new TitledBorder(null, "Appointment Description");
		pDetail.setBorder(detailBorder);
		
		JPanel titleAndTextPanel = new JPanel();
		JLabel titleL = new JLabel("TITLE");
		titleField = new JTextField(15);
		titleField.setPreferredSize(new Dimension(300, FIELD_HEIGHT));
		titleAndTextPanel.add(titleL);
		titleAndTextPanel.add(titleField);
		pDetail.add(titleAndTextPanel, BorderLayout.NORTH);

		detailArea = new JTextArea(20, 30);
		detailArea.setEditable(true);
		JScrollPane detailScroll = new JScrollPane(detailArea);
		pDetail.add(detailScroll, BorderLayout.SOUTH);

		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		top.setBorder(new BevelBorder(BevelBorder.RAISED));
		top.add(pDate);
		top.add(pTime);
		top.add(pFreq);
		top.add(pReminder);
		top.add(pVenue);
		top.add(pDetail);

		contentPane.add(top);
		

		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(FlowLayout.RIGHT));

		saveBtn = new JButton("Save");
		saveBtn.addActionListener(this);
		panel2.add(saveBtn);

		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		panel2.add(cancelBtn);

		contentPane.add("South", panel2);
		
		resetDefault();
		pack();
		
		this.setVisible(false);
	}
	
	private void resetDefault() {
		yearF.setText("");
		monthBox.setSelectedIndex(0);
		dayF.setText("");
		sTimeHBox.setSelectedIndex(0);
		sTimeMBox.setSelectedIndex(0);
		eTimeHBox.setSelectedIndex(0);
		eTimeMBox.setSelectedIndex(0);
		repeatBox.setSelectedIndex(0);
		tillDayBtn.setEnabled(false);
		foreverBtn.setEnabled(false);
		tillYearF.setText("");
		tillYearF.setEnabled(false);
		tillMonthBox.setEnabled(false);
		tillDayF.setText("");
		tillDayF.setEnabled(false);
		needReminder.setSelected(false);
		reminderF.setText("");
		reminderF.setEnabled(false);
		if(venueBox.getItemCount() > 0)
			venueBox.setSelectedIndex(0);
		titleField.setText("Untitled");
		detailArea.setText("");
	}
	
	private void setToAppt(Appointment appt) {
		if(appt.getId() == 0) {
			setTitle("New");
		}
		else {
			setTitle("Edit");
			Date sTime = new Date(appt.getStartTime());
			Date eTime = new Date(appt.getEndTime());
			yearF.setText(String.valueOf(sTime.getYear() + 1900));
			monthBox.setSelectedIndex(sTime.getMonth());
			dayF.setText(String.valueOf(sTime.getDate()));
			sTimeHBox.setSelectedIndex(sTime.getHours());
			sTimeMBox.setSelectedIndex(sTime.getMinutes() / 15);
			eTimeHBox.setSelectedIndex(eTime.getHours());
			eTimeMBox.setSelectedIndex(eTime.getMinutes() / 15);
			setRepeat(appt.getFrequency());
			if(appt.getFrequency() > 0) {
				if(appt.getLastDay() == Long.MAX_VALUE) {
					tillDayBtn.setEnabled(false);
					foreverBtn.setEnabled(true);
					tillYearF.setEnabled(false);
					tillMonthBox.setEnabled(false);
					tillDayF.setEnabled(false);
				}
				else {
					tillDayBtn.setEnabled(true);
					foreverBtn.setEnabled(false);
					tillYearF.setEnabled(true);
					tillMonthBox.setEnabled(true);
					tillDayF.setEnabled(true);
				}
			}
			
			boolean isReminded = appt.getReminderAhead() > 0;
			needReminder.setSelected(isReminded);
			reminderF.setEnabled(isReminded);
			reminderF.setText(isReminded ? String.valueOf(appt.getReminderAhead()) : "");
			venueBox.setSelectedItem(aVenue.get(appt.getVenueId()));
			titleField.setText(appt.getName());
			detailArea.setText(appt.getInfo());
		}
	}
	
	private void setRepeat(int repeatType) {
		repeatBox.setSelectedIndex(repeatType);
		if(repeatType == 0) {
			tillDayBtn.setEnabled(false);
			foreverBtn.setEnabled(false);
			tillYearF.setEnabled(false);
			tillMonthBox.setEnabled(false);
			tillDayF.setEnabled(false);
		}
		else {
			tillDayBtn.setEnabled(true);
			foreverBtn.setEnabled(true);
			tillYearF.setEnabled(true);
			tillMonthBox.setEnabled(true);
			tillDayF.setEnabled(true);
		}
	}
	
	private JTextField getYearField() {
		JTextField field = new JTextField();
		field.setPreferredSize(new Dimension(100, FIELD_HEIGHT));
		return field;
	}
	
	private JTextField getDayField() {
		JTextField field = new JTextField();
		field.setPreferredSize(new Dimension(50, FIELD_HEIGHT));
		return field;
	}
	
	private JComboBox<String> getMonthBox() {
		JComboBox<String> box = new JComboBox<String>();
		box.setPreferredSize(new Dimension(60, FIELD_HEIGHT));
		for (int cnt = 0; cnt < 12; cnt++)
			box.addItem(months[cnt]);
		return box;
	}
	
	private JComboBox<String> getHourBox() {
		JComboBox<String> box = new JComboBox<String>();
		box.setPreferredSize(new Dimension(60, FIELD_HEIGHT));
		for (int cnt = 0; cnt < 24; cnt++)
			box.addItem(String.format("%02d", cnt));
		return box;
	}
	
	private JComboBox<String> getMinuteBox() {
		JComboBox<String> box = new JComboBox<String>();
		box.setPreferredSize(new Dimension(60, FIELD_HEIGHT));
		for (int cnt = 0; cnt < 4; cnt++)
			box.addItem(String.format("%02d", cnt * 15));
		return box;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == null) {
			
		}

	}

	@Override
	public void fireEvent(ApptSchedulerControllerEvent e) {
		ApptSchedulerControllerEvent.Command command = e.getCommand();
		switch(command) {
		case START:
			setVisible(true);
			break;
		case SAVE_PENDING:
			saveBtn.setEnabled(false);
			saveBtn.setText("Saving...");
			break;
		case PROMPT_ERR:
			saveBtn.setEnabled(true);
			saveBtn.setText("Save");
			String errTitle = e.getErrTitle();
			String errText = e.getErrText();
			JOptionPane.showMessageDialog(null, errText, errTitle, JOptionPane.ERROR_MESSAGE);
			break;
		}

	}

	@Override
	protected void updateVenueList() {
		venueBox.removeAllItems();
		Iterator<Entry<Long, Venue>> itr = this.aVenue.entrySet().iterator();
		while(itr.hasNext()) {
			Entry<Long, Venue> entry = itr.next();
			venueBox.addItem(entry.getValue());
		}
		pack();
		repaint();
	}

	@Override
	protected void updateAppt() {
		setToAppt(this.appt);
	}
	
}
