package jenetic.ast.expressions;

import jenetic.interfaces.JavaExpression;

public abstract class JavaUnaryOperation
{
	public static enum JavaUnarySymbol
	{
		NOT, BIT_NOT
	}
	
	public JavaUnarySymbol symbol;
	
	public JavaExpression value;
}
