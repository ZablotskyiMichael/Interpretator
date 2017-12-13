package interpretator.component.impl.calculator.logic;

import interpretator.component.BinaryCalculator;
import interpretator.component.impl.calculator.AbstractBinaryCalculator;
import interpretator.model.Expression;

public class LogicalAndBinaryCalculator extends AbstractBinaryCalculator implements BinaryCalculator {

	@Override
	public Object calculate(Expression expression1, Expression expression2) {
		Object value1 = expression1.getValue();
		if (value1 instanceof Boolean) {
			if ((Boolean) value1) {
				Object value2 = expression2.getValue();
				if (value2 instanceof Boolean) {
					return value2;
				} else {
					throw createRuntimeException(value1, value2);
				}
			} else {
				return Boolean.FALSE;
			}
		} else {
			throw createRuntimeException(value1);
		}
	}
}
