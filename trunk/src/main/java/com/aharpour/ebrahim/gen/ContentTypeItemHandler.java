package com.aharpour.ebrahim.gen;

import com.aharpour.ebrahim.model.ContentTypeBean.Item;

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
	public HandlerResponse handle(Item item, ImportRegistry importRegistry);

}
