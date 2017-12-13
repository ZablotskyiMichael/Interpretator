package interpretator.component.impl.calculator.predicate;

import interpretator.component.BinaryCalculator;
import interpretator.component.impl.calculator.AbstractBinaryCalculator;
import interpretator.model.Expression;

public class LessOrEqualToBinaryCalculator extends AbstractBinaryCalculator implements BinaryCalculator {
	@Override
	public Object calculate(Expression expression1, Expression expression2) {
		Object value1 = expression1.getValue();
		Object value2 = expression2.getValue();
		if (value1 instanceof Number && value2 instanceof Number) {
			return ((Number) value1).doubleValue() <= ((Number) value2).doubleValue();
		} else {
			throw createRuntimeException(value1, value2);
		}
	}
}
