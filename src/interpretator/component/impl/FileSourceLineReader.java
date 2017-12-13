package interpretator.component.impl;

import interpretator.component.SourceLineReader;
import interpretator.component.StringSignificantVerificator;
import interpretator.model.SourceLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileSourceLineReader implements SourceLineReader {
	private StringSignificantVerificator verificator;

	public FileSourceLineReader(StringSignificantVerificator verificator) {
		this.verificator = verificator;
	}

	@Override
	public SourceLine[] read(String sourceName) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(sourceName));
			List<SourceLine> res = new ArrayList<>();
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				if (verificator.isSignificant(line)) {
					res.add(new SourceLine(line, i + 1));
				}
			}
			return res.toArray(new SourceLine[0]);
		} catch (IOException e) {
			throw new RuntimeException("Can't read lines from file: " + sourceName, e);
		}
	}
}
