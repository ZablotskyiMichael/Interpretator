package interpretator.component.impl;

import interpretator.component.OperationTreeBuilder;
import interpretator.component.TokenParser;
import interpretator.exception.ExceptionHelper;
import interpretator.exception.SyntaxInterpretatorException;
import interpretator.model.operation.DoWhileOperation;
import interpretator.model.operation.ForOperation;
import interpretator.model.operation.IfOperation;
import interpretator.model.Operation;
import interpretator.model.operation.SimpleOperation;
import interpretator.model.SourceLine;
import interpretator.model.operation.WhileOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class OperationTreeBuilderImpl implements OperationTreeBuilder {
	private TokenParser tokenParser;

	public OperationTreeBuilderImpl(TokenParser tokenParser) {
		this.tokenParser = tokenParser;
	}

	@Override
	public Operation[] build(SourceLine[] sourceLines) {
		List<Operation> operations = new ArrayList<>();
		ListIterator<SourceLine> iterator = Arrays.asList(sourceLines).listIterator();
		readOperations(operations, iterator, false);
		return operations.toArray(new Operation[0]);
	}

	private void readOperations(List<Operation> operations, ListIterator<SourceLine> iterator, boolean expectedClose) {
		while (iterator.hasNext()) {
			SourceLine sourceLine = iterator.next();
			ExceptionHelper.setCurrentSourceLine(sourceLine);
			String[] tokens = tokenParser.parse(sourceLine.getStr());
			if ("}".equals(tokens[0]) || "case".equals(tokens[0]) || "default".equals(tokens[0])) {
				return;
			}
			readOperations(sourceLine, tokens, operations, iterator);
		}
		if (expectedClose) {
			throw new SyntaxInterpretatorException("Missing }");
		}
	}

	private void readOperations(SourceLine sourceLine, String[] tokens, List<Operation> operations,
			ListIterator<SourceLine> iterator) {
		if ("if".equals(tokens[0])) {
			operations.add(readIf(sourceLine, tokens, iterator));
		} else if ("while".equals(tokens[0])) {
			operations.add(readWhile(sourceLine, tokens, iterator));
		} else if ("do".equals(tokens[0])) {
			operations.add(readDoWhile(sourceLine, tokens, iterator));
		} else if ("for".equals(tokens[0])) {
			operations.add(readFor(sourceLine, tokens, iterator));
		}  else {
			operations.add(new SimpleOperation(tokens, sourceLine));
		}
	}


	
	private Operation readFor(SourceLine sourceLine, String[] tokens, ListIterator<SourceLine> iterator) {
		checkForSyntax(tokens);
		List<Operation> trueOperations = new ArrayList<>();
		readOperations(trueOperations, iterator, true);
		String[] loopControlExpression = Arrays.copyOfRange(tokens, 2, tokens.length - 2);
		checkLoopControlExpressionSyntax(loopControlExpression);
		int index = Arrays.asList(loopControlExpression).indexOf(";");
		Operation initialization = new SimpleOperation(noEmptyExpression(loopControlExpression, 0, index), sourceLine);
		String[] restLoopControlExpression = Arrays.copyOfRange(loopControlExpression, index + 1,
				loopControlExpression.length);
		index = Arrays.asList(restLoopControlExpression).indexOf(";");
		String[] conditionalExpression = noEmptyExpression(restLoopControlExpression, 0, index);
		Operation update = new SimpleOperation(
				noEmptyExpression(restLoopControlExpression, index + 1, restLoopControlExpression.length), sourceLine);
		return new ForOperation(tokens, sourceLine, trueOperations, initialization, conditionalExpression, update);
	}

	private String[] noEmptyExpression(String[] controlExpression, int start, int end) {
		String[] expression = Arrays.copyOfRange(controlExpression, start, end);
		if (expression == null) {
			expression = tokenParser.parse("");
		}
		return expression;
	}

	private void checkLoopControlExpressionSyntax(String[] loopControlExpression) {
		int countSemicolon = 0;
		if (loopControlExpression != null) {
			for (int i = 0; i < loopControlExpression.length; i++) {
				if (";".equals(loopControlExpression[i])) {
					countSemicolon += 1;
				}
			}
		}
		if (loopControlExpression == null || countSemicolon != 2) {
			throw new SyntaxInterpretatorException("Invalid syntax for loop control expression");
		}
	}

	private void checkForSyntax(String[] tokens) {
		if (tokens.length < 6 || !"{".equals(tokens[tokens.length - 1])) {
			throw new SyntaxInterpretatorException("Invalid syntax for loop for");
		}
	}

	// FIXMY do-while
	private Operation readDoWhile(SourceLine sourceLine, String[] tokens, ListIterator<SourceLine> iterator) {
		checkElseAndDoSyntax(tokens);
		List<Operation> trueOperations = new ArrayList<>();
		readOperations(trueOperations, iterator, true);
		String[] conditionalExpression = null;
		if (iterator.hasNext()) {
			SourceLine doWhileSourceLine = iterator.next();
			ExceptionHelper.setCurrentSourceLine(doWhileSourceLine);
			String[] doWhileTokens = tokenParser.parse(doWhileSourceLine.getStr());
			if (checkDoWhileSyntax(doWhileTokens)) {
				conditionalExpression = Arrays.copyOfRange(doWhileTokens, 2, doWhileTokens.length - 1);
				return new DoWhileOperation(tokens, sourceLine, trueOperations, conditionalExpression);
			} else {
				iterator.previous();
			}
		}
		conditionalExpression = tokenParser.parse("false");
		return new DoWhileOperation(tokens, sourceLine, trueOperations, conditionalExpression);
	}

	private boolean checkDoWhileSyntax(String[] doWhileTokens) {
		if (!"while".equals(doWhileTokens[0])) {
			return false;
		}
		if (doWhileTokens.length < 4 || !")".equals(doWhileTokens[doWhileTokens.length - 1])) {
			return false;
		}
		return true;
	}

	// ---------------------------------------------
	private Operation readIf(SourceLine sourceLine, String[] tokens, ListIterator<SourceLine> iterator) {
		checkBasicSyntax(tokens);
		List<Operation> trueOperations = new ArrayList<>();
		readOperations(trueOperations, iterator, true);
		if (iterator.hasNext()) {
			SourceLine elseSourceLine = iterator.next();
			ExceptionHelper.setCurrentSourceLine(elseSourceLine);
			String[] elseTokens = tokenParser.parse(elseSourceLine.getStr());
			if (elseTokens.length >= 2 && "else".equals(elseTokens[0]) && "if".equals(elseTokens[1])) {
				checkElseIfSyntax(elseTokens);
				return new IfOperation(tokens, sourceLine, trueOperations, (IfOperation) readIf(elseSourceLine,
						Arrays.copyOfRange(elseTokens, 1, elseTokens.length), iterator));
			} else if ("else".equals(elseTokens[0])) {
				checkElseAndDoSyntax(elseTokens);
				List<Operation> falseOperations = new ArrayList<>();
				readOperations(falseOperations, iterator, true);
				return new IfOperation(tokens, sourceLine, trueOperations, falseOperations);
			} else {
				iterator.previous();
			}
		}
		return new IfOperation(tokens, sourceLine, trueOperations);
	}

	private void checkElseIfSyntax(String[] tokens) {
		if (tokens.length < 6 || !"{".equals(tokens[tokens.length - 1])) {
			throw new SyntaxInterpretatorException("Invalid syntax for else if");
		}
	}

	private void checkElseAndDoSyntax(String[] tokens) {
		if (tokens.length != 2 || !"{".equals(tokens[tokens.length - 1])) {
			throw new SyntaxInterpretatorException(String.format("Invalid syntax for %s", tokens[0]));
		}
	}

	private Operation readWhile(SourceLine sourceLine, String[] tokens, ListIterator<SourceLine> iterator) {
		checkBasicSyntax(tokens);
		List<Operation> trueOperations = new ArrayList<>();
		readOperations(trueOperations, iterator, true);
		return new WhileOperation(tokens, sourceLine, trueOperations);
	}

	private void checkBasicSyntax(String[] tokens) {
		if (tokens.length < 5 || !"{".equals(tokens[tokens.length - 1])) {
			throw new SyntaxInterpretatorException(String.format("Invalid syntax for %s", tokens[0]));
		}
	}
}
