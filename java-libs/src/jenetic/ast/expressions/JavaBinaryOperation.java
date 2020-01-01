package jenetic.ast.expressions;

import jenetic.interfaces.JavaExpression;

public abstract class JavaBinaryOperation implements JavaExpression
{
	public static enum JavaBinaryOperationSymbol
	{
		EQUAL, NOT_EQUAL,
		LESS_THAN, LESS_THAN_EQUAL,
		GREATER_THAN, GREATER_THAN_EQUAL,
		AND, OR,
		BIT_AND, BIT_OR, 
	}
	
	
	public JavaExpression left;
	public JavaExpression right;
}
