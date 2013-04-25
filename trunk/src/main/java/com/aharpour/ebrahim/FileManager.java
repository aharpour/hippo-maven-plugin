package com.aharpour.ebrahim;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

public class FileManager {
	private static final String JAVA_FILE_EXTENSION = ".java";
	public final File sourceRoot;
	private final Log log;

	public FileManager(File sourceRoot, Log log) throws FileManagerException {
		if (sourceRoot.exists()) {
			if (!sourceRoot.isDirectory()) {
				throw new IllegalArgumentException("sourceRoot is required to be a directory.");
			}
		} else {
			if (sourceRoot.mkdirs()) {
				log.info("sourceRoot folder was created at \"" + sourceRoot.getAbsolutePath() + "\"");
			} else {
				log.error("Could not create the source root file. It is probably caused by lack of permissions.");
				throw new FileManagerException("Could not create the source root.");
			}
		}
		this.log = log;
		this.sourceRoot = sourceRoot;
	}

	public File getPackage(String[] packagePath) throws FileManagerException {
		String foldersPath = StringUtils.join(packagePath, File.separatorChar);
		File result = new File(sourceRoot + File.separator + foldersPath);
		if (result.exists()) {
			if (!result.isDirectory()) {
				throw new FileManagerException("there is already a file at \"" + result.getAbsolutePath()
						+ "\" but it is not a folder.");
			}
		} else {
			if (!result.mkdirs()) {
				throw new FileManagerException("attemped to create folders failed.");
			} else {
				log.info("the following folder(s) " + foldersPath + " were created.");
			}
		}
		return result;
	}

	public File getClassFile(File pack, String className) throws FileManagerException {
		try {
			File result = new File(pack.getAbsolutePath() + File.separator + className + JAVA_FILE_EXTENSION);
			if (result.exists()) {
				if (!result.isFile()) {
					throw new FileManagerException("A folder with same name as \"" + className + JAVA_FILE_EXTENSION
							+ "\" already exists at " + pack.getAbsolutePath());
				}
			} else {
				pack.createNewFile();
			}
			return result;
		} catch (IOException e) {
			throw new FileManagerException("File to create a file with the name \"" + className + JAVA_FILE_EXTENSION
					+ "\" at " + pack.getAbsolutePath(), e);
		}
	}

	public class FileManagerException extends MojoExecutionException {

		private static final long serialVersionUID = 1L;

		public FileManagerException(String message, Throwable cause) {
			super(message, cause);
		}

		public FileManagerException(String message) {
			super(message);
		}

	}

}
