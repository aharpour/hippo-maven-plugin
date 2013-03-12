package com.aharpour.ebrahim.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aharpour.ebrahim.model.ContentTypeBean;
import com.aharpour.ebrahim.model.HippoBeanClass;

public class BeanGenerator {
	
	private final Map<String, HippoBeanClass> beansOnClassPath;
	private final Map<String, HippoBeanClass> beansInProject;
	
	private PackageGenerator packageNameGenerator;
	private List<ContentTypeItemHandler> handlersChain;
	
	
	public BeanGenerator(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
		this.beansOnClassPath = beansOnClassPath;
		this.beansInProject = beansInProject;
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		
	}

	
	
	public String generateBean(ContentTypeBean contentTypeBean) {
		ImportRegistry importRegistry = new ImportRegistry();
		List<MethodGenerator> mehtods = new ArrayList<MethodGenerator>();
		List<PropertyGenerator> properties = new ArrayList<PropertyGenerator>();
		
		return null;//TODO
	}
	

}
