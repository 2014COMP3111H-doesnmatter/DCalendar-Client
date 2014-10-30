package hkust.cse.calendar.gui.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import hkust.cse.calendar.gui.controller.CalMonthControllerEvent;
import hkust.cse.calendar.gui.view.base.BaseCalMonthView;
import hkust.cse.calendar.utils.DateTimeHelper;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class PrimCalMonthView extends BaseCalMonthView {
	private JTable monthTable;
	private CalendarTableModel monthTableModel;
	private MouseListener mouseListener = new MouseAdapter() {
		public void mouseReleased(MouseEvent e) {
			int currentRow = monthTable.getSelectedRow();
			int currentCol = monthTable.getSelectedColumn();
			
			CalendarTableModel.CellObject obj = (CalendarTableModel.CellObject)monthTable.getModel().getValueAt(currentRow, currentCol);
			
			if(obj != null) {
				CalMonthViewEvent ev = new CalMonthViewEvent(this, CalMonthViewEvent.Command.SWITCH_DATE);
				ev.setDate(obj.getDay());
				triggerMonthViewEvent(ev);
			}
		}
	};
	
	public PrimCalMonthView() {
		monthTableModel = new CalendarTableModel();
		
		this.setLayout(new GridLayout(1, 1));
		this.setPreferredSize(new Dimension(536, 280));
		this.setMinimumSize(new Dimension(536, 280));
		
		monthTable = new JTable(monthTableModel);
		monthTable.setDefaultRenderer(CalendarTableModel.CellObject.class, new PrimTableCellRenderer());

		monthTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		monthTable.setRowHeight(40);
		monthTable.setRowSelectionAllowed(false);
		monthTable.setFillsViewportHeight(true);
		JTableHeader head = monthTable.getTableHeader();
		head.setReorderingAllowed(false);
		head.setResizingAllowed(false);
		
		monthTable.addMouseListener(mouseListener);
		
		JScrollPane scrollPane = new JScrollPane(monthTable);
		this.add(scrollPane);
	}

	@Override
	public void fireEvent(CalMonthControllerEvent e) {
		CalMonthControllerEvent.Command command = e.getCommand();
		if(command == CalMonthControllerEvent.Command.UPDATE_ALL) {
			monthTableModel.setMonth(e.getStartOfMonth(), e.getToday(), e.getOccupied());
		}
		else if(command == CalMonthControllerEvent.Command.UPDATE_OCCUPIED) {
			monthTableModel.setOccupied(e.getOccupied());
		}
		else if(command == CalMonthControllerEvent.Command.UPDATE_TODAY) {
			//Controller should ensure the update is valid
			monthTableModel.setToday(e.getToday());
		}
	}
	
	
	final static private class PrimTableCellRenderer extends DefaultTableCellRenderer {
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			      boolean hasFocus, int rowIndex, int vColIndex) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, rowIndex, vColIndex);
			CalendarTableModel.CellObject obj = (CalendarTableModel.CellObject)value;
			
			if(obj == null) {
				return this;
			}
			
			if(obj.isToday())
				setForeground(Color.RED);
			else
				setForeground(Color.BLACK);

			Font f = getFont();
			if(obj.hasEvent()) {
				setFont(f.deriveFont(f.getStyle() | Font.BOLD));
			}
			else {
				setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
			}
			setBackground(Color.WHITE);
			setHorizontalAlignment(SwingConstants.RIGHT);
			setVerticalAlignment(SwingConstants.TOP);
			
			return this;
		}
	}
	
	final static private class CalendarTableModel extends AbstractTableModel {
		final static private int DAY_IN_WEEK = 7;
		final static private int MAX_ROW = 6;
		final static private int MAX_DAY_IN_MONTH = 31;
		final static private String[] aDayName = { "Sunday", "Monday", "Tuesday",
			"Wednesday", "Thursday", "Friday", "Saturday" };
		
		private int dayInMonth;
		private int dayOfFirstDate;
		private CellObject[] aDay = new CellObject[MAX_DAY_IN_MONTH];
		private int today;

		public CalendarTableModel() {
			int i;
			for(i = 0; i < MAX_DAY_IN_MONTH; i++) {
				aDay[i] = new CellObject(i + 1, false, false);
			}
			today = -1;
			dayInMonth = MAX_DAY_IN_MONTH;
			dayOfFirstDate = 0;
		}
		
		public void setMonth(long startOfMonth, long todayStamp, boolean[] occupied) {
			dayInMonth = DateTimeHelper.getDayInMonth(startOfMonth);
			dayOfFirstDate = (new Date(startOfMonth)).getDay();
			if(today >= 0) {
				aDay[today].setToday(false);
			}
			today = DateTimeHelper.getDifferenceInDay(todayStamp, startOfMonth);
			if(today < 0 || today >= dayInMonth) {
				today = -1;
			}
			else {
				aDay[today].setToday(true);
			}
			setOccupied(occupied);
		}
		
		public void setToday(long todayStamp) {
			if(today > 0) {
				aDay[today].setToday(false);
				int r = today / DAY_IN_WEEK;
				int c = today % DAY_IN_WEEK;
				fireTableCellUpdated(r, c);
			}
			today = (new Date(todayStamp)).getDate() - 1;
			aDay[today].setToday(true);
			int r = today / DAY_IN_WEEK;
			int c = today % DAY_IN_WEEK;
			fireTableCellUpdated(r, c);
		}
		
		public void setOccupied(boolean[] occupied) {
			int i;
			for(i = 0; i < dayInMonth; i++) {
				aDay[i].setOccupied(occupied[i]);
			}
			fireTableDataChanged();
		}
		
		@Override
		public int getColumnCount() {
			return DAY_IN_WEEK;
		}

		@Override
		public int getRowCount() {
			return MAX_ROW;
		}
		
		@Override
		public Class<?> getColumnClass(int c) {
			return CellObject.class;
		}
		
		@Override
		public String getColumnName(int c) {
			return aDayName[c];
		}

		@Override
		public Object getValueAt(int r, int c) {
			int day = r * DAY_IN_WEEK + c - dayOfFirstDate;
			
			if(day < 0 || day >= dayInMonth)
				return null;
			
			return aDay[day];
		}
		
		final static public class CellObject {
			private int day;
			private boolean today;
			private boolean occupied;
			
			CellObject() {
				day = 0;
				today = false;
				occupied = false;
			}
			
			CellObject(int day, boolean today, boolean occupied) {
				this.day = day;
				this.today = today;
				this.occupied = occupied;
			}
			
			public void setDay(int day) {
				this.day = day;
			}
			public int getDay() {
				return day;
			}
			
			public void setToday(boolean today) {
				this.today = today;
			}
			public boolean isToday() {
				return today;
			}
			
			public void setOccupied(boolean occupied) {
				this.occupied = occupied;
			}
			public boolean hasEvent() {
				return occupied;
			}
			
			@Override
			public String toString() {
				return String.valueOf(day);
			}
		}
		
	}

}