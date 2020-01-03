package jenetic.ast.expressions;

import jenetic.interfaces.JavaExpression;

public class JavaAssign implements JavaExpression
{
	public static enum JavaAssignSymbol
	{
		EQ, 
		ADD, SUB, MUL, DIV, MOD,
		BIT_AND, BIT_OR, BIT_XOR,
		SHL, SHR, SHRU,
	}
	
	public JavaAssignSymbol symbol;
	
	public JavaExpression left;
	public JavaExpression right;
}
