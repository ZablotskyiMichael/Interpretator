package interpretator.component.impl.calculator.arithmetic;

import interpretator.component.UnaryCalculator;
import interpretator.component.impl.calculator.AbstractBinaryCalculator;
import interpretator.model.Expression;

public class DecrementUnaryCalculator extends AbstractBinaryCalculator implements UnaryCalculator {

	@Override
	public Object calculate(Expression expression) {
		Object value = expression.getValue();
		if (value instanceof Integer) {
			return (Integer) value - 1;
		} else if (value instanceof Double) {
			return (Double) value - 1;
		} else {
			throw createRuntimeException(value);
		}
	}
}
