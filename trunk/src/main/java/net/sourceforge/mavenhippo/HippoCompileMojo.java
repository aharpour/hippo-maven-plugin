package net.sourceforge.mavenhippo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sourceforge.mavenhippo.FileManager.FileManagerException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.InstantiationStrategy;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

@Mojo(name = "compile", executionStrategy = "always", instantiationStrategy = InstantiationStrategy.SINGLETON, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresDirectInvocation = true, requiresOnline = false, requiresProject = true, requiresReports = false, threadSafe = false)
@Execute(goal = "compile", phase = LifecyclePhase.NONE)
public class HippoCompileMojo extends AbstractHippoMojo {

	private static final String JAVA = "*.java";

	public void execute() throws MojoExecutionException {
		try {
			String command = "javac " + getOutputDirectoryOption() + getClassPathOptions() + getClasses();
			getLog().debug("Executing command: " + command);
			Process compilerProcess = Runtime.getRuntime().exec(command);
			String error = getErrors(compilerProcess);
			if (StringUtils.isNotBlank(error)) {
				getLog().error(error);
				throw new MojoExecutionException("Compilation failure.");
			}

		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private String getErrors(Process compilerProcess) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintStream errors = new PrintStream(byteArrayOutputStream);

		BufferedReader errorStream = new BufferedReader(new InputStreamReader(compilerProcess.getErrorStream()));
		String line;
		while ((line = errorStream.readLine()) != null) {
			errors.println(line);
		}
		errors.flush();
		errors.close();
		String error = new String(byteArrayOutputStream.toByteArray());
		return error;
	}

	private String getClasses() throws FileManagerException {
		FileManager fileManager = new FileManager(sourceRoot, getLog(), false);
		File basePackage = fileManager.getPackage(parsePackageName(this.basePackage));
		//		getPackagesRecursively(basePackage, ) TODO
		String absolutePath = basePackage.getAbsolutePath();

		return (absolutePath.endsWith(File.separator) ? absolutePath + JAVA : absolutePath + File.separator + JAVA);
	}

	private List<File> getPackagesRecursively(File p, List<File> packages) {
		File[] children = p.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		for (File child : children) {
			packages.add(child);
			getPackagesRecursively(child, packages);
		}
		return packages;
	}

	private String getClassPathOptions() {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(getProjectClassPath())) {
			sb.append("-cp ").append(getProjectClassPath()).append(" ");
		}
		return sb.toString();
	}

	private String getOutputDirectoryOption() {
		StringBuffer sb = new StringBuffer();
		if (sourceRoot != null) {
			sb.append("-d ").append(project.getBuild().getOutputDirectory()).append(" ");
		}
		return sb.toString();
	}

	private String getProjectClassPath() {
		Set<Artifact> artifacts = project.getArtifacts();
		List<String> paths = new ArrayList<String>();
		for (Artifact artifact : artifacts) {
			if (artifact != null && artifact.getFile() != null) {
				paths.add(artifact.getFile().getAbsolutePath());
			}
		}
		return StringUtils.join(paths, ';');

	}

}
