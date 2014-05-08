package net.sourceforge.mavenhippo.utils.exceptions;

import org.apache.maven.plugin.MojoExecutionException;

public class GeneratorException extends MojoExecutionException {

    private static final long serialVersionUID = 1L;

    public GeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

}
