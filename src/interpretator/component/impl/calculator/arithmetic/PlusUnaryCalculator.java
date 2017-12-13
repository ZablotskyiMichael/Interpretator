package interpretator.component.impl.calculator.arithmetic;

import interpretator.component.UnaryCalculator;
import interpretator.component.impl.calculator.AbstractBinaryCalculator;
import interpretator.model.Expression;

public class PlusUnaryCalculator extends AbstractBinaryCalculator implements UnaryCalculator {
	@Override
	public Object calculate(Expression expression) {
		Object value = expression.getValue();
		if (value instanceof Number) {
			return value;
		} else {
			throw createRuntimeException(value);
		}
	}
}
