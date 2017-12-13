package interpretator.component.impl.expressionresolver;

import interpretator.component.ExpressionResolver;
import interpretator.model.Expression;

public class SwitchExpressionResolver implements ExpressionResolver {

	@Override
	public boolean isSupport(String... tokens) {
		return tokens.length >= 1;
		// TODO method
	}

	@Override
	public Expression resolve(String... tokens) {
		// TODO Auto-generated method stub
		return null;
	}
}
