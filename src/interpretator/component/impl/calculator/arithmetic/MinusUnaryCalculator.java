package interpretator.component.impl.calculator.arithmetic;

import interpretator.component.UnaryCalculator;
import interpretator.component.impl.calculator.AbstractBinaryCalculator;
import interpretator.model.Expression;

public class MinusUnaryCalculator extends AbstractBinaryCalculator implements UnaryCalculator {
	@Override
	public Object calculate(Expression expression) {
		Object value = expression.getValue();
		if (value instanceof Integer) {
			return -(Integer) value;
		} else if (value instanceof Double) {
			return -(Double) value;
		} else {
			throw createRuntimeException(value);
		}
	}
}
