package com.aharpour.ebrahim.gen;

import java.util.Map;

import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.HippoBeanClass;

public abstract class PackageHandler extends ClasspathAware {

	public PackageHandler(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
		super(beansOnClassPath, beansInProject);
	}

	public abstract PackageGenerator getPackageGenerator(ContentTypeBean contentType);

}
