package com.aharpour.ebrahim.gen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ImportRegistry {

	private Map<String, ClassReference> imports = new HashMap<String, ClassReference>();

	public ClassReference register(ClassReference reference) {
		if (imports.containsKey(reference.getSimpleName())) {
			ClassReference registeredClass = imports.get(reference.getSimpleName());
			if (registeredClass.getClassName().equals(reference.getClassName())) {
				reference.setUseSimpleName(true);
			} else {
				reference.setUseSimpleName(false);
			}
		} else {
			imports.put(reference.getSimpleName(), reference);
			reference.setUseSimpleName(true);
		}
		return reference;
	}

	public List<String> getImports() {
		List<String> result = new ArrayList<String>();
		for (Iterator<String> iterator = imports.keySet().iterator(); iterator.hasNext();) {
			result.add(imports.get(iterator.next()).getClassName());
		}
		Collections.sort(result, String.CASE_INSENSITIVE_ORDER);
		return result;
	}

}
