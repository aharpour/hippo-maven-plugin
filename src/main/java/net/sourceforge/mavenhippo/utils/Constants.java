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

    interface ContextParameter {
        String BEANS_ANNOTATED_CLASSES_PARAM = "hst-beans-annotated-classes";
    }

    interface PropertyName {
        String JCR_PRIMARY_TYPE = "jcr:primaryType";
        String JCR_MIXIN_TYPES = "jcr:mixinTypes";
        String HIPPOSYSEDIT_PATH = "hipposysedit:path";
        String HIPPOSYSEDIT_TYPE = "hipposysedit:type";
        String HIPPOSYSEDIT_URI = "hipposysedit:uri";
        String HIPPOSYSEDIT_MULTIPLE = "hipposysedit:multiple";
        String HIPPOSYSEDIT_SUPERTYPE = "hipposysedit:supertype";
        String HIPPOSYSEDIT_MIXIN = "hipposysedit:mixin";
        String CAPTION = "caption";
        String FIELD = "field";
    }

    interface PropertyValue {
        String HIPPOSYSEDIT_REMODEL = "hipposysedit:remodel";
        String FALSE = "false";
        String TRUE = "true";

    }

    interface NodeName {
        String HIPPOSYSEDIT_NODETYPE = "hipposysedit:nodetype";
        String DEFAULT = "_default_";
        String EDITOR_TEMPLATES = "editor:templates";
        String HIPPOSYSEDIT_PROTOTYPES = "hipposysedit:prototypes";
        String HIPPOSYSEDIT_PROTOTYPE = "hipposysedit:prototype";
        String CLUSTER_OPTIONS = "cluster.options";
        String VALUELIST_OPTIONS = "valuelist.options";
    }

    interface NodeType {
        String TEMPLATE_TYPE = "hipposysedit:templatetype";
        String HIPPOSYSEDIT_FIELD = "hipposysedit:field";
        String NT_UNSTRUCTURED = "nt:unstructured";
        String HIPPO_MIRROR = "hippo:mirror";
        String HIPPO_COMPOUND = "hippo:compound";
        String HIPPO_RESOURCE = "hippo:resource";
        String HIPPOGALLERYPICKER_IMAGELINK = "hippogallerypicker:imagelink";
        String HIPPOSTD_HTML = "hippostd:html";
    }

    interface Language {
        String PACKAGE_SEPARATOR = ".";
    }

}
