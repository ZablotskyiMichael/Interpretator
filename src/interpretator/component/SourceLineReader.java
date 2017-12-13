package interpretator.component;

import interpretator.model.SourceLine;

public interface SourceLineReader {
	SourceLine[] read(String sourceName);
}
