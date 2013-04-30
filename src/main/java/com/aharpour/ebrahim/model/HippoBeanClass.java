package com.aharpour.ebrahim.model;

import org.apache.commons.lang3.StringUtils;

public class HippoBeanClass {
	public final String packageName;
	public final String name;
	public final String nodeType;

	public HippoBeanClass(String packageName, String name, String nodeType) {
		this.packageName = packageName;
		this.name = name;
		this.nodeType = nodeType;
	}

	public String getFullyQualifiedName() {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isBlank(name)) {
			sb.append(name);
		} else {
			sb.append(packageName).append('.').append(name);
		}
		return sb.toString();
	}
}
