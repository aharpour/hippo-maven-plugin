package com.aharpour.ebrahim.gen.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aharpour.ebrahim.gen.AnnotationGenerator;
import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.ImportRegistry;
import com.aharpour.ebrahim.gen.PropertyGenerator;
import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer.AnalyzerResult;
import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer.Type;
import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.utils.FreemarkerUtils;

public class DefaultPropertyGenerator implements PropertyGenerator {

	private final ClassReference type;
	private final String fieldName;
	private final boolean multiple;
	private final Type propertyType;
	private ClassReference listClass;

	public DefaultPropertyGenerator(AnalyzerResult analyzerResult, Item item, ImportRegistry importRegistry) {
		this.type = analyzerResult.getReturnType();
		this.propertyType = analyzerResult.getType();
		this.fieldName = item.getSimpleName();
		this.multiple = item.isMultiple();
		importRegistry.register(type);
		if (this.multiple) {
			listClass = new ClassReference(List.class);
			importRegistry.register(listClass);
		}
	}

	@Override
	public String getFragment() {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("multiple", multiple);
			if (multiple) {
				model.put("list", listClass);
			}
			model.put("type", type);
			model.put("fieldName", fieldName);
			model.put("basicType", Type.PROPERTY == propertyType);
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