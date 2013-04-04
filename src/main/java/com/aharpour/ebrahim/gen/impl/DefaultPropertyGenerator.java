package com.aharpour.ebrahim.gen.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aharpour.ebrahim.gen.AnnotationGenerator;
import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.PropertyGenerator;
import com.aharpour.ebrahim.utils.FreemarkerUtils;

public class DefaultPropertyGenerator implements PropertyGenerator {

	private final ClassReference type;
	private final String fieldName;

	public DefaultPropertyGenerator(ClassReference type, String fieldName) {
		this.type = type;
		this.fieldName = fieldName;
	}

	@Override
	public String getFragment() {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("type", type);
			model.put("fieldName", fieldName);
			return FreemarkerUtils
					.renderTemplate("com/aharpour/ebrahim/gen/impl/default-property-generator.ftl", model);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<AnnotationGenerator> getAnnotations() {
		return new ArrayList<AnnotationGenerator>();
	}
}