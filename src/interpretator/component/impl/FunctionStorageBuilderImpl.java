package interpretator.component.impl;

import interpretator.component.FunctionStorage;
import interpretator.component.FunctionStorageBuilder;
import interpretator.component.OperationTreeBuilder;
import interpretator.component.TokenParser;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.Function;
import interpretator.model.Operation;
import interpretator.model.SourceLine;

import java.util.*;

import static interpretator.Config.variableVerificator;

public class FunctionStorageBuilderImpl implements FunctionStorageBuilder {
    private final TokenParser tokenParser;
    private final OperationTreeBuilder operationTreeBuilder;

    public FunctionStorageBuilderImpl(TokenParser tokenParser, OperationTreeBuilder operationTreeBuilder) {
        this.tokenParser = tokenParser;
        this.operationTreeBuilder = operationTreeBuilder;
    }

    @Override
    public FunctionStorage build(SourceLine[] sourceLines) {
        ListIterator<SourceLine> iterator = Arrays.asList(sourceLines).listIterator();
        Map<String, Function> map = new HashMap<>();
        populateFunctionMap(map, iterator);
        return new FunctionStorageImpl(map);
    }

    private void populateFunctionMap(Map<String, Function> map, ListIterator<SourceLine> iterator) {
        while (iterator.hasNext()) {
            SourceLine sourceLine = iterator.next();
            String[] tokens = tokenParser.parse(sourceLine.getStr());
            validateFunctionSyntax(tokens);
            Function function = readFunction(tokens, iterator);
            if (map.put(function.getName(), function) != null) {
                throw new SyntaxInterpretatorException(
                        String.format("Function %s is already defined", function.getName()));
            }
        }
    }

    private Function readFunction(String[] tokens, ListIterator<SourceLine> iterator) {
        String name = tokens[1];
        List<String> args = readFunctionArguments(tokens);
        List<SourceLine> bodySourceLines = readBody(iterator);
        Operation[] operations = operationTreeBuilder.build(bodySourceLines.toArray(new SourceLine[0]));
        return new Function(name, args, Arrays.asList(operations));
    }

    private List<SourceLine> readBody(ListIterator<SourceLine> iterator) {
        List<SourceLine> lines = new ArrayList<>();
        int blockCount = 1;
        while (iterator.hasNext()) {
            SourceLine sourceLine = iterator.next();
            String[] tokens = tokenParser.parse(sourceLine.getStr());
            blockCount = updateBlockCount(blockCount, tokens);
            if(blockCount == 0) {
                return lines;
            } else {
                lines.add(sourceLine);
            }
        }
        throw new SyntaxInterpretatorException("Missing }");
    }

    private int updateBlockCount(int blockCount, String[] tokens) {
        int openBlockCount = 0;
        int closeBlockCount = 0;
        for (String token : tokens) {
            if("{".equals(token)) {
                openBlockCount++;
            } else if ("}".equals(token)) {
                closeBlockCount ++;
            }
        }
        return blockCount + openBlockCount - closeBlockCount;
    }

    private List<String> readFunctionArguments(String[] tokens) {
        List<String> args = new ArrayList<>();
        boolean start = false;
        List<String> tokenBuilder = new ArrayList<>();
        for (String token : tokens) {
            if (")".equals(token)) {
                if(tokenBuilder.size() > 0) {
                    addArgument(args, tokenBuilder);
                }
                return args;
            } else if(start) {
                 if(",".equals(token)) {
                     addArgument(args, tokenBuilder);
                 } else {
                     tokenBuilder.add(token);
                 }
            } if ("(".equals(token)) {
                start = true;
            }
        }
        return args;
    }

    private void addArgument(List<String> args, List<String> tokenBuilder) {
        if(tokenBuilder.size() == 0) {
            throw new SyntaxInterpretatorException("Variable name expected");
        }
        if(tokenBuilder.size() > 1) {
            throw new SyntaxInterpretatorException("Variable name expected");
        }
        variableVerificator.verify(tokenBuilder.get(0));
        args.add(tokenBuilder.get(0));
        tokenBuilder.clear();
    }

    private void validateFunctionSyntax(String[] tokens) {
        if (!"function".equals(tokens[0])) {
            throw new SyntaxInterpretatorException("Expected function key word");
        }
        if (tokens.length < 3) {
            throw new SyntaxInterpretatorException("Invalid function declaration syntax");
        }
        if (!"(".equals(tokens[2])) {
            throw new SyntaxInterpretatorException("Invalid function declaration syntax");
        }
        if (!")".equals(tokens[tokens.length - 2])) {
            throw new SyntaxInterpretatorException("Invalid function declaration syntax");
        }
        if (!"{".equals(tokens[tokens.length - 1])) {
            throw new SyntaxInterpretatorException("Invalid function declaration syntax");
        }
    }
}
