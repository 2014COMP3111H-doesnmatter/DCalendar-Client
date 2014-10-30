package hkust.cse.calendar.gui.view;

import hkust.cse.calendar.gui.controller.MonthSelectorControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseMonthSelectorView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.metal.MetalBorders.Flush3DBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class PrimMonthSelectorView extends BaseMonthSelectorView implements
		ActionListener
{
	private final static String[] holidays = {
			"New Years Day\nSpring Festival\n",
			"President's Day (US)\n",
			"",
			"Ching Ming Festival\nGood Friday\nThe day following Good Friday\nEaster Monday\n",
			"Labour Day\nThe Buddha's Birthday\nTuen Ng Festival\n",
			"",
			"Hong Kong Special Administrative Region Establishment Day\n",
			"Civic Holiday(CAN)\n",
			"",
			"National Day\nChinese Mid-Autumn Festival\nChung Yeung Festival\nThanksgiving Day\n",
			"Veterans Day(US)\nThanksgiving Day(US)\n", "Christmas\n" };

	private final static String[] months = { "January", "Feburary", "March", "April",
			"May", "June", "July", "August", "September", "October",
			"November", "December" };
	
	private BasicArrowButton eButton;
	private BasicArrowButton wButton;
	private JLabel yearLabel;
	private JComboBox<String> monthBox;
	private JTextPane note;
	private StyledDocument memDoc = null;
	private SimpleAttributeSet sab = null;
	
	public PrimMonthSelectorView()
	{
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(500, 300));

		JLabel textL = new JLabel("Important Days");
		textL.setForeground(Color.red);

		note = new JTextPane();
		note.setEditable(false);
		note.setBorder(new Flush3DBorder());
		memDoc = note.getStyledDocument();
		sab = new SimpleAttributeSet();
		StyleConstants.setBold(sab, true);
		StyleConstants.setFontSize(sab, 30);

		JPanel noteP = new JPanel();
		noteP.setLayout(new BorderLayout());
		noteP.add(textL, BorderLayout.NORTH);
		noteP.add(note, BorderLayout.CENTER);

		this.add(noteP, BorderLayout.CENTER);

		eButton = new BasicArrowButton(SwingConstants.EAST);
		eButton.setEnabled(true);
		eButton.addActionListener(this);
		wButton = new BasicArrowButton(SwingConstants.WEST);
		wButton.setEnabled(true);
		wButton.addActionListener(this);

		yearLabel = new JLabel("0000");
		monthBox = new JComboBox<String>();
		monthBox.setPreferredSize(new Dimension(200, 30));
		for (int cnt = 0; cnt < 12; cnt++)
			monthBox.addItem(months[cnt]);
		//We must bind listener here to avoid the event triggered above
		monthBox.addActionListener(this);

		JPanel yearGroup = new JPanel();
		yearGroup.setLayout(new FlowLayout());
		yearGroup.setBorder(new Flush3DBorder());
		yearGroup.add(wButton);
		yearGroup.add(yearLabel);
		yearGroup.add(eButton);
		yearGroup.add(monthBox);

		this.add(yearGroup, BorderLayout.NORTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		MonthSelectorViewEvent ev = new MonthSelectorViewEvent(this);
		if(obj == eButton) {
			ev.setCommand(MonthSelectorViewEvent.Command.NEXT_YEAR);
			triggerMonthSelectorViewEvent(ev);
		}
		else if(obj == wButton) {
			ev.setCommand(MonthSelectorViewEvent.Command.PREV_YEAR);
			triggerMonthSelectorViewEvent(ev);
		}
		else if(obj == monthBox) {
			ev.setCommand(MonthSelectorViewEvent.Command.UPDATE_MONTH);
			ev.setMonth(monthBox.getSelectedIndex());
			triggerMonthSelectorViewEvent(ev);
		}

	}

	@Override
	public void fireEvent(MonthSelectorControllerEvent e) {
		MonthSelectorControllerEvent.Command command = e.getCommand();
		switch(command) {
		case UPDATE:
			setYearMonth(e.getSelectedDay());
		}

	}
	
	private void setYearMonth(long stamp) {
		Date selected = new Date(stamp);
		yearLabel.setText(String.valueOf(selected.getYear() + 1900));
		if(monthBox.getSelectedIndex() != selected.getMonth()) {
			monthBox.setSelectedIndex(selected.getMonth());
		}
		try {
			memDoc.remove(0, memDoc.getLength());
			memDoc.insertString(0, holidays[selected.getMonth()], sab);
		} catch (BadLocationException e1) {

			e1.printStackTrace();
		}
	}

}
