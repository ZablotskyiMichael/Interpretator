package interpretator.component.impl;

import interpretator.component.OperationInterpretator;
import interpretator.component.VariableVerificator;
import interpretator.exception.SyntaxInterpretatorException;

public class VariableVerificatorImpl implements VariableVerificator {
	private OperationInterpretator[] operationInterpretators;
	private String[] expressionKeyWords = { "null", "true", "false" };

	public VariableVerificatorImpl(OperationInterpretator[] operationInterpretators) {
		this.operationInterpretators = operationInterpretators;
	}

	@Override
	public void verify(String varName) {
		verifyFirstLetterOfVariableName(varName);
		verifyExistingInvalidCharInVariableName(varName);
		verifyUsingOfKeywordInVariableName(varName);
		verifyUsingOfExpressionKeywords(varName);
	}

	private void verifyUsingOfExpressionKeywords(String varName) {
		for (String expressionKeyWord : expressionKeyWords) {
			if (expressionKeyWord.equals(varName)) {
				throw new SyntaxInterpretatorException(
						"Invalid variable name: " + "Keyword can't be a variable name: " + varName);
			}
		}
	}

	private void verifyFirstLetterOfVariableName(String varName) {
		char first = varName.charAt(0);
		if (!Character.isLetter(first) && first != '_') {
			throw new SyntaxInterpretatorException("Invalid variable name: " + "First symbol should be a letter or _");
		}
	}

	private void verifyUsingOfKeywordInVariableName(String varName) {
		for (OperationInterpretator operationInterpretator : operationInterpretators) {
			if (varName.equals(operationInterpretator.getKeyWord())) {
				throw new SyntaxInterpretatorException(
						"Invalid variable name: " + "Keyword can't be a variable name: " + varName);
			}
		}
	}

	private void verifyExistingInvalidCharInVariableName(String varName) {
		for (int i = 0; i < varName.length(); i++) {
			char ch = varName.charAt(i);
			if (!Character.isLetter(ch) && !Character.isDigit(ch) && ch != '_') {
				throw new SyntaxInterpretatorException("Invalid variable name: " + "Detected invalid symbol: " + ch);
			}
		}
	}
}
