package com.aharpour.ebrahim.gen;

import java.util.Map;

import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.HippoBeanClass;

public abstract class SupperClassHandler extends ClasspathAware {

	public SupperClassHandler(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
		super(beansOnClassPath, beansInProject);
	}

	public abstract ClassReference getSupperClass(ContentTypeBean contentTypeBean, ImportRegistry importRegistry);

}
