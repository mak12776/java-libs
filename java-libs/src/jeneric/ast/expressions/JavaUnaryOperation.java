package jeneric.ast.expressions;

import jeneric.interfaces.JavaExpression;

public abstract class JavaUnaryOperation
{
	public static enum JavaUnarySymbol 
	{
		POSTIVE, NEGATIVE,
		LOGIC_NOT, BITWISE_NOT,
		POST_ADD, POST_SUB,
		PRE_ADD, PRE_SUB,
	}
	
	public JavaUnarySymbol symbol;
	
	public JavaExpression value;
}
