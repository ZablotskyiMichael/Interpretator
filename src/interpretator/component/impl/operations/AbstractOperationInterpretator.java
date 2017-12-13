package interpretator.component.impl.operations;

import interpretator.component.VariableStorage;
import interpretator.exception.RuntimeInterpretatorException;
import interpretator.exception.TernimateException;
import interpretator.model.Expression;
import interpretator.model.Operation;

import java.util.List;

import static interpretator.Config.contextInterpretator;
import static interpretator.Config.expressionResolver;
import static interpretator.Config.variableStorage;

import static interpretator.utils.TypeUtils.getType;

public abstract class AbstractOperationInterpretator {

	protected void invokeBlock(List<Operation> trueOperations) {
		VariableStorage currentVariableStorage = variableStorage;
		variableStorage = variableStorage.createChildVariableStorage();
		try {
			for (Operation operation : trueOperations) {
				contextInterpretator.interpert(operation);
			}
		} finally {
			variableStorage = currentVariableStorage;
		}
	}

	protected boolean evaluate(String[] conditionalExpression) {
		Expression expression = expressionResolver.resolve(conditionalExpression);
		Object value = expression.getValue();
		if (value instanceof Boolean) {
			return ((Boolean) value);
		} else {
			throw new RuntimeInterpretatorException("Condition should have boolean type. Current type is "
					+ getType(value));
		}
	}

	protected void checkForTermination() {
		if (Thread.interrupted()) {
			throw new TernimateException();
		}
	}
}