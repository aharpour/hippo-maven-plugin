package com.aharpour.ebrahim.parser;

import java.io.File;
import java.util.Map;

import com.aharpour.ebrahim.model.HippoBeanClass;

public interface JavaParser {

	public Map<String, HippoBeanClass> parse(File file);

}
