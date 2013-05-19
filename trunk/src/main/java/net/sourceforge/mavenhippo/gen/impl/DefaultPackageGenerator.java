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

import net.sourceforge.mavenhippo.gen.PackageGenerator;

import org.apache.commons.lang3.StringUtils;


/**
 * @author Ebrahim Aharpour
 * 
 */
public class DefaultPackageGenerator implements PackageGenerator {
	private final String[] packageName;

	public DefaultPackageGenerator(String[] packageName) {
		this.packageName = packageName;
	}

	@Override
	public String getFragment() {
		String result = "";
		String name = getPackageName();
		if (StringUtils.isNotBlank(name)) {
			result = "package " + name + ";";
		}
		return result;
	}

	@Override
	public String getPackageName() {
		return StringUtils.join(packageName, '.');
	}

	@Override
	public String[] getPackage() {
		return packageName;
	}

}
