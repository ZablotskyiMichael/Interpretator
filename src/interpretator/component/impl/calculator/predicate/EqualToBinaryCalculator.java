package interpretator.component.impl.calculator.predicate;

import interpretator.component.BinaryCalculator;
import interpretator.component.impl.calculator.AbstractBinaryCalculator;
import interpretator.model.Expression;

public class EqualToBinaryCalculator extends AbstractBinaryCalculator implements BinaryCalculator {

	@Override
	public Object calculate(Expression expression1, Expression expression2) {
		Object value1 = expression1.getValue();
		Object value2 = expression2.getValue();
		if (value1 == null || value2 == null) {
			throw createRuntimeException(value1, value2);
		}
		if (value1.getClass() == value2.getClass()) {
			return singleValueCalculator(value1, value2);
		} else if (value1 instanceof Number && value2 instanceof Number) {
			return singleValueCalculator(value1, value2);
		} else {
			throw createRuntimeException(value1, value2);
		}
	}

	private Object singleValueCalculator(Object value1, Object value2) {
		if (value1.equals(value2)) {
			return true;
		} else {
			return false;
		}
	}
}
