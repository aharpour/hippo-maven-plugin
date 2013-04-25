package com.aharpour.ebrahim.gen.impl;

import java.util.Map;

import com.aharpour.ebrahim.gen.ClassNameHandler;
import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.HippoBeanClass;
import com.aharpour.ebrahim.utils.NammingUtils;

public class DefaultClassNameHandler extends ClassNameHandler {

	public DefaultClassNameHandler(Map<String, HippoBeanClass> beansOnClassPath,
			Map<String, HippoBeanClass> beansInProject) {
		super(beansOnClassPath, beansInProject);
	}

	@Override
	public String getClassName(ContentTypeBean contentTypeBean) {
		return NammingUtils.stringToClassName(contentTypeBean.getSimpleName());
	}

}
