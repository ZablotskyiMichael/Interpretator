package interpretator.component.impl;

import interpretator.component.ContextInterpretator;
import interpretator.component.OperationInterpretator;
import interpretator.exception.ExceptionHelper;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Operation;

public class ContextInterpretatorImpl implements ContextInterpretator {
	private OperationInterpretator[] operationInterpretators;

	public ContextInterpretatorImpl(OperationInterpretator[] operationInterpretators) {
		this.operationInterpretators = operationInterpretators;
	}

	@Override
	public void interpert(Operation operation) {
		ExceptionHelper.setCurrentSourceLine(operation.getSourceLine());
		for (OperationInterpretator interpretator : operationInterpretators) {
			if (interpretator.isSupport(operation)) {
				interpretator.interpert(operation);
				return;
			}
		}
		throw new SyntaxInterpretatorException("Unsupported operation: " + operation.getSourceLine().getStr());
	}
}
