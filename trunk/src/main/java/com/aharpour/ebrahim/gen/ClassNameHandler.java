package com.aharpour.ebrahim.gen;

import java.util.Map;

import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.HippoBeanClass;

public abstract class ClassNameHandler extends ClasspathAware {

	public ClassNameHandler(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
		super(beansOnClassPath, beansInProject);
	}

	public abstract String getClassName(ContentTypeBean contentTypeBean);
}
