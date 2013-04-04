package com.aharpour.ebrahim.gen.impl;

import java.util.Map;

import com.aharpour.ebrahim.gen.ContentTypeItemHandler;
import com.aharpour.ebrahim.gen.HandlerResponse;
import com.aharpour.ebrahim.gen.ImportRegistry;
import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.model.HippoBeanClass;

/**
 * @author Ebrahim Aharpour
 *
 */
public class DefaultItemHandler extends ContentTypeItemHandler {

	public DefaultItemHandler(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
		super(beansOnClassPath, beansInProject);
	}

	@Override
	public HandlerResponse handle(Item item, ImportRegistry importRegistry) {
		System.out.println(item.getType());
		return null;
	}

}
