package interpretator.exception;

@SuppressWarnings("serial")
public class RuntimeInterpretatorException extends AbstractInterpretatorException {
	public RuntimeInterpretatorException(String message) {
		super(message);
	}

	@Override
	protected ExceptionType getExceptionType() {
		return ExceptionType.Runtime;
	}
}
