package com.aharpour.ebrahim.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NammingUtils {
	
	private static final String NONE_LETTER = "[^a-zA-Z0-9]+";
	private static final Pattern INITIAL_NUMBERS = Pattern.compile("^[0-9]+");


	private NammingUtils() {
	}
	
	public static String stringToClassName(String input) {
		String result = extractRowForm(input);
		result = removeInitialNumbers(result);
		return capitalizeTheFirstLetter(result);
	}
	
	public static String stringToMethodName(String input) {
		String result = extractRowForm(input);
		result = removeInitialNumbers(result);
		return decapitalizeTheFirstLetter(result);
	}

	private static String extractRowForm(String input) {
		StringBuilder result = new StringBuilder();
		String[] segments = input.split(NONE_LETTER);
		for (String segment : segments) {
			result.append(capitalizeTheFirstLetter(segment));
		}
		return result.toString();
	}
	
	
	static String capitalizeTheFirstLetter(String input) {
		String result;
		if (input.length() > 0) {
			result = input.substring(0, 1).toUpperCase() + input.substring(1);
		} else {
			result = input;
		}
		return result;
	}
	
	
	static String decapitalizeTheFirstLetter(String input) {
		String result;
		if (input.length() > 0) {
			result = input.substring(0, 1).toLowerCase() + input.substring(1);
		} else {
			result = input;
		}
		return result;
	}
	
	static String removeInitialNumbers(String input) {
		String result;
		Matcher matcher = INITIAL_NUMBERS.matcher(input);
		if (matcher.find()) {
			result = input.substring(matcher.end());
		} else {
			result = input;
		}
		return result;
	}

}
