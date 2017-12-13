package interpretator.component.impl.expressionresolver;

import interpretator.component.ExpressionResolver;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.expression.ConstantExpression;
import interpretator.model.Expression;
import interpretator.model.expression.VariableExpression;

import static interpretator.Config.variableVerificator;

public class SingleExpressionResolver implements ExpressionResolver {
	@Override
	public boolean isSupport(String... tokens) {
		return tokens.length == 1;
	}

	@Override
	public Expression resolve(String... tokens) {
		String token = tokens[0];
		if ("null".equals(token)) {
			return new ConstantExpression(null);
		}
		if ("true".equals(token) || "false".equals(token)) {
			return new ConstantExpression(Boolean.parseBoolean(token));
		}
		if (token.startsWith("\"")) {
			return readStringConstant(token);
		}
		try {
			return new ConstantExpression(Integer.parseInt(token));
		} catch (NumberFormatException e) {
			try {
				return new ConstantExpression(Double.parseDouble(token));
			} catch (NumberFormatException e1) {
				variableVerificator.verify(token);
				return new VariableExpression(token);
			}
		}
	}

	private Expression readStringConstant(String token) {
		if (token.endsWith("\"")) {
			return new ConstantExpression(token.substring(1, token.length() - 1));
		} else {
			throw new SyntaxInterpretatorException("String constant should end with \"");
		}
	}
}
