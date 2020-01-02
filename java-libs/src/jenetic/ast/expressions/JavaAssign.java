package jenetic.ast.expressions;

import jenetic.ast.nodes.JavaCompoundName;
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
	
	public JavaCompoundName name;
	public JavaExpression value;
}