package com.aharpour.ebrahim.model;

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
		return packageName + "." + name;
	}

}