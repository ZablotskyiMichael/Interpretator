package interpretator.exception;

import interpretator.model.SourceLine;

import static interpretator.exception.ExceptionHelper.getCurrentSourceLine;

@SuppressWarnings("serial")
public abstract class AbstractInterpretatorException extends RuntimeException {
	private final String message;

	public AbstractInterpretatorException(String message) {
		this.message = buildErrorMessage(getExceptionType(), getCurrentSourceLine(), message);
	}

	public AbstractInterpretatorException(String message, boolean hideSourceLine) {
		this.message = buildErrorMessage(getExceptionType(), null, message);
	}

	protected String buildErrorMessage(ExceptionType exceptionType, SourceLine sourceLine, String message) {
		if (sourceLine == null) {
			return String.format("%s error - %s", exceptionType, message);
		} else {
			return String.format("%s error [Line %s] - %s", exceptionType, sourceLine.getNumber(), message);
		}

	}

	protected abstract ExceptionType getExceptionType();

	@Override
	public String getMessage() {
		return message;
	}

	protected enum ExceptionType {
		Syntax,

		Runtime;
	}
}
