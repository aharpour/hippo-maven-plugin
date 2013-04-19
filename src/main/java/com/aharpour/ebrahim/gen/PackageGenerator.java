package com.aharpour.ebrahim.gen;

import java.util.Map;

import com.aharpour.ebrahim.model.HippoBeanClass;

public abstract class PackageGenerator extends ClasspathAware implements FragmentGenerator {

	public PackageGenerator(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
		super(beansOnClassPath, beansInProject);
	}

	public abstract String getPackageName();

	public abstract String[] getPackage();

}
