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
package net.sourceforge.mavenhippo;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import net.sourceforge.mavenhippo.model.HippoBeanClass;
import net.sourceforge.mavenhippo.utils.Constants;
import net.sourceforge.mavenhippo.utils.ContextParameterExtractor;
import net.sourceforge.mavenhippo.utils.MetadataReaderClasspathResourceScanner;
import net.sourceforge.mavenhippo.utils.ObjectConverterUtils;
import net.sourceforge.mavenhippo.utils.exceptions.BeanFinderException;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.util.ClasspathResourceScanner;
import org.xml.sax.SAXException;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class ClassPathBeanFinder {

    private static final String BEANS_ANNOTATED_CLASSES_CONF_PARAM_ERROR_MSG = "Please check HST-2 Content Beans Annotation configuration as servlet context parameter.\n"
            + "You can set a servlet context parameter named 'hst-beans-annotated-classes' with xml or classes location filter.\n"
            + "For example, '/WEB-INF/beans-annotated-classes.xml' or 'classpath*:org/examples/beans/**/*.class'";

    private final ClassLoader projectClassloader;
    private volatile Class<? extends Annotation> nodeAnnotationClass;

    public ClassPathBeanFinder(ClassLoader projectClassloader) {
        this.projectClassloader = projectClassloader;
    }

    public Map<String, HippoBeanClass> getBeansOnClassPath(ContextParameterExtractor contextParameterExtractor)
            throws MojoExecutionException {
        try {
            Map<String, HippoBeanClass> result = new HashMap<String, HippoBeanClass>();
            String beansAnnotatedClassesParam = contextParameterExtractor
                    .getContextParameter(Constants.ContextParameter.BEANS_ANNOTATED_CLASSES_PARAM);
            List<Class<? extends HippoBean>> annotatedClasses = getAnnotatedClasses(beansAnnotatedClassesParam);
            for (Class<? extends HippoBean> clazz : annotatedClasses) {
                if (clazz.isAnnotationPresent(getNodeAnnotationClass())) {
                    Annotation annotation = clazz.getAnnotation(getNodeAnnotationClass());
                    String nodeType = getJcrType(annotation);
                    result.put(nodeType, new HippoBeanClass(clazz.getPackage().getName(), clazz.getSimpleName(),
                            nodeType));
                }
            }
            return result;
        } catch (Exception e) {
            throw new MojoExecutionException(
                    "The following exception was throw while trying to get annotated classes.", e);
        }
    }

    private String getJcrType(Annotation annotation) {
        Class<? extends Annotation> nodeAnnotClass = getNodeAnnotationClass();
        try {
            Method method = nodeAnnotClass.getMethod("jcrType");
            return (String) method.invoke(annotation);
        } catch (Exception e) {
            throw new BeanFinderException(e.getLocalizedMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Annotation> getNodeAnnotationClass() {
        try {
            if (nodeAnnotationClass == null) {
                synchronized (this) {
                    if (nodeAnnotationClass == null) {
                        nodeAnnotationClass = (Class<? extends Annotation>) Class.forName(
                                "org.hippoecm.hst.content.beans.Node", true, projectClassloader);

                    }
                }
            }

            return nodeAnnotationClass;
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getLocalizedMessage(), e);
        }
    }

    private List<Class<? extends HippoBean>> getAnnotatedClasses(String beansAnnotatedClassesParam) throws IOException,
            SAXException, ParserConfigurationException, MojoExecutionException {
        List<Class<? extends HippoBean>> annotatedClasses;
        if (beansAnnotatedClassesParam.startsWith("classpath*:")) {
            ClasspathResourceScanner scanner;
            scanner = MetadataReaderClasspathResourceScanner.newInstance(projectClassloader);

            String[] split = StringUtils.split(beansAnnotatedClassesParam, ", \t\r\n");
            List<String> packages = new ArrayList<String>();
            packages.addAll(Arrays.asList(split));
            packages.add("classpath*:org/hippoecm/hst/content/beans/standard/**/*.class");
            Thread.currentThread().setContextClassLoader(projectClassloader);
            annotatedClasses = ObjectConverterUtils.getAnnotatedClasses(scanner, projectClassloader,
                    packages.toArray(new String[0]));
        } else {
            URL xmlConfURL = ClassLoader.getSystemClassLoader().getResource(beansAnnotatedClassesParam);
            if (xmlConfURL == null) {
                throw new MojoExecutionException(BEANS_ANNOTATED_CLASSES_CONF_PARAM_ERROR_MSG);
            }
            annotatedClasses = ObjectConverterUtils.getAnnotatedClasses(xmlConfURL);
        }
        return annotatedClasses;

    }

}
