package interpretator.component.impl.expressionresolver;

import interpretator.component.ExpressionResolver;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Expression;
import interpretator.model.expression.FunctionInvokeExpression;

import java.util.ArrayList;
import java.util.List;

import static interpretator.Config.expressionResolver;
import static interpretator.Config.functionNameVerifier;

public class FunctionInvokeExpressionResolver implements ExpressionResolver {
    @Override
    public boolean isSupport(String... tokens) {
        return tokens.length >= 3 && functionNameVerifier.isValidFunctionName(tokens[0])
                && "(".equals(tokens[1]) && ")".equals(tokens[tokens.length - 1]);
    }

    @Override
    public Expression resolve(String... tokens) {
        String name = tokens[0];
        List<Expression> arguments = new ArrayList<>();
        populateArguments(arguments, tokens);
        return new FunctionInvokeExpression(name, arguments);
    }

    private void populateArguments(List<Expression> arguments, String[] tokens) {
        boolean start = false;
        List<String> tokenBuilder = new ArrayList<>();
        for (String token : tokens) {
            if (")".equals(token)) {
                if(!tokenBuilder.isEmpty()) {
                    addArgument(arguments, tokenBuilder);
                }
                return;
            } else if(start) {
                if(",".equals(token)) {
                    addArgument(arguments, tokenBuilder);
                } else {
                    tokenBuilder.add(token);
                }
            } if ("(".equals(token)) {
                start = true;
            }
        }
    }

    private void addArgument(List<Expression> args, List<String> tokenBuilder) {
        if(tokenBuilder.size() == 0) {
            throw new SyntaxInterpretatorException("Expression expected");
        }
        args.add(expressionResolver.resolve(tokenBuilder.toArray(new String[0])));
        tokenBuilder.clear();
    }
}
