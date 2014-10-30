package hkust.cse.calendar.utils;

import hkust.cse.calendar.model.Appointment;

import java.util.Date;

public class DateTimeHelper {
	final static public long TIME_OF_DAY = 1000 * 60 * 60 * 24;
	final static public long TIME_OF_WEEK = TIME_OF_DAY * 7;
	final static public long MAX_TIME_OF_MONTH = TIME_OF_DAY * 31;
	final static public int MAX_DAY_IN_MONTH = 31;

	static public boolean isInMonth(long startOfMonth, Appointment appt) {
		Date nextMonth = new Date(startOfMonth + MAX_TIME_OF_MONTH);
		nextMonth.setDate(1);
		long nextMonthStamp = nextMonth.getTime();
		
		long startOfAppt = appt.getStartTime();
		long endOfAppt = appt.getLastDay();
		if(startOfAppt >= nextMonthStamp || endOfAppt < startOfMonth) {
			return false;
		}
		if(appt.getFrequency() == Appointment.Frequency.MONTHLY) {
			int dayInMonth = getDayInMonth(startOfMonth);
			Date eventStart = new Date(startOfAppt);
			return eventStart.getDate() <= dayInMonth;
		}
		return true;
	}
	
	static public boolean isInDay(long startOfDay, Appointment appt) {
		Date today = new Date(startOfDay);
		Date nextDay = new Date(startOfDay + TIME_OF_DAY);
		long nextDayStamp = nextDay.getTime();
		
		long startOfAppt = appt.getStartTime();
		long endOfAppt = appt.getLastDay();
		if(startOfAppt >= nextDayStamp || endOfAppt < startOfDay) {
			return false;
		}
		switch(appt.getFrequency()) {
		case Appointment.Frequency.WEEKLY:
			return today.getDay() == (new Date(startOfAppt)).getDay();
		case Appointment.Frequency.MONTHLY:
			return today.getDate() == (new Date(startOfAppt)).getDate();
		}
		return true;
	}

	static public long getStartOfMonth(long stamp) {
		Date date = new Date(stamp);
		date.setDate(1);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		stamp = date.getTime();
		return stamp - (stamp % 1000);
	}
	
	static public long getStartOfWeek(long stamp) {
		Date date = new Date(stamp);
		int dayOfWeek = date.getDay();
		date.setTime(stamp - TIME_OF_DAY * dayOfWeek);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		stamp = date.getTime();
		return stamp - (stamp % 1000);
	}
	
	static public long getStartOfDay(long stamp) {
		Date date = new Date(stamp);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		stamp = date.getTime();
		return stamp - (stamp % 1000);
	}
	
	static public int getDayInMonth(long stamp) {
		stamp = getStartOfMonth(stamp);
		Date thisMonth = new Date(stamp);
		Date nextMonth = new Date(stamp + MAX_TIME_OF_MONTH);
		nextMonth.setDate(1);
		return (int) ((nextMonth.getTime() - thisMonth.getTime()) / TIME_OF_DAY);
	}
	
	static public int getDifferenceInDay(long stampA, long stampB) {
		return (int)Math.floorDiv(stampA - stampB, TIME_OF_DAY);
	}
}