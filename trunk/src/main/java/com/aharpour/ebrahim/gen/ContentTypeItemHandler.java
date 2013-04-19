package com.aharpour.ebrahim.gen;

import java.util.Map;

import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.model.HippoBeanClass;

/**
 * @author Ebrahim Aharpour
 * 
 */
public abstract class ContentTypeItemHandler {

	protected final Map<String, HippoBeanClass> beansOnClassPath;
	protected final Map<String, HippoBeanClass> beansInProject;

	public ContentTypeItemHandler(Map<String, HippoBeanClass> beansOnClassPath,
			Map<String, HippoBeanClass> beansInProject) {
		this.beansInProject = beansInProject;
		this.beansOnClassPath = beansOnClassPath;
	}

	/**
	 * 
	 * 
	 * @param item
	 * @param importRegistry
	 * @return null if does not want to handle this item.
	 */
	public abstract HandlerResponse handle(Item item, ImportRegistry importRegistry);

}
