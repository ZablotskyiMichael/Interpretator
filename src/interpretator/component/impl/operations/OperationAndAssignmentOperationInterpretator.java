package interpretator.component.impl.operations;

import interpretator.component.OperationInterpretator;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Expression;
import interpretator.model.Operation;

import java.util.Set;

import static interpretator.Config.expressionResolver;
import static interpretator.Config.variableStorage;

public class OperationAndAssignmentOperationInterpretator implements OperationInterpretator {
	private Object[] supportOperators;

	public OperationAndAssignmentOperationInterpretator(Set<String> binaryAssignmentCalculatorMap) {
		this.supportOperators = binaryAssignmentCalculatorMap.toArray();
	}

	@Override
	public String getKeyWord() {
		return "";
	}

	@Override
	public boolean isSupport(Operation operation) {
		Object[] isSupportOperator = supportOperators;
		if (operation.getTokenCount() >= 2) {
			if (variableStorage.isDefined(operation.getToken(0))) {
				for (int i = 0; i < isSupportOperator.length; i++) {
					if (isSupportOperator[i].equals(operation.getToken(1))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void interpert(Operation operation) {
		validateOperation(operation);
		String varName = operation.getToken(0);
		Object varValue = calculateValue(operation, 0);
		variableStorage.put(varName, varValue);
	}

	private Object calculateValue(Operation operation, int subFrom) {
		if (operation.getTokenCount() == 2) {
			return null;
		} else {
			String[] expressionTokens = operation.getSubTokens(subFrom);
			Expression expression = expressionResolver.resolve(expressionTokens);
			return expression.getValue();
		}
	}

	private void validateOperation(Operation operation) {
		if (operation.getTokenCount() == 1) {
			throw new SyntaxInterpretatorException("Missing =");

		}
		if (operation.getTokenCount() == 2) {
			if ("=".equals(operation.getToken(1))) {
				throw new SyntaxInterpretatorException("Missing variable value");
			} else {
				throw new SyntaxInterpretatorException("Missing =");
			}
		}
		if (variableStorage.isDefined(operation.getToken(2)) && variableStorage.get(operation.getToken(0)) == null) {
			throw new SyntaxInterpretatorException("Variable " + operation.getToken(0) + " is not initialized");
		}
	}
}
