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
public class NamespaceUtils {

	private NamespaceUtils() {
	}

	public static String getNamespace(String nodeName) {
		return nodeName.substring(0, Math.max(0, nodeName.lastIndexOf(':')));
	}

	public static String getSimpleName(String nodeName) {
		return nodeName.substring(nodeName.lastIndexOf(':') + 1);
	}

}
