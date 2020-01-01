package jenetic.ast.expressions.operations;

import jenetic.interfaces.JavaExpression;

public abstract class JavaBinaryOperation implements JavaExpression
{
	public JavaExpression left;
	public JavaExpression right;
}
