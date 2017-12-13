package interpretator.component.impl.expressionresolver;

import interpretator.component.ExpressionResolver;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Expression;
import interpretator.model.expression.ArrayWithValuesDeclarationExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static interpretator.Config.expressionResolver;

public class ArrayWithValuesDeclarationExpressionResolver implements ExpressionResolver {
    @Override
    public boolean isSupport(String... tokens) {
        return tokens.length > 0 && "{".equals(tokens[0]);
    }

    @Override
    public Expression resolve(String... tokens) {
        validateSyntax(tokens);
        List<String[]> tokenGroup = groupByComma(Arrays.copyOfRange(tokens, 1, tokens.length-1));
        List<Expression> expressions = tokenGroup.stream()
                .map(t -> expressionResolver.resolve(t))
                .collect(Collectors.toList());
        /*List<Expression> expressions = new ArrayList<>();
        for (String [] token : tokenGroup) {
            expressions.add(expressionResolver.resolve(token));
        }*/
        return new ArrayWithValuesDeclarationExpression(expressions);
    }

    private List<String[]> groupByComma(String[] tokens) {
        if(tokens.length == 0) {
            return Collections.emptyList();
        }
        List<String[]> result = new ArrayList<>();
        List<String> tokenBuilder = new ArrayList<>();
        for (String token : tokens) {
            if(",".equals(token)) {
                addTokenToResult(result, tokenBuilder);
            } else {
                tokenBuilder.add(token);
            }
        }
        addTokenToResult(result, tokenBuilder);
        return result;
    }

    private void addTokenToResult(List<String[]> result, List<String> tokenBuilder) {
        if(tokenBuilder.isEmpty()) {
            throw new SyntaxInterpretatorException("Missing array element");
        }
        result.add(tokenBuilder.toArray(new String[0]));
        tokenBuilder.clear();;
    }

    private void validateSyntax(String[] tokens) {
        if(!"}".equals(tokens[tokens.length - 1])) {
            throw new SyntaxInterpretatorException("Missing }");
        }
    }
}
