package interpretator.component.impl.expressionresolver;

import interpretator.component.ExpressionResolver;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Expression;

public class ExpressionResolverImpl implements ExpressionResolver {
	private final ExpressionResolver[] expressionResolvers;

	public ExpressionResolverImpl(ExpressionResolver[] expressionResolvers) {
		this.expressionResolvers = expressionResolvers;
	}

	@Override
	public boolean isSupport(String... tokens) {
		return true;
	}

	@Override
	public Expression resolve(String... tokens) {
		for (ExpressionResolver expressionResolver : expressionResolvers) {
			if (expressionResolver.isSupport(tokens)) {
				return expressionResolver.resolve(tokens);
			}
		}
		throw new SyntaxInterpretatorException("Unsupported expression: " + String.join(" ", tokens));
	}
}
