package interpretator.model.expression;

import interpretator.component.UnaryCalculator;
import interpretator.model.Expression;

public class UnaryExpression implements Expression {
	private final Expression operand;
	private final UnaryCalculator unaryCalculator;

	public UnaryExpression(Expression operand, UnaryCalculator unaryCalculator) {
		this.operand = operand;
		this.unaryCalculator = unaryCalculator;
	}

	@Override
	public Object getValue() {
		return unaryCalculator.calculate(operand);
	}
}
