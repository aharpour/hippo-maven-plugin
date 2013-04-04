package com.aharpour.ebrahim.gen;

import org.apache.commons.lang3.StringUtils;

public class ClassReference {

	private String className;
	private Class<?> clazz;
	private boolean useSimpleName;

	public ClassReference(Class<?> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("clazz parameter is required.");
		}
		this.clazz = clazz;
	}

	public ClassReference(String className) {
		if (StringUtils.isBlank(className)) {
			throw new IllegalArgumentException("className parameter is required.");
		}
		this.className = className.trim().intern();
	}

	public String getClassName() {
		String result;
		if (clazz != null) {
			result = clazz.getName();
		} else {
			result = className;
		}
		return result;
	}

	public String getSimpleName() {
		String result;
		if (clazz != null) {
			result = clazz.getSimpleName();
		} else {
			result = className.substring(Math.max(className.lastIndexOf('.'), 0));
		}
		return result;
	}
	
	public void setUseSimpleName(boolean useSimpleName) {
		this.useSimpleName = useSimpleName;
	}
	
	@Override
	public String toString() {
		String result;
		if (useSimpleName) {
			result = getSimpleName();
		} else {
			result = getClassName();
		}
		return result;
	}

}
