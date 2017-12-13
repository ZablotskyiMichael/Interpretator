package interpretator.model.expression;

import interpretator.component.UnaryCalculator;
import interpretator.exception.RuntimeInterpretatorException;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Expression;

import static interpretator.Config.variableStorage;

public class UnaryIncDecPostfixExpression implements Expression {
	protected final VariableExpression operand;
	protected final UnaryCalculator unaryCalculator;

	public UnaryIncDecPostfixExpression(Expression operand, UnaryCalculator unaryCalculator) {
		if (!(operand instanceof VariableExpression)) {
			throw new SyntaxInterpretatorException("Only variable allowed here");
		}
		this.operand = (VariableExpression) operand;
		this.unaryCalculator = unaryCalculator;
	}

	@Override
	public Object getValue() {
		Object oldValue = operand.getValue();
		if (oldValue == null) {
			throw new RuntimeInterpretatorException("Variable " + operand.getVariableName() + " is not initialized");
		}
		variableStorage.put(operand.getVariableName(), unaryCalculator.calculate(operand));
		return oldValue;
	}

}