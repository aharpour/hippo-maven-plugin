package com.aharpour.ebrahim.gen;

import com.aharpour.ebrahim.model.ContentTypeItemBean;

/**
 * @author Ebrahim Aharpour
 *
 */
public interface ContentTypeItemHandler {
	
	/**
	 * 
	 * 
	 * @param item
	 * @param importRegistry
	 * @return null if does not want to handle this item.
	 */
	public HandlerResponse handle(ContentTypeItemBean item, ImportRegistry importRegistry);

}
