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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class ImportRegistry {

    private Map<String, ClassReference> imports = new HashMap<String, ClassReference>();

    public ClassReference register(ClassReference reference) {
        if (imports.containsKey(reference.getSimpleName())) {
            ClassReference registeredClass = imports.get(reference.getSimpleName());
            if (registeredClass.getClassName().equals(reference.getClassName())) {
                reference.setUseSimpleName(true);
            } else {
                reference.setUseSimpleName(false);
            }
        } else {
            imports.put(reference.getSimpleName(), reference);
            reference.setUseSimpleName(true);
        }
        return reference;
    }

    public List<String> getImports() {
        List<String> result = new ArrayList<String>();
        for (Iterator<String> iterator = imports.keySet().iterator(); iterator.hasNext();) {
            result.add(imports.get(iterator.next()).getClassName());
        }
        Collections.sort(result, String.CASE_INSENSITIVE_ORDER);
        return result;
    }

}
