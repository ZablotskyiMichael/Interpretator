package interpretator.component.impl.operations;

import interpretator.component.OperationInterpretator;
import interpretator.model.flow.BreakException;
import interpretator.model.operation.WhileOperation;
import interpretator.model.Operation;

public class WhileOperationInterpretator extends AbstractOperationInterpretator implements OperationInterpretator {
	@Override
	public String getKeyWord() {
		return "while";
	}

	@Override
	public void interpert(Operation operation) {
		WhileOperation whileOperation = (WhileOperation) operation;
		String[] conditionalExpression = operation.getSubTokens(2, operation.getTokenCount() - 2);
		while (evaluate(conditionalExpression)) {
			checkForTermination();
			try {
				invokeBlock(whileOperation.getTrueOperations());
			} catch (BreakException e) {
				break;
			}
		}
	}
}
