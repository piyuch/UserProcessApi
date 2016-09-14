package de.user.test.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {

	/**
	 * Format the white label user date of birth to this format: "yyyy-MM-dd"
	 * 
	 * @param date
	 * @return a formatted date "yyyy-MM-dd"
	 */
	public String DateFormatter(Date date) {
		SimpleDateFormat dateOfBirthFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateOfBirth = dateOfBirthFormat.format(date);
		return dateOfBirth;
	}

}
