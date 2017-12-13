package interpretator.model.operation;

import interpretator.model.Operation;
import interpretator.model.SourceLine;

import java.util.List;

public class WhileOperation extends SimpleOperation {
	private final List<Operation> trueOperations;

	public WhileOperation(String[] tokens, SourceLine sourceLine, List<Operation> trueOperations) {
		super(tokens, sourceLine);
		this.trueOperations = trueOperations;
	}

	public List<Operation> getTrueOperations() {
		return trueOperations;
	}
}
