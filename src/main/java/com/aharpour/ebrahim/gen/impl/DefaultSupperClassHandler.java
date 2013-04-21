package com.aharpour.ebrahim.gen.impl;

import java.util.Map;

import org.hippoecm.hst.content.beans.standard.HippoItem;

import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.SupperClassHandler;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.HippoBeanClass;

public class DefaultSupperClassHandler extends SupperClassHandler {

	public DefaultSupperClassHandler(Map<String, HippoBeanClass> beansOnClassPath,
			Map<String, HippoBeanClass> beansInProject) {
		super(beansOnClassPath, beansInProject);
	}

	@Override
	public ClassReference getSupperClass(ContentTypeBean contentTypeBean) {
		// FIXME
		return new ClassReference(HippoItem.class);
	}

}
