package com.aharpour.ebrahim.handlers;

import java.util.Map;

import com.aharpour.ebrahim.gen.ContentTypeItemHandler;
import com.aharpour.ebrahim.gen.HandlerResponse;
import com.aharpour.ebrahim.gen.ImportRegistry;
import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.model.HippoBeanClass;

public class Handler1 extends ContentTypeItemHandler {

	public Handler1(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
		super(beansOnClassPath, beansInProject);
	}

	@Override
	public HandlerResponse handle(Item item, ImportRegistry importRegistry) {
		return null;
	}

}
