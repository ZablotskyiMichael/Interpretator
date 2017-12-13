package interpretator.component.impl.expressionresolver;

import interpretator.model.Expression;
import interpretator.model.expression.ArrayElementByIndexExpression;

public class ArrayElementByIndexExpressionResolver extends ArrayEmptyDeclarationExpressionResolver {

    @Override
    public boolean isSupport(String... tokens) {
        return tokens.length >= 4 && "[".equals(tokens[1]);
    }

    @Override
    protected Expression createExpression(String token1, Expression indexExpression) {
        return new ArrayElementByIndexExpression(token1, indexExpression);
    }
}
