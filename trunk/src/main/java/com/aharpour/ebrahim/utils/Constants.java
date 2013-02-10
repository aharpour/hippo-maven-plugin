package com.aharpour.ebrahim.utils;

public interface Constants {

	public interface ContextParameter {
		public static final String BEANS_ANNOTATED_CLASSES_PARAM = "hst-beans-annotated-classes";
	}

	public interface PropertyName {
		public static final String JCR_PRIMARY_TYPE = "jcr:primaryType";
		public static final String JCR_MIXIN_TYPES = "jcr:mixinTypes";
		public static final String CAPTION = "caption";
		public static final String FIELD = "field";
	}

	public interface PropertyValue {
		public static final String HIPPOSYSEDIT_REMODEL = "hipposysedit:remodel";
	}

	public interface NodeName {
		public static final String HIPPOSYSEDIT_NODETYPE = "hipposysedit:nodetype";
		public static final String DEFAULT = "_default_";
		public static final String EDITOR_TEMPLATES = "editor:templates";
	}

	public interface NodeType {
		public static final String TEMPLATE_TYPE = "hipposysedit:templatetype";
		public static final String HIPPOSYSEDIT_FIELD = "hipposysedit:field";
	}

}
