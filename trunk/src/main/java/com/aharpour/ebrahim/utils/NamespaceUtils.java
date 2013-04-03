package com.aharpour.ebrahim.utils;

public class NamespaceUtils {
	
	private NamespaceUtils() {
	}
	
	public static String getNamespace(String nodeName) {
		return nodeName.substring(0, Math.max(0, nodeName.lastIndexOf(':')));
	}

	public static String getSimpleName(String nodeName) {
		return nodeName.substring(nodeName.lastIndexOf(':') + 1);
	}

}
