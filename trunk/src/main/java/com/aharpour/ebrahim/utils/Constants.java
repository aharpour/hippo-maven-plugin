package com.aharpour.ebrahim.utils;

public interface Constants {

	public interface ContextParameter {
		public static final String BEANS_ANNOTATED_CLASSES_PARAM = "hst-beans-annotated-classes";
	}

	public interface PropertyName {
		public static final String JCR_PRIMARY_TYPE = "jcr:primaryType";
		public static final String JCR_MIXIN_TYPES = "jcr:mixinTypes";
		public static final String HIPPOSYSEDIT_PATH = "hipposysedit:path";
		public static final String HIPPOSYSEDIT_TYPE = "hipposysedit:type";
		public static final String HIPPOSYSEDIT_MULTIPLE = "hipposysedit:multiple";
		public static final String HIPPOSYSEDIT_SUPERTYPE = "hipposysedit:supertype";
		public static final String CAPTION = "caption";
		public static final String FIELD = "field";
	}

	public interface PropertyValue {
		public static final String HIPPOSYSEDIT_REMODEL = "hipposysedit:remodel";
		public static final String FALSE = "false";
		public static final String TRUE = "true";

	}

	public interface NodeName {
		public static final String HIPPOSYSEDIT_NODETYPE = "hipposysedit:nodetype";
		public static final String DEFAULT = "_default_";
		public static final String EDITOR_TEMPLATES = "editor:templates";
		public static final String HIPPOSYSEDIT_PROTOTYPES = "hipposysedit:prototypes";
		public static final String HIPPOSYSEDIT_PROTOTYPE = "hipposysedit:prototype";
	}

	public interface NodeType {
		public static final String TEMPLATE_TYPE = "hipposysedit:templatetype";
		public static final String HIPPOSYSEDIT_FIELD = "hipposysedit:field";
		public static final String NT_UNSTRUCTURED = "nt:unstructured";
		public static final String HIPPO_MIRROR = "hippo:mirror";
		public static final String HIPPO_COMPOUND = "hippo:compound";
		public static final String HIPPO_RESOURCE = "hippo:resource";
		public static final String HIPPOSTD_HTML = "hippostd:html";
	}

}
