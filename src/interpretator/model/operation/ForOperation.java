package interpretator.model.operation;

import java.util.List;

import interpretator.model.Operation;
import interpretator.model.SourceLine;

public class ForOperation extends SimpleOperation {
	private final List<Operation> trueOperations;
	private final Operation initialization;
	private final String[] conditionalExpression;
	private final Operation update;

	public ForOperation(String[] tokens, SourceLine sourceLine, List<Operation> trueOperations, 
			Operation initialization, String[] conditionalExpression, Operation update) {
		super(tokens, sourceLine);
		this.trueOperations = trueOperations;
		this.initialization = initialization;
		this.conditionalExpression = conditionalExpression;
		this.update = update;
	}

	public List<Operation> getTrueOperations() {
		return trueOperations;
	}
	
	public Operation getInitialization() {
		return initialization;
	}

	public String[] getConditionalExpression() {
		return conditionalExpression;
	}

	public Operation getUpdate() {
		return update;
	}
}
