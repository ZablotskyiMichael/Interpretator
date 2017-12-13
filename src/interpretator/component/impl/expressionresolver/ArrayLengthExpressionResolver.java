package interpretator.component.impl.expressionresolver;

import interpretator.component.ExpressionResolver;
import interpretator.model.Expression;
import interpretator.model.expression.ArrayLengthExpression;

import static interpretator.Config.variableVerificator;

public class ArrayLengthExpressionResolver implements ExpressionResolver {
    @Override
    public boolean isSupport(String... tokens) {
        return tokens.length > 0 && "len".equals(tokens[0]);
    }

    @Override
    public Expression resolve(String... tokens) {
        variableVerificator.verify(tokens[1]);
        return new ArrayLengthExpression(tokens[1]);
    }
}
