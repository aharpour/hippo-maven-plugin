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
package net.sourceforge.mavenhippo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class FileManager {
	private static final String JAVA_FILE_EXTENSION = ".java";
	public final File sourceRoot;
	private final Log log;

	public FileManager(File sourceRoot, Log log) throws FileManagerException {
		if (sourceRoot.exists()) {
			if (!sourceRoot.isDirectory()) {
				throw new IllegalArgumentException("sourceRoot is required to be a directory.");
			} else {
				if (forcefulDeletion(sourceRoot)) {
					log.info("source root folder was deleted.");
					createSourceRootFolder(sourceRoot, log);
				} else {
					if (sourceRoot.exists()) {
						log.warn("source root folder could not be cleaned.");
					} else {
						createSourceRootFolder(sourceRoot, log);
					}

				}
			}
		} else {
			createSourceRootFolder(sourceRoot, log);
		}
		this.log = log;
		this.sourceRoot = sourceRoot;
	}

	private void createSourceRootFolder(File sourceRoot, Log log) throws FileManagerException {
		if (sourceRoot.mkdirs()) {
			log.info("sourceRoot folder was created at \"" + sourceRoot.getAbsolutePath() + "\"");
		} else {
			log.error("Could not create the source root file. It is probably caused by lack of permissions.");
			throw new FileManagerException("Could not create the source root.");
		}
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

	private boolean forcefulDeletion(File file) {
		if (file == null) {
			throw new IllegalArgumentException("file argument is required");
		}
		boolean result = true;
		if (file.exists()) {
			if (file.isDirectory()) {
				for (File child : file.listFiles()) {
					result = forcefulDeletion(child) && result;
					if (child.isDirectory()) {
						result = child.delete() && result;
					}
				}
			}
			result = file.delete() && result;
		}
		return result;
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
