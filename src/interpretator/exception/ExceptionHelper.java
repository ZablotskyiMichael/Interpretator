package interpretator.exception;

import interpretator.model.SourceLine;

public class ExceptionHelper {
	private static SourceLine currentSourceLine;

	public static void setCurrentSourceLine(SourceLine currentSourceLine) {
		ExceptionHelper.currentSourceLine = currentSourceLine;
	}

	public static SourceLine getCurrentSourceLine() {
		return currentSourceLine;
	}
}
