package interpretator.component.impl.operations;

import interpretator.component.OperationInterpretator;
import interpretator.exception.RuntimeInterpretatorException;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Expression;
import interpretator.model.Operation;

import static interpretator.Config.*;

public class VarDeclarationOperationInterpretator implements OperationInterpretator {
	@Override
	public String getKeyWord() {
		return "var";
	}

	@Override
	public void interpert(Operation operation) {
		validateOperation(operation);
		String varName = operation.getToken(1);
		variableVerificator.verify(varName);
		Object varValue = calculateValue(operation);
		if (variableStorage.isDefined(varName)) {
			throw new RuntimeInterpretatorException(String.format("Variable '%s' already defined", varName));
		}
		variableStorage.put(varName, varValue);
	}

	private Object calculateValue(Operation operation) {
		if (operation.getTokenCount() == 2) {
			return null;
		} else {
			String[] expressionTokens = operation.getSubTokens(3);
			Expression expression = expressionResolver.resolve(expressionTokens);
			Object value = expression.getValue();
			if(value == VOID) {
				throw new SyntaxInterpretatorException("Void can't be out");
			}
			return value;
		}
	}

	private void validateOperation(Operation operation) {
		if (operation.getTokenCount() == 1) {
			throw new SyntaxInterpretatorException("Missing variable name");
		}
		if (operation.getTokenCount() == 3) {
			if ("=".equals(operation.getToken(2))) {
				throw new SyntaxInterpretatorException("Missing variable value");
			} else {
				throw new SyntaxInterpretatorException("Missing or invalid position of =");
			}
		}

		if (operation.getTokenCount() > 3) {
			if (!"=".equals(operation.getToken(2))) {
				throw new SyntaxInterpretatorException("Missing or invalid position of =");
			}
		}
	}
}
