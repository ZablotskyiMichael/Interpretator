package interpretator.component.impl.calculator.logic;

import interpretator.component.UnaryCalculator;
import interpretator.component.impl.calculator.AbstractBinaryCalculator;
import interpretator.model.Expression;

public class LogicalNoUnaryCalculator extends AbstractBinaryCalculator implements UnaryCalculator {

	@Override
	public Object calculate(Expression expression) {
		Object value = expression.getValue();
		if (value instanceof Boolean) {
			return !(Boolean) value;
		} else {
			throw createRuntimeException(value);
		}
	}
}
