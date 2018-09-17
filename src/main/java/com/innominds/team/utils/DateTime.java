package com.innominds.team.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

	// Get the current system date

	public static String getDate() {

		Date currentDateTime = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern("MM-dd-yyyy");
		String date = simpleDateFormat.format(currentDateTime);

		return date;
	}

	// Get the current system date and time along with the zone

	public static String getDateTime() {

		Date curDateTime = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern("'Date:'MMMM d,yyyy 'Time:' hh:mm:ss a zz");
		String datetime = simpleDateFormat.format(curDateTime);

		return datetime;

	}

	// Get the current system date and time in customized format.

	public static String getSystemDateTime() {

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh-mm");
		Date date = new Date();
		return dateFormat.format(date);

	}

	// Get the system date and time in customized format for the history Tab

	public static String getDateTimeHistory() {

		Date currentDateTime = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern("yyyy-MM-dd hh:mm:ss");

		String datetime = simpleDateFormat.format(currentDateTime);

		return datetime;
	}

	// Get the current system time.

	public static String getTime() {

		Date currentDateTime = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern("'Time:'hh:mm:ss a zzz");

		String time = simpleDateFormat.format(currentDateTime);

		return time;
	}

	public static String getTime1() {

		Date currentDateTime = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern("hh.mm a");

		String time = simpleDateFormat.format(currentDateTime);

		return time;
	}

	public static String getDateTime1() {

		Date currentDateTime = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern("MMdd_HHMMss");

		String time = simpleDateFormat.format(currentDateTime);

		return time;
	}

	public static void main(String[] args) {
		getSystemDateTime();
	}

}
