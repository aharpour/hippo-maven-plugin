/*
 *  Copyright 2008-2013 Hippo B.V. (http://www.onehippo.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  slightly modified by Ebrahim Aharpour to run standalone
 */
package net.sourceforge.mavenhippo.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import net.sourceforge.mavenhippo.utils.exceptions.ScanningException;

import org.hippoecm.hst.util.ClasspathResourceScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

/**
 * MetadataReaderClasspathResourceScanner
 * <P>
 * This class implements {@link ClasspathResourceScanner} by leveraging Spring
 * Framework components.
 * </P>
 * <P>
 * This class can be used in either way, defining a bean in the application
 * context or invoking the static {@link #newInstance(ServletContext)} method.
 * The former is convenient for the applications which already use Spring
 * Framework, while the latter is convenient for the applications which don't
 * use Spring Framework.
 * </P>
 * 
 * @version $Id$
 */
public class MetadataReaderClasspathResourceScanner implements ClasspathResourceScanner, ResourceLoaderAware {

    private static Logger log = LoggerFactory.getLogger(MetadataReaderClasspathResourceScanner.class);

    private static ClassPathXmlApplicationContext singletonResourceLoader;

    private ResourcePatternResolver resourcePatternResolver;

    /**
     * Create an instance with setting the proper <CODE>ResourceLoader</CODE>
     * object. If there's any web application context already, then the existing
     * web application context is used as <CODE>ResourceLoader</CODE>.
     * Otherwise, it creates a <CODE>ResourceLoader</CODE> internally for
     * convenience.
     * 
     * @param classLoader
     * 
     * @param servletContext
     * @return
     */
    public static MetadataReaderClasspathResourceScanner newInstance(ClassLoader classLoader) {
        MetadataReaderClasspathResourceScanner scanner = new MetadataReaderClasspathResourceScanner();
        singletonResourceLoader = new ClassPathXmlApplicationContext();
        singletonResourceLoader.setClassLoader(classLoader);
        scanner.setResourceLoader(singletonResourceLoader);

        return scanner;
    }

    public MetadataReaderClasspathResourceScanner() {

    }

    @Override
    public Set<String> scanClassNamesAnnotatedBy(Class<? extends Annotation> annotationType, boolean matchSuperClass,
            String... locationPatterns) {
        if (resourcePatternResolver == null) {
            throw new IllegalStateException("ResourceLoader has not been set.");
        }

        if (locationPatterns == null || locationPatterns.length == 0) {
            throw new IllegalArgumentException("Provide one or more location pattern(s).");
        }

        Set<String> annotatedClassNames = new LinkedHashSet<String>();

        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

        try {
            TypeFilter typeFilter = new CustomAnnotationTypeFilter(annotationType, matchSuperClass);

            for (String locationPattern : locationPatterns) {
                Resource[] resources = resourcePatternResolver.getResources(locationPattern);

                for (Resource resource : resources) {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);

                    if (typeFilter.match(metadataReader, metadataReaderFactory)) {
                        annotatedClassNames.add(metadataReader.getAnnotationMetadata().getClassName());
                    }
                }
            }
        } catch (IOException e) {
            log.error("Cannot load resource(s) from the classpath.", e);
            throw new ScanningException("Cannot load resource(s) from the classpath.", e);
        }

        return annotatedClassNames;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
    }

    private static class CustomAnnotationTypeFilter extends AnnotationTypeFilter {

        private final boolean matchSuperClass;

        public CustomAnnotationTypeFilter(Class<? extends Annotation> annotationType, boolean matchSuperClass) {
            super(annotationType);
            this.matchSuperClass = matchSuperClass;
        }

        public CustomAnnotationTypeFilter(Class<? extends Annotation> annotationType, boolean considerMetaAnnotations,
                boolean matchSuperClass) {
            super(annotationType, considerMetaAnnotations);
            this.matchSuperClass = matchSuperClass;
        }

        @Override
        protected Boolean matchSuperClass(String superClassName) {
            if (matchSuperClass) {
                return super.matchSuperClass(superClassName);
            } else {
                return Boolean.FALSE;
            }
        }
    }
}