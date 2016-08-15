package net.sourceforge.mavenhippo.utils.exceptions;

public class NodeTypeDefinitionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public NodeTypeDefinitionException(String message) {
    super(message);
  }

  public NodeTypeDefinitionException(String message, Throwable cause) {
    super(message, cause);
  }
}
