
package jeneric.ast.expressions;

import jeneric.interfaces.JavaExpression;

public abstract class JavaUnaryOperation
{
	public enum JavaUnarySymbol
	{
		POSTIVE, NEGATIVE, LOGIC_NOT, BITWISE_NOT, PRE_ADD, POST_ADD, PRE_SUB, POST_SUB,
	}

	public JavaUnarySymbol symbol;
	public JavaExpression value;
}
