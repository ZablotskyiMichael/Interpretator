package interpretator.model;

import java.util.Arrays;

public interface Operation {

	String[] getTokens();

	SourceLine getSourceLine();

	default int getTokenCount() {
		return getTokens().length;
	}

	default String getToken(int index) {
		return getTokens()[index];
	}

	default String[] getSubTokens(int from, int to) {
		return Arrays.copyOfRange(getTokens(), from, to);
	}

	default String[] getSubTokens(int from) {
		return getSubTokens(from, getTokenCount());
	}
}
