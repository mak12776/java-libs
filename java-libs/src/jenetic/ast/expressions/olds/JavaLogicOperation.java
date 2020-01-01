package jenetic.ast.expressions.olds;

import jenetic.ast.expressions.JavaBinaryOperation;

public class JavaLogicOperation extends JavaBinaryOperation
{
	public static enum JavaLogicSymbol
	{
		AND, 
		OR,
	}
	
	public JavaLogicSymbol symbol;
}
