package jenetic.ast.expressions;

import jenetic.interfaces.JavaExpression;

public abstract class JavaBinaryOperation implements JavaExpression
{
	public static enum JavaBinaryOperationSymbol
	{
		ADD, SUB, MUL, DIV, MOD,
		EQ, NE, LT, LE, GT, GE, 
		INSTANCE_OF,
		SHL, SHR, SHRU,
		AND, OR, 
		BIT_AND, BIT_OR, BIT_XOR, 
	}
	
	public JavaBinaryOperationSymbol symbol;
	
	public JavaExpression left;
	public JavaExpression right;
}
