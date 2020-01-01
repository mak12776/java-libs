package jenetic.ast.expressions;

import jenetic.interfaces.JavaExpression;

public abstract class JavaBinaryOperation implements JavaExpression
{
	public static enum JavaBinaryOperationSymbol
	{
		ADD, SUB, MUL, DIV,
		EQ, NE, LT, LE, GT, GE,
		AND, OR, BIT_AND, BIT_OR, 
	}
	
	public JavaBinaryOperationSymbol symbol;
	
	public JavaExpression left;
	public JavaExpression right;
}
