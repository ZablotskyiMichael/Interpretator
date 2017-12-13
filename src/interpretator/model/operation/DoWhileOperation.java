package interpretator.model.operation;

import interpretator.model.Operation;
import interpretator.model.SourceLine;

import java.util.List;

public class DoWhileOperation extends SimpleOperation {
	private final List<Operation> trueOperations;
	private final String[] conditionalExpression;

	public DoWhileOperation(String[] tokens, SourceLine sourceLine, List<Operation> trueOperations, String[] conditionalExpression) {
		super(tokens, sourceLine);
		this.trueOperations = trueOperations;
		this.conditionalExpression = conditionalExpression;
	}

	public List<Operation> getTrueOperations() {
		return trueOperations;
	}
	
	public String[] getConditionalExpression() {
		return conditionalExpression;
	}
}
