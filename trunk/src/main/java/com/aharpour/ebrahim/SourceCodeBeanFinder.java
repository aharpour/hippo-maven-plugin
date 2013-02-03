package com.aharpour.ebrahim;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.aharpour.ebrahim.model.HippoBeanClass;
import com.aharpour.ebrahim.utils.ContextParameterExtractor;

public class SourceCodeBeanFinder {

	private final File projectFolder;

	public SourceCodeBeanFinder(File projectFolder) {
		this.projectFolder = projectFolder;
	}

	public Map<String, HippoBeanClass> getBeansInProject(ContextParameterExtractor contextParameterExtractor) {
		Map<String, HippoBeanClass> result = new HashMap<String, HippoBeanClass>();
		// TODO Auto-generated method stub
		return result;
	}
}
