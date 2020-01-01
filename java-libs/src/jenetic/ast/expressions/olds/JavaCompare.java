package jenetic.ast.expressions.olds;

import jenetic.ast.expressions.JavaBinaryOperation;

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
