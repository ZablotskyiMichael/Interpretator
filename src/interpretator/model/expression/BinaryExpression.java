package interpretator.model.expression;

import interpretator.component.BinaryCalculator;
import interpretator.model.Expression;

public class BinaryExpression implements Expression {
	private final Expression operand1;
	private final BinaryCalculator binaryCalculator;
	private final Expression operand2;

	public BinaryExpression(Expression operand1, BinaryCalculator binaryCalculator, Expression operand2) {
		this.operand1 = operand1;
		this.binaryCalculator = binaryCalculator;
		this.operand2 = operand2;
	}

	@Override
	public Object getValue() {
		return binaryCalculator.calculate(operand1, operand2);
	}
}
