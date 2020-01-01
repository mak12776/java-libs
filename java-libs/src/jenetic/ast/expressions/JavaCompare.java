package jenetic.ast.expressions;

import jenetic.ast.expressions.operations.JavaBinaryOperation;

public class JavaCompare extends JavaBinaryOperation
{	
	public static enum JavaCompareSymbol
	{
		EQ, NE,
		LT, LE,
		GT, GE,
	}
	
	public JavaCompareSymbol symbol;
}
