package jenetic.ast.expressions;

import jenetic.interfaces.JavaExpression;

public abstract class JavaUnaryOperation
{
	public static enum JavaUnarySymbol 
	{ 
		NOT, BIT_NOT,
		POST_ADD, POST_SUB,
		PRE_ADD, PRE_SUB,
		
	}
	
	public JavaUnarySymbol symbol;
	
	public JavaExpression value;
}
