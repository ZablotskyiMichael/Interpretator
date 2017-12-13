package interpretator.model.expression;

import interpretator.component.UnaryCalculator;
import interpretator.model.Expression;

public class UnaryIncDecPreffixExpression extends UnaryIncDecPostfixExpression {
	public UnaryIncDecPreffixExpression(Expression operand, UnaryCalculator unaryCalculator) {
		super(operand, unaryCalculator);
	}

	@Override
	public Object getValue() {
		super.getValue();
		return operand.getValue();
	}
}
