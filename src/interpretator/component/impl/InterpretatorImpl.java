package interpretator.component.impl;

import interpretator.Config;
import interpretator.component.*;
import interpretator.exception.AbstractInterpretatorException;
import interpretator.exception.TernimateException;
import interpretator.model.Function;
import interpretator.model.Operation;
import interpretator.model.SourceLine;

public class InterpretatorImpl implements Interpretator {
	private SourceLineReader sourceLineReader;
	private FunctionStorageBuilder functionStorageBuilder;
	private ContextInterpretator contextInterpretator;

	public InterpretatorImpl(SourceLineReader sourceLineReader,
							 FunctionStorageBuilder functionStorageBuilder,
							 ContextInterpretator contextInterpretator) {
		this.sourceLineReader = sourceLineReader;
		this.functionStorageBuilder = functionStorageBuilder;
		this.contextInterpretator = contextInterpretator;
	}

	@Override
	public void interpret(String sourceName) {
		try {
			SourceLine[] sourceLines = sourceLineReader.read(sourceName);
			FunctionStorage functionStorage = functionStorageBuilder.build(sourceLines);
			Config.functionStorage = functionStorage;
			Function main = functionStorage.findMain();
			invokeMainFunction(main);
		} catch (AbstractInterpretatorException e) {
			System.err.println(e.getMessage());
		} catch (TernimateException e) {
			System.err.println("Ternimated");
		} catch (StackOverflowError e) {
			System.err.println("Stack overflow");
		}
	}

	private void invokeMainFunction(Function main) {
		for (Operation operation : main.getBody()) {
			contextInterpretator.interpert(operation);
		}
	}
}
