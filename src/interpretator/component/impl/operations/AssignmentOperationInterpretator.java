package interpretator.component.impl.operations;

import interpretator.component.OperationInterpretator;

import static interpretator.Config.expressionResolver;

import java.util.Arrays;

import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Expression;
import interpretator.model.Operation;
import interpretator.model.expression.VariableExpression;

public class AssignmentOperationInterpretator implements OperationInterpretator {

	@Override
	public String getKeyWord() {
		return "";
	}

	@Override
	public boolean isSupport(Operation operation) {
		if (operation.getToken(0).equals("for")) {
			return false;
		}
		return Arrays.asList(operation.getTokens()).contains("=");
	}

	@Override
	public void interpert(Operation operation) {
		int index = Arrays.asList(operation.getTokens()).indexOf("=");
		String[] before = Arrays.copyOfRange(operation.getTokens(), 0, index);
		String[] after = Arrays.copyOfRange(operation.getTokens(), index+1, operation.getTokenCount());
		Expression varExp = expressionResolver.resolve(before);
		if(!(varExp instanceof VariableExpression)) {
			throw new SyntaxInterpretatorException("Variable exprected");
		}
		Expression valueExpression = expressionResolver.resolve(after);
		((VariableExpression)varExp).setValue(valueExpression.getValue());
	}


}
