package com.aharpour.ebrahim.gen.impl;

import org.apache.commons.lang3.StringUtils;

import com.aharpour.ebrahim.gen.PackageGenerator;

public class DefaultPackageGenerator implements PackageGenerator {
	private final String[] packageName;

	public DefaultPackageGenerator(String[] packageName) {
		this.packageName = packageName;
	}

	@Override
	public String getFragment() {
		String result = "";
		String name = getPackageName();
		if (StringUtils.isNotBlank(name)) {
			result = "package " + name + ";";
		}
		return result;
	}

	@Override
	public String getPackageName() {
		return StringUtils.join(packageName, '.');
	}

	@Override
	public String[] getPackage() {
		return packageName;
	}

}
