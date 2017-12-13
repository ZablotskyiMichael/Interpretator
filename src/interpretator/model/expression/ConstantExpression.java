package interpretator.model.expression;

import interpretator.model.Expression;

public class ConstantExpression implements Expression {
	private final Object value;

	public ConstantExpression(Object value) {
		this.value = value;
	}

	@Override
	public Object getValue() {
		return value;
	}
}
