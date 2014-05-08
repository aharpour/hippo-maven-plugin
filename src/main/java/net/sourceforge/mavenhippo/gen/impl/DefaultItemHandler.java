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
package net.sourceforge.mavenhippo.gen.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.mavenhippo.gen.ClassReference;
import net.sourceforge.mavenhippo.gen.ContentTypeItemHandler;
import net.sourceforge.mavenhippo.gen.HandlerResponse;
import net.sourceforge.mavenhippo.gen.ImportRegistry;
import net.sourceforge.mavenhippo.gen.MethodGenerator;
import net.sourceforge.mavenhippo.gen.PackageHandler;
import net.sourceforge.mavenhippo.gen.PropertyGenerator;
import net.sourceforge.mavenhippo.gen.impl.ContentTypeItemAnalyzer.AnalyzerResult;
import net.sourceforge.mavenhippo.model.HippoBeanClass;
import net.sourceforge.mavenhippo.model.ContentTypeBean.Item;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class DefaultItemHandler extends ContentTypeItemHandler {

    private final ContentTypeItemAnalyzer analyzer;

    public DefaultItemHandler(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
            Set<String> namespaces, PackageHandler packageGenerator) {
        super(beansOnClassPath, beansInProject, namespaces, packageGenerator);
        analyzer = new ContentTypeItemAnalyzer(beansOnClassPath, beansInProject, namespaces, packageGenerator);
    }

    @Override
    public HandlerResponse handle(Item item, ImportRegistry importRegistry) {
        AnalyzerResult analyzed = analyzer.analyze(item);
        ClassReference type = analyzed.getReturnType();
        importRegistry.register(type);
        List<PropertyGenerator> propertyGenerators = Collections
                .singletonList((PropertyGenerator) new DefaultPropertyGenerator(analyzed, item, importRegistry));
        List<MethodGenerator> methodGenerators = Collections
                .singletonList((MethodGenerator) new DefaultMethodGenerator(analyzed, item, importRegistry));

        return new HandlerResponse(propertyGenerators, methodGenerators);
    }

}
