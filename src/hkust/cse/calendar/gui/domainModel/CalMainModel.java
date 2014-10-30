package hkust.cse.calendar.gui.domainModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.List;

import hkust.cse.calendar.utils.DateTimeHelper;
import hkust.cse.calendar.utils.EventSource;
import hkust.cse.calendar.utils.GenListener;

public class CalMainModel extends EventSource {
	private Date selectedDay = new Date();
	List<GenListener<CalMainModelEvent>> nListener = new ArrayList<GenListener<CalMainModelEvent>>();
	
	public CalMainModel() {
		trimDate();
	}
	
	CalMainModel(long stamp) {
		setSelectedDayStamp(stamp);
	}
	
	CalMainModel(Date date) {
		setSelectedDay(date);
	}

	public Date getSelectedDay() {
		return selectedDay;
	}

	public void setSelectedDay(Date selectedDay) {
		this.selectedDay = selectedDay;
		trimDate();
		triggerUpdate();
	}
	
	public long getSelectedDayStamp() {
		return selectedDay.getTime();
	}
	
	public void setSelectedDayStamp(long stamp) {
		stamp = DateTimeHelper.getStartOfDay(stamp);
		selectedDay.setTime(stamp);
		triggerUpdate();
	}
	
	public int getYear() {
		return selectedDay.getYear() + 1900;
	}
	
	/**
	 * Set current selected year, while preserving month and date.
	 * 
	 * <p><b>Implementation Note:</b>
	 * It may be frustrating if currently is on Feb. 29th and switch to a non-leap year. Default
	 * behaviour of Java will redirect to Mar. 1st of that year. But to ensure that the month
	 * is preserved. This function will adjust to Feb. 28th accordingly.</p>
	 * @see #setMonth
	 * @param year The year to set
	 */
	public void setYear(int year) {
		if(year == selectedDay.getYear() + 1900) {
			return;
		}
		int origDate = selectedDay.getDate();
		int origMonth = selectedDay.getMonth();
		selectedDay.setYear(year - 1900);
		if(selectedDay.getDate() != origDate) {
			//Set month back
			selectedDay.setMonth(origMonth);
			int dayInMonth = DateTimeHelper.getDayInMonth(selectedDay.getTime());
			selectedDay.setDate(dayInMonth - 1);
		}
		triggerUpdate();
	}
	
	public int getMonth() {
		return selectedDay.getMonth();
	}
	
	/**
	 * Set current selected month, while preserving year and date.
	 * 
	 * <p><b>Implementation Note:</b>
	 * It is well known that different month has different number of days.
	 * So as in {@link #setYear}, this method ensure the month is switched and
	 * will preserve date whenever possible.</p>
	 * @see #setYear
	 * @param month The month to set, range 0-11
	 */
	public void setMonth(int month) {
		if(month == selectedDay.getMonth()) {
			return;
		}
		int origDate = selectedDay.getDate();
		selectedDay.setMonth(month);
		if(selectedDay.getDate() != origDate) {
			//Set month again because 31/05 -> 31/04 = 01/05
			selectedDay.setMonth(month);
			int dayInMonth = DateTimeHelper.getDayInMonth(selectedDay.getTime());
			selectedDay.setDate(dayInMonth - 1);
		}
		triggerUpdate();
	}
	
	/**
	 * Set month and year together.
	 * The behaviour is similar to @link{#setYear} and @link{#stMonth}
	 * @see #setYear
	 * @see #setMonth
	 * @param year Year to set
	 * @param month Month to set, 0-11
	 */
	public void setYearMonth(int year, int month) {
		if(year == selectedDay.getYear() + 1900 && month == selectedDay.getMonth()) {
			return;
		}
		int origDate = selectedDay.getDate();
		selectedDay.setYear(year - 1900);
		selectedDay.setMonth(month);
		selectedDay.setMonth(month);
		selectedDay.setDate(origDate);
		if(selectedDay.getDate() != origDate) {
			//Set month again
			selectedDay.setMonth(month);
			int dayInMonth = DateTimeHelper.getDayInMonth(selectedDay.getTime());
			selectedDay.setDate(dayInMonth - 1);
		}
		triggerUpdate();
	}
	
	public int getDate() {
		return selectedDay.getDate();
	}
	
	public void setDate(int date) {
		selectedDay.setDate(date);
		triggerDayUpdate();
	}
	
	public void addModelEventListener(GenListener<CalMainModelEvent> listener) {
		nListener.add(listener);
	}
	
	protected void triggerUpdate() {
		CalMainModelEvent e = new CalMainModelEvent(this, CalMainModelEvent.Command.UPDATE);
		fireList(nListener, e);
	}
	
	protected void triggerDayUpdate() {
		CalMainModelEvent e = new CalMainModelEvent(this, CalMainModelEvent.Command.UPDATE_ONLY_DAY);
		fireList(nListener, e);
	}
	
	private void trimDate() {
		long stamp = selectedDay.getTime();
		stamp = DateTimeHelper.getStartOfDay(stamp);
		selectedDay.setTime(stamp);
	}
	
	final static public class CalMainModelEvent extends EventObject {
		static public enum Command {
			UPDATE,
			UPDATE_ONLY_DAY
		};
		private Command command;
		
		CalMainModelEvent(Object source) {
			super(source);
		}
		
		CalMainModelEvent(Object source, Command command) {
			super(source);
			this.setCommand(command);
		}

		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}
	}

}