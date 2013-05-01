/*
 *    Copyright 2013 Ebrahim Aharpour
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   
 *   Partially sponsored by Smile B.V
 */
package com.aharpour.ebrahim.gen.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aharpour.ebrahim.gen.AnnotationGenerator;
import com.aharpour.ebrahim.gen.ClassReference;
import com.aharpour.ebrahim.gen.ImportRegistry;
import com.aharpour.ebrahim.gen.MethodGenerator;
import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer.AnalyzerResult;
import com.aharpour.ebrahim.gen.impl.ContentTypeItemAnalyzer.Type;
import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.utils.FreemarkerUtils;
import com.aharpour.ebrahim.utils.NammingUtils;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class DefaultMethodGenerator implements MethodGenerator {

	private final ClassReference returnType;
	private final String fieldName;
	private final Type callType;
	private final String propertyName;
	private final boolean multiple;
	private ClassReference listClass;

	public ClassReference getReturnType() {
		return returnType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Type getCallType() {
		return callType;
	}

	public DefaultMethodGenerator(AnalyzerResult analyzerResult, Item item, ImportRegistry importRegistry) {
		this.returnType = analyzerResult.getReturnType();
		importRegistry.register(returnType);
		this.fieldName = item.getSimpleName();
		this.propertyName = item.getRelativePath();
		this.callType = analyzerResult.getType();
		this.multiple = item.isMultiple();
		if (multiple) {
			listClass = new ClassReference(List.class);
			importRegistry.register(listClass);
		}
	}

	@Override
	public String getFragment() {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("multiple", multiple);
			model.put("basicType", Type.PROPERTY == callType);
			model.put("type", returnType);
			if (multiple) {
				model.put("list", listClass);
			}
			model.put("fieldName", fieldName);
			model.put("methodName", NammingUtils.stringToClassName(fieldName));
			model.put("propertyName", propertyName);
			String templatePath;
			switch (callType) {
			case HIPPO_HTML:
				templatePath = "com/aharpour/ebrahim/gen/impl/html-method-generator.ftl";
				break;
			case LINKED_BEAN:
				templatePath = "com/aharpour/ebrahim/gen/impl/linked-method-generator.ftl";
				break;
			case PROPERTY:
				templatePath = "com/aharpour/ebrahim/gen/impl/property-method-generator.ftl";
				break;
			default:
				templatePath = "com/aharpour/ebrahim/gen/impl/node-method-generator.ftl";
				break;
			}
			return FreemarkerUtils.renderTemplate(templatePath, model);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<AnnotationGenerator> getAnnotations() {
		return new ArrayList<AnnotationGenerator>();
	}

}
