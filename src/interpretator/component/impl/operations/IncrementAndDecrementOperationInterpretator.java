package interpretator.component.impl.operations;

import static interpretator.Config.expressionResolver;

import java.util.Set;

import interpretator.component.OperationInterpretator;
import interpretator.model.Operation;

public class IncrementAndDecrementOperationInterpretator implements OperationInterpretator {
	private final Set<String> supportOperators;

	public IncrementAndDecrementOperationInterpretator(Set<String> binaryAssignmentCalculatorMap) {
		this.supportOperators = binaryAssignmentCalculatorMap;
	}

	@Override
	public String getKeyWord() {
		return "";
	}

	@Override
	public boolean isSupport(Operation operation) {
		return operation.getTokenCount() == 2 && (supportOperators.contains(operation.getToken(0))
				|| supportOperators.contains(operation.getToken(1)));
	}

	@Override
	public void interpert(Operation operation) {
		expressionResolver.resolve(operation.getTokens()).getValue();
	}
}
