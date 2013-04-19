package com.aharpour.ebrahim.gen;

import java.util.Map;

import com.aharpour.ebrahim.model.HippoBeanClass;

public abstract class ClasspathAware {

	protected final Map<String, HippoBeanClass> beansOnClassPath;
	protected final Map<String, HippoBeanClass> beansInProject;

	public ClasspathAware(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
		this.beansInProject = beansInProject;
		this.beansOnClassPath = beansOnClassPath;
	}

}
