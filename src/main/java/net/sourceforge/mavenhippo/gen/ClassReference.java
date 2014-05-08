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

import org.apache.commons.lang3.StringUtils;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class ClassReference {

    private String className;
    private Class<?> clazz;
    private boolean useSimpleName;

    public ClassReference(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz parameter is required.");
        }
        this.clazz = clazz;
    }

    public ClassReference(String className) {
        if (StringUtils.isBlank(className)) {
            throw new IllegalArgumentException("className parameter is required.");
        }
        this.className = className.trim().intern();
    }

    public String getClassName() {
        String result;
        if (clazz != null) {
            result = clazz.getName();
        } else {
            result = className;
        }
        return result;
    }

    public String getSimpleName() {
        String result;
        if (clazz != null) {
            result = clazz.getSimpleName();
        } else {
            result = className.substring(className.lastIndexOf('.') + 1);
        }
        return result;
    }

    public void setUseSimpleName(boolean useSimpleName) {
        this.useSimpleName = useSimpleName;
    }

    @Override
    public String toString() {
        String result;
        if (useSimpleName) {
            result = getSimpleName();
        } else {
            result = getClassName();
        }
        return result;
    }

}
