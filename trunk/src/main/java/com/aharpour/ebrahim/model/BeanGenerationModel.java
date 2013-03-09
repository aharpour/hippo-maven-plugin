package com.aharpour.ebrahim.model;

public class BeanGenerationModel {
	private final ContentTypeBean contentTypeBean;
	private final HippoBeanClass beanOnClassPath;
	private final HippoBeanClass beanInProject;

	public BeanGenerationModel(ContentTypeBean contentTypeBean, HippoBeanClass beanOnClassPath,
			HippoBeanClass beanInProject) {
		this.contentTypeBean = contentTypeBean;
		this.beanOnClassPath = beanOnClassPath;
		this.beanInProject = beanInProject;
	}

	public ContentTypeBean getContentTypeBean() {
		return contentTypeBean;
	}

	public HippoBeanClass getBeanOnClassPath() {
		return beanOnClassPath;
	}

	public HippoBeanClass getBeanInProject() {
		return beanInProject;
	}
	
	public boolean hasBeanInProject() {
		return beanInProject != null;
	}
	
	public boolean hasBeanOnClassPath() {
		return beanOnClassPath != null;
	}
	
	
	

}
