/*
 *    Copyright 2013 Ebrahim Aharpour
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   
 *   Partially sponsored by Smile B.V
 */
package net.sourceforge.mavenhippo.utils;

/**
 * @author Ebrahim Aharpour
 * 
 */
public interface Constants {

	public interface ContextParameter {
		public static final String BEANS_ANNOTATED_CLASSES_PARAM = "hst-beans-annotated-classes";
	}

	public interface PropertyName {
		public static final String JCR_PRIMARY_TYPE = "jcr:primaryType";
		public static final String JCR_MIXIN_TYPES = "jcr:mixinTypes";
		public static final String HIPPOSYSEDIT_PATH = "hipposysedit:path";
		public static final String HIPPOSYSEDIT_TYPE = "hipposysedit:type";
		public static final String HIPPOSYSEDIT_URI = "hipposysedit:uri";
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
		public static final String HIPPOGALLERYPICKER_IMAGELINK = "hippogallerypicker:imagelink";
		public static final String HIPPOSTD_HTML = "hippostd:html";
	}

	public interface Language {
		public static final String PACKAGE_SEPARATOR = ".";
	}

}
