package interpretator.component.impl.expressionresolver;

import interpretator.component.ExpressionResolver;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Expression;
import interpretator.model.expression.ArrayEmptyDeclarationExpression;

import java.util.Arrays;

import static interpretator.Config.expressionResolver;

public class ArrayEmptyDeclarationExpressionResolver implements ExpressionResolver {

    @Override
    public boolean isSupport(String... tokens) {
        return tokens.length > 0 && "array".equals(tokens[0]);
    }

    @Override
    public Expression resolve(String... tokens) {
        validateSyntax(tokens);
        String[] sizeTokens = Arrays.copyOfRange(tokens, 2, tokens.length-1);
        Expression sizeExpression = expressionResolver.resolve(sizeTokens);
        return createExpression(tokens[0], sizeExpression);
    }

    protected Expression createExpression(String token1, Expression sizeExpression) {
        return new ArrayEmptyDeclarationExpression(sizeExpression);
    }

    private void validateSyntax(String[] tokens) {
        if(tokens.length < 4) {
            throw new SyntaxInterpretatorException("Invalid array declaration syntax");
        }
        if(!"[".equals(tokens[1])) {
            throw new SyntaxInterpretatorException("Missing [");
        }
        if(!"]".equals(tokens[tokens.length - 1])) {
            throw new SyntaxInterpretatorException("Missing ]");
        }
    }
}
