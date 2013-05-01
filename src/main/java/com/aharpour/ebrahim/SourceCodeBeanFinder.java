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
package com.aharpour.ebrahim;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.logging.Log;

import com.aharpour.ebrahim.model.HippoBeanClass;
import com.aharpour.ebrahim.parser.JavaAntlrParser;
import com.aharpour.ebrahim.parser.JavaParser;
import com.aharpour.ebrahim.utils.Constants;
import com.aharpour.ebrahim.utils.ContextParameterExtractor;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class SourceCodeBeanFinder {

	private static final String JAVA_EXTENSION = ".java";
	private static final String CLASSPATH = "classpath*:";
	private static final Pattern START_WITH_SLASH_PATTERN = Pattern.compile("^/*");
	private final int maximumDepthOfScan;
	private final File sourceDirectory;
	private final Log log;

	public SourceCodeBeanFinder(File sourceDirectory, int maximumDepthOfScan, Log log) {
		this.sourceDirectory = sourceDirectory;
		this.maximumDepthOfScan = maximumDepthOfScan;
		this.log = log;
	}

	public Map<String, HippoBeanClass> getBeansInProject(ContextParameterExtractor contextParameterExtractor) {
		Map<String, HippoBeanClass> result = new HashMap<String, HippoBeanClass>();
		String sourceDirectoryPath = sourceDirectory.getAbsolutePath();
		String beansAnnotatedClassesParam = contextParameterExtractor
				.getContextParameter(Constants.ContextParameter.BEANS_ANNOTATED_CLASSES_PARAM);
		if (beansAnnotatedClassesParam.startsWith(CLASSPATH)) {
			String[] split = StringUtils.split(beansAnnotatedClassesParam, ", \t\r\n");
			for (String slice : split) {
				File packageFolder = getPackageFolder(sourceDirectoryPath, slice);
				if (packageFolder.exists()) {
					scanPackageRecursively(packageFolder, result, maximumDepthOfScan);
				}
			}
		}
		return result;
	}

	private void scanPackageRecursively(File file, Map<String, HippoBeanClass> map, int depth) {
		if (depth > 0) {
			if (file.isDirectory()) {
				File[] subfolders = file.listFiles(DIRECTORY_FILTER);
				for (File subfolder : subfolders) {
					scanPackageRecursively(subfolder, map, depth - 1);
				}
				File[] javaFiles = file.listFiles(JAVA_FILE_FILTER);
				for (File javaFile : javaFiles) {
					scanFile(javaFile, map);
				}
			} else if (file.getName().endsWith(JAVA_EXTENSION)) {
				scanFile(file, map);
			}
		}
	}

	private void scanFile(File javaFile, Map<String, HippoBeanClass> map) {
		JavaParser javaParser = new JavaAntlrParser(log);
		Map<String, HippoBeanClass> parseResult = javaParser.parse(javaFile);
		if (parseResult != null) {
			map.putAll(parseResult);
		}
	}

	private File getPackageFolder(String sourceDirectoryPath, String slice) {
		String path = getPath(slice);
		String pack;
		if (sourceDirectoryPath.endsWith(File.separator)) {
			pack = sourceDirectoryPath + denormalizePath(path);
		} else {
			pack = sourceDirectoryPath + normalizePath(path);
		}
		return new File(pack);
	}

	private String getPath(String slice) {
		int start = 0;
		int end = slice.length();
		if (slice.startsWith(CLASSPATH)) {
			start = CLASSPATH.length();
			int indexOfStart = slice.indexOf('*', start);
			if (indexOfStart > start) {
				end = indexOfStart;
			}
		}
		return slice.substring(start, end);
	}

	private String normalizePath(String path) {
		String result;
		if (path.startsWith("/")) {
			result = path;
		} else {
			result = "/" + path;
		}
		return result.replace('/', File.separatorChar);
	}

	private String denormalizePath(String path) {
		String result;
		Matcher matcher = START_WITH_SLASH_PATTERN.matcher(path);
		if (matcher.find()) {
			result = path.substring(matcher.end());
		} else {
			result = path;
		}
		return result.replace('/', File.separatorChar);
	}

	private static final FileFilter DIRECTORY_FILTER = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}
	};
	private static final FileFilter JAVA_FILE_FILTER = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith(JAVA_EXTENSION);
		}
	};
}
