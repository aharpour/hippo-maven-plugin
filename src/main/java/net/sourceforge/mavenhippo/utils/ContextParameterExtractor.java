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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.maven.plugin.MojoExecutionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class ContextParameterExtractor {

    private static final String CONTEXT_PARAM_ELEMENT = "context-param";
    private static final String PARAM_VALUE_ELEMENT = "param-value";
    private static final String PARAM_NAME_ELEMENT = "param-name";

    private final Map<String, String> contextParameterMap = new HashMap<String, String>();

    public ContextParameterExtractor(File deploymentDescriptor) throws MojoExecutionException {
        try {
            if (deploymentDescriptor != null && deploymentDescriptor.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(deploymentDescriptor);
                NodeList contextParamElements = doc.getElementsByTagName(CONTEXT_PARAM_ELEMENT);
                for (int i = 0; i < contextParamElements.getLength(); i++) {
                    Element contextParam = getElement(contextParamElements.item(i));
                    if (contextParam != null) {
                        String paramName = getSingleElementText(contextParam, PARAM_NAME_ELEMENT).trim();
                        String paramValue = getSingleElementText(contextParam, PARAM_VALUE_ELEMENT).trim();
                        contextParameterMap.put(paramName, paramValue);
                    }
                }
            } else {
                throw new MojoExecutionException("could not find web descriptor file");
            }
        } catch (SAXException e) {
            throw new MojoExecutionException("SAXException while parsing Web Discriptor", e);
        } catch (IOException e) {
            throw new MojoExecutionException("IOException while parsing Web Discriptor", e);
        } catch (ParserConfigurationException e) {
            throw new MojoExecutionException("ParserConfigurationException while parsing Web Discriptor", e);
        }

    }

    public String getContextParameter(String paramterName) {
        return contextParameterMap.get(paramterName);
    }

    private String getSingleElementText(Element element, String subElementName) {
        String elementText = null;
        NodeList nodeList = element.getElementsByTagName(subElementName);
        if (nodeList.getLength() == 1) {
            Node node = nodeList.item(0);
            elementText = getElement(node).getTextContent();
        }
        return elementText;
    }

    private Element getElement(Node contextParam) {
        Element result = null;
        if (contextParam.getNodeType() == Node.ELEMENT_NODE) {
            result = ((Element) contextParam);
        }
        return result;
    }

}
