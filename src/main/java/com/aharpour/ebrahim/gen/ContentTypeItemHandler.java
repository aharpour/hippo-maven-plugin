package com.aharpour.ebrahim.gen;

import java.util.Map;
import java.util.Set;

import com.aharpour.ebrahim.model.ContentTypeBean.Item;
import com.aharpour.ebrahim.model.HippoBeanClass;

/**
 * @author Ebrahim Aharpour
 * 
 */
public abstract class ContentTypeItemHandler extends ClasspathAware {

	protected Set<String> namespaces;
	protected PackageHandler packageHandler;

	public ContentTypeItemHandler(Map<String, HippoBeanClass> beansOnClassPath,
			Map<String, HippoBeanClass> beansInProject, Set<String> namespaces, PackageHandler packageHandler) {
		super(beansOnClassPath, beansInProject);
		this.namespaces = namespaces;
		this.packageHandler = packageHandler;
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
