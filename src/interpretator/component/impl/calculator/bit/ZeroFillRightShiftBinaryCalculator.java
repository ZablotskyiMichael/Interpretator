package interpretator.component.impl.calculator.bit;

import interpretator.component.BinaryCalculator;
import interpretator.component.impl.calculator.AbstractBinaryCalculator;
import interpretator.model.Expression;

public class ZeroFillRightShiftBinaryCalculator extends AbstractBinaryCalculator implements BinaryCalculator {
	@Override
	public Object calculate(Expression expression1, Expression expression2) {
		Object value1 = expression1.getValue();
		Object value2 = expression2.getValue();
		if (value1 instanceof Integer && value2 instanceof Integer) {
			return (Integer) value1 >>> (Integer) value2;
		}
		throw createRuntimeException(value1, value2);
	}
}