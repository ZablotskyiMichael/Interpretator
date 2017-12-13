package interpretator.component.impl.calculator.bit;

import interpretator.component.UnaryCalculator;
import interpretator.component.impl.calculator.AbstractBinaryCalculator;
import interpretator.model.Expression;

public class BitwiseComplimentUnaryCalculator extends AbstractBinaryCalculator implements UnaryCalculator {

	@Override
	public Object calculate(Expression expression) {
		Object value = expression.getValue();
		if (value instanceof Integer) {
			return ~(Integer) value;
		} else {
			throw createRuntimeException(value);
		}
	}
}