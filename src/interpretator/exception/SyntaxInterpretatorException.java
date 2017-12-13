package interpretator.exception;

@SuppressWarnings("serial")
public class SyntaxInterpretatorException extends AbstractInterpretatorException {

	public SyntaxInterpretatorException(String message) {
		super(message);
	}

	public SyntaxInterpretatorException(String message, boolean hideSourceLine) {
		super(message, hideSourceLine);
	}

	@Override
	protected ExceptionType getExceptionType() {
		return ExceptionType.Syntax;
	}

}
