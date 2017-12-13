package interpretator.component.impl.operations;

import interpretator.component.OperationInterpretator;
import interpretator.model.operation.IfOperation;
import interpretator.model.Operation;

public class IfOperationInterpretator extends AbstractOperationInterpretator implements OperationInterpretator {
	@Override
	public String getKeyWord() {
		return "if";
	}

	@Override
	public void interpert(Operation operation) {
		IfOperation ifOperation = (IfOperation) operation;
		String[] conditionalExpression = operation.getSubTokens(2, operation.getTokenCount() - 2);
		if (evaluate(conditionalExpression)) {
			if (!ifOperation.getTrueOperations().isEmpty()) {
				invokeBlock(ifOperation.getTrueOperations());
			}
		} else {
			if (!ifOperation.getFalseOperations().isEmpty()) {
				invokeBlock(ifOperation.getFalseOperations());
			}
		}
	}
}
