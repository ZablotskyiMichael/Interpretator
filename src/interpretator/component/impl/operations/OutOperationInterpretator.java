package interpretator.component.impl.operations;

import interpretator.component.OperationInterpretator;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Expression;
import interpretator.model.Operation;

import static interpretator.Config.VOID;
import static interpretator.Config.expressionResolver;

public class OutOperationInterpretator implements OperationInterpretator {
	@Override
	public String getKeyWord() {
		return "out";
	}

	@Override
	public void interpert(Operation operation) {
		validateOperation(operation);
		String[] expressionTokens = operation.getSubTokens(1);
		Expression expression = expressionResolver.resolve(expressionTokens);
		Object value = expression.getValue();
		if(value == VOID) {
			throw new SyntaxInterpretatorException("Void can't be out");
		}
		System.out.print(value);

	}

	private void validateOperation(Operation operation) {
		if (operation.getTokenCount() == 1) {
			throw new SyntaxInterpretatorException("Missing expression");
		}
	}
}
