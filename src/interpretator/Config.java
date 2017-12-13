package interpretator;

import interpretator.component.*;
import interpretator.component.impl.*;
import interpretator.component.impl.calculator.bit.BitwiseAndBinaryCalculator;
import interpretator.component.impl.calculator.bit.BitwiseComplimentUnaryCalculator;
import interpretator.component.impl.calculator.bit.BitwiseOrBinaryCalculator;
import interpretator.component.impl.calculator.bit.BitwiseXORBinaryCalculator;
import interpretator.component.impl.calculator.arithmetic.DecrementUnaryCalculator;
import interpretator.component.impl.calculator.arithmetic.DivisionBinaryCalculator;
import interpretator.component.impl.calculator.arithmetic.ModulusBinaryCalculator;
import interpretator.component.impl.calculator.predicate.EqualToBinaryCalculator;
import interpretator.component.impl.calculator.predicate.GreaterBinaryCalculator;
import interpretator.component.impl.calculator.predicate.GreaterOrEqualToBinaryCalculator;
import interpretator.component.impl.calculator.arithmetic.IncrementUnaryCalculator;
import interpretator.component.impl.calculator.bit.LeftShiftBinaryCalculator;
import interpretator.component.impl.calculator.predicate.LessBinaryCalculator;
import interpretator.component.impl.calculator.predicate.LessOrEqualToBinaryCalculator;
import interpretator.component.impl.calculator.logic.LogicalAndBinaryCalculator;
import interpretator.component.impl.calculator.logic.LogicalNoUnaryCalculator;
import interpretator.component.impl.calculator.logic.LogicalOrBinaryCalculator;
import interpretator.component.impl.calculator.arithmetic.MinusBinaryCalculator;
import interpretator.component.impl.calculator.arithmetic.MinusUnaryCalculator;
import interpretator.component.impl.calculator.predicate.NotEqualToBinaryCalculator;
import interpretator.component.impl.calculator.arithmetic.PlusBinaryCalculator;
import interpretator.component.impl.calculator.arithmetic.PlusUnaryCalculator;
import interpretator.component.impl.calculator.bit.RightShiftBinaryCalculator;
import interpretator.component.impl.calculator.arithmetic.TimesBinaryCalculator;
import interpretator.component.impl.calculator.bit.ZeroFillRightShiftBinaryCalculator;
import interpretator.component.impl.expressionresolver.*;
import interpretator.component.impl.operations.*;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Config {
	public static final Object VOID = new Object();
	public static VariableStorage variableStorage = new VariableStorageImpl();
	public static FunctionStorage functionStorage;
	public static final StringSignificantVerificator verificator = new StringSignificantVerificatorImpl();
	public static final SourceLineReader sourceLineReader = new FileSourceLineReader(verificator);
	public static final TokenParser tokenParser = new TokenParserImpl();
	public static final OperationTreeBuilder operationTreeBuilder = new OperationTreeBuilderImpl(tokenParser);

	private static final Map<String, BinaryCalculator> binaryCalculatorMap = new HashMap<String, BinaryCalculator>() {
		{
			put("-", new MinusBinaryCalculator());
			put("+", new PlusBinaryCalculator());
			put("*", new TimesBinaryCalculator());
			put("/", new DivisionBinaryCalculator());
			put("%", new ModulusBinaryCalculator());
			put("==", new EqualToBinaryCalculator());
			put("!=", new NotEqualToBinaryCalculator());
			put(">", new GreaterBinaryCalculator());
			put("<", new LessBinaryCalculator());
			put(">=", new GreaterOrEqualToBinaryCalculator());
			put("<=", new LessOrEqualToBinaryCalculator());
			put("&", new BitwiseAndBinaryCalculator());
			put("|", new BitwiseOrBinaryCalculator());
			put(">>", new RightShiftBinaryCalculator());
			put("<<", new LeftShiftBinaryCalculator());
			put(">>>", new ZeroFillRightShiftBinaryCalculator());
			put("^", new BitwiseXORBinaryCalculator());
			put("&&", new LogicalAndBinaryCalculator());
			put("||", new LogicalOrBinaryCalculator());
		}
	};

	private static final Map<String, BinaryCalculator> binaryAssignmentCalculatorMap = new HashMap<String, BinaryCalculator>() {
		{
			put("+=", new PlusBinaryCalculator());
			put("-=", new MinusBinaryCalculator());
			put("*=", new TimesBinaryCalculator());
			put("/=", new DivisionBinaryCalculator());
			put("%=", new ModulusBinaryCalculator());
			put("&=", new BitwiseAndBinaryCalculator());
			put("|=", new BitwiseOrBinaryCalculator());
			put(">>=", new RightShiftBinaryCalculator());
			put("<<=", new LeftShiftBinaryCalculator());
			put(">>>=", new ZeroFillRightShiftBinaryCalculator());
			put("^=", new BitwiseXORBinaryCalculator());
		}
	};

	private static final Map<String, UnaryCalculator> unaryCalculatorMap = new HashMap<String, UnaryCalculator>() {
		{
			put("!", new LogicalNoUnaryCalculator());
			put("-", new MinusUnaryCalculator());
			put("+", new PlusUnaryCalculator());
			put("~", new BitwiseComplimentUnaryCalculator());
		}
	};

	private static final Map<String, UnaryCalculator> unaryIncDecCalculatorMap = new HashMap<String, UnaryCalculator>() {
		{
			put("++", new IncrementUnaryCalculator());
			put("--", new DecrementUnaryCalculator());
		}
	};

	public static final OperationInterpretator[] operationInterpretators = { 
			new VarDeclarationOperationInterpretator(),
			new OutlnOperationInterpretator(),
			new OutOperationInterpretator(),
			new InOperationInterpretator(),
			new AssignmentOperationInterpretator(),
			new OperationAndAssignmentOperationInterpretator(binaryAssignmentCalculatorMap.keySet()),
			new IfOperationInterpretator(),
			new IncrementAndDecrementOperationInterpretator(unaryIncDecCalculatorMap.keySet()),
			new WhileOperationInterpretator(),
			new BreakOperationInterpretator(),
			new DoWhileOperationInterpretator(),
			new ForOperationInterpretator(),
			new ReturnOperationInterpretator(),
			new FunctionInvokeOperationInterpretator()
			};

	public static final VariableVerificator variableVerificator = new VariableVerificatorImpl(operationInterpretators);
	public static final FunctionNameVerifier functionNameVerifier = new FunctionNameVerifierImpl(variableVerificator);
	public static final ContextInterpretator contextInterpretator = new ContextInterpretatorImpl(
			operationInterpretators);

	public static final ExpressionResolver functionExpressionResolver = new FunctionInvokeExpressionResolver();
	private static final ExpressionResolver singleExpressionResolver = new SingleExpressionResolver();
	private static final ExpressionResolver[] expressionResolvers = {
			new ArrayEmptyDeclarationExpressionResolver(),
			new ArrayWithValuesDeclarationExpressionResolver(),
			new ArrayLengthExpressionResolver(),
			new ArrayElementByIndexExpressionResolver(),
			singleExpressionResolver,
			functionExpressionResolver,
			new BinaryExpressionResolver(singleExpressionResolver, binaryCalculatorMap, binaryAssignmentCalculatorMap),
			new UnaryExpressionResolver(singleExpressionResolver, unaryCalculatorMap, unaryIncDecCalculatorMap)
	};
	public static final ExpressionResolver expressionResolver = new ExpressionResolverImpl(expressionResolvers);
	public static final FunctionStorageBuilder functionStorageBuilder =
			new FunctionStorageBuilderImpl(tokenParser, operationTreeBuilder);
	public static final Interpretator interpretator = new InterpretatorImpl(sourceLineReader, functionStorageBuilder,
			contextInterpretator);
}
