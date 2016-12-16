package com.renjie120.tool;

import java.util.Calendar;

public class DateTool {
	public static String getStringCurrentDateTime() {
		Calendar rightNow = Calendar.getInstance();
		int intYear = rightNow.get(Calendar.YEAR);

		int intMonth = rightNow.get(Calendar.MONTH) + 1;
		String strMonth = null;
		if (intMonth < 10) {
			strMonth = "0" + intMonth;
		} else {
			strMonth = "" + intMonth;
		}
	
		int intDate = rightNow.get(Calendar.DATE);
		String strDate = null;
		if (intDate < 10) {
			strDate = "0" + intDate;
		} else {
			strDate = "" + intDate;
		}

		int intHour = rightNow.get(Calendar.HOUR_OF_DAY);
		String strHour = null;
		if (intHour < 10) {
			strHour = "0" + intHour;
		} else {
			strHour = "" + intHour;
		}

		int intMinute = rightNow.get(Calendar.MINUTE);
		String strMinute = null;
		if (intMinute < 10) {
			strMinute = "0" + intMinute;
		} else {
			strMinute = "" + intMinute;
		}

		int intSecond = rightNow.get(Calendar.SECOND);
		String strSecond = null;
		if (intSecond < 10) {
			strSecond = "0" + intSecond;
		} else {
			strSecond = "" + intSecond;
		}

		return intYear + "-" + strMonth + "-" + strDate + " " + strHour + ":"
				+ strMinute + ":" + strSecond;

	}

}
