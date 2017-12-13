package interpretator.model.operation;

import interpretator.model.Operation;
import interpretator.model.SourceLine;

public class SimpleOperation implements Operation {
	private final String[] tokens;
	private final SourceLine sourceLine;

	public SimpleOperation(String[] tokens, SourceLine sourceLine) {
		this.tokens = tokens;
		this.sourceLine = sourceLine;
	}

	@Override
	public String[] getTokens() {
		return tokens;
	}

	@Override
	public SourceLine getSourceLine() {
		return sourceLine;
	}

	@Override
	public String toString() {
		return String.join(" ", tokens);
	}
}
