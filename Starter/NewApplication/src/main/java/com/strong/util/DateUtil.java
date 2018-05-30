package com.strong.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtil {
	public static String timestampToString(Timestamp ts) {
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		try {
			tsStr = sdf.format(ts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}
}
