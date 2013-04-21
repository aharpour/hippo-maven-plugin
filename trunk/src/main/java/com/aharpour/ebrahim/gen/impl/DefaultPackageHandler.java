package com.aharpour.ebrahim.gen.impl;

import java.util.Map;

import com.aharpour.ebrahim.gen.PackageGenerator;
import com.aharpour.ebrahim.gen.PackageHandler;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.HippoBeanClass;

public class DefaultPackageHandler extends PackageHandler {

	public DefaultPackageHandler(Map<String, HippoBeanClass> beansOnClassPath,
			Map<String, HippoBeanClass> beansInProject) {
		super(beansOnClassPath, beansInProject);
	}

	@Override
	public PackageGenerator getPackageGenerator(ContentTypeBean contentType) {
		// FIXME
		return new DefaultPackageGenerator(basePackage);
	}

}
