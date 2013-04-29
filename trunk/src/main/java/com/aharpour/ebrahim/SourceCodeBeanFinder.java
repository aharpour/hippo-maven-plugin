package com.aharpour.ebrahim;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.aharpour.ebrahim.model.HippoBeanClass;
import com.aharpour.ebrahim.utils.Constants;
import com.aharpour.ebrahim.utils.ContextParameterExtractor;

public class SourceCodeBeanFinder {

	private static final String CLASSPATH = "classpath*:";
	private static final Pattern START_WITH_SLASH_PATTERN = Pattern.compile("^/*");
	private final File sourceDirectory;

	public SourceCodeBeanFinder(File sourceDirectory) {
		this.sourceDirectory = sourceDirectory;
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

				}
			}
		}
		// ANTLRInputStream input = new ANTLRFileStream(source);
		// Java7Lexer lexer = new Java7Lexer(input);
		// CommonTokenStream tokens = new CommonTokenStream(lexer);
		// Java7Parser parser = new Java7Parser(tokens);
		// ParserRuleContext tree = parser.compilationUnit();
		// ParseTreeWalker walker = new ParseTreeWalker();
		/*
		 * walker.walk(new Java7Listener() {
		 * 
		 * }
		 */
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

			} else {

			}
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
			return pathname.getName().endsWith(".java");
		}
	};
}
