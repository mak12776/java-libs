package jenetic.ast.expressions;

import jenetic.ast.expressions.operations.JavaBinaryOperation;

public class JavaLogicOperation extends JavaBinaryOperation
{
	public static enum JavaLogicSymbol
	{
		AND, 
		OR,
	}
	
	public JavaLogicSymbol symbol;
}
