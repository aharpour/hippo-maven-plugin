package com.aharpour.ebrahim.gen.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aharpour.ebrahim.gen.AnnotationGenerator;
import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.MethodGenerator;
import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer.Type;
import com.aharpour.ebrahim.utils.FreemarkerUtils;

public class DefaultMethodGenerator implements MethodGenerator {
	
	private final ClassReference returnType;
	private final String fieldName;
	private final Type callType;
	
	public ClassReference getReturnType() {
		return returnType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public Type getCallType() {
		return callType;
	}
	
	public DefaultMethodGenerator(ClassReference returnType, String fieldName, Type callType) {
		this.returnType = returnType;
		this.fieldName = fieldName;
		this.callType = callType;
	}
	@Override
	public String getFragment() {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("type", returnType);
			model.put("fieldName", fieldName);
			String templatePath;
			switch (callType) {
			case HIPPO_HTML:
				//TODO
				templatePath = "";
				break;
			case LINKED_BEAN:
				templatePath = "";
				break;
			case PROPERTY:
				templatePath = "";
				break;
			default:
				templatePath = "";
				break;
			}
			return FreemarkerUtils
					.renderTemplate(templatePath, model);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public List<AnnotationGenerator> getAnnotations() {
		return new ArrayList<AnnotationGenerator>();
	}
	
	
}
