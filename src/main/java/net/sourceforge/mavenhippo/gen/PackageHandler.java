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
package net.sourceforge.mavenhippo.gen;

import java.util.Map;

import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.HippoBeanClass;

/**
 * @author Ebrahim Aharpour
 * 
 */
public abstract class PackageHandler extends ClasspathAware {

    private String[] basePackage = new String[] { "generated", "beans" };

    public PackageHandler(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject) {
        super(beansOnClassPath, beansInProject);
    }

    public abstract PackageGenerator getPackageGenerator(ContentTypeBean contentType);

    public void setBasePackage(String[] basePackage) {
        this.basePackage = basePackage != null ? basePackage.clone() : new String[0];
    }

    public String[] getBasePackage() {
        return basePackage != null ? basePackage.clone() : new String[0];
    }

}
