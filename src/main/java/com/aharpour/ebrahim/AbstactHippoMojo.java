package com.aharpour.ebrahim;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.settings.Settings;

public abstract class AbstactHippoMojo extends AbstractMojo {

	@Component
	protected MavenSession session;

	@Component
	protected MojoExecution mojo;

	@Component
	protected Settings settings;

}
