package com.aharpour.ebrahim.handlers;

import java.util.Map;
import java.util.Set;

import com.aharpour.ebrahim.gen.ContentTypeItemHandler;
import com.aharpour.ebrahim.gen.HandlerResponse;
import com.aharpour.ebrahim.gen.ImportRegistry;
import com.aharpour.ebrahim.gen.PackageHandler;
import com.aharpour.ebrahim.gen.annotation.Weight;
import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.model.HippoBeanClass;

@Weight(-0.1)
public class Handler2 extends ContentTypeItemHandler {

	public Handler2(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
			Set<String> namespaces, PackageHandler packageHandler) {
		super(beansOnClassPath, beansInProject, namespaces, packageHandler);
	}

	@Override
	public HandlerResponse handle(Item item, ImportRegistry importRegistry) {
		return null;
	}

}
