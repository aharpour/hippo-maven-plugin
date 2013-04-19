package com.aharpour.ebrahim.gen.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.ContentTypeItemHandler;
import com.aharpour.ebrahim.gen.HandlerResponse;
import com.aharpour.ebrahim.gen.ImportRegistry;
import com.aharpour.ebrahim.gen.MethodGenerator;
import com.aharpour.ebrahim.gen.PropertyGenerator;
import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer.AnalyzerResult;
import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.model.HippoBeanClass;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class DefaultItemHandler extends ContentTypeItemHandler {

	private final ContentTypeItemAnalyzer analyzer;

	public DefaultItemHandler(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
		super(beansOnClassPath, beansInProject);
		analyzer = new ContentTypeItemAnalyzer(beansOnClassPath, beansInProject);
	}

	@Override
	public HandlerResponse handle(Item item, ImportRegistry importRegistry) {
		AnalyzerResult analyzed = analyzer.analyze(item);
		ClassReference type = analyzed.getReturnType();
		importRegistry.register(type);
		List<PropertyGenerator> propertyGenerators = Collections
				.singletonList((PropertyGenerator) new DefaultPropertyGenerator(analyzed, item, importRegistry));
		List<MethodGenerator> methodGenerators = Collections
				.singletonList((MethodGenerator) new DefaultMethodGenerator(analyzed, item, importRegistry));

		return new HandlerResponse(propertyGenerators, methodGenerators);
	}

}
