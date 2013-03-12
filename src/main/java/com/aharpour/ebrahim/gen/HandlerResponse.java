package com.aharpour.ebrahim.gen;

import java.util.List;

public class HandlerResponse {

	private final List<PropertyGenerator> propertyGenerators;
	private final List<MethodGenerator> methodGenerators;

	public HandlerResponse(List<PropertyGenerator> propertyGenerators, List<MethodGenerator> methodGenerators) {
		this.propertyGenerators = propertyGenerators;
		this.methodGenerators = methodGenerators;
	}

	public List<PropertyGenerator> getPropertyGenerators() {
		return propertyGenerators;
	}

	public List<MethodGenerator> getMethodGenerators() {
		return methodGenerators;
	}

}
