
package labs.faces.cman.tree.expressions;

import labs.faces.cman.interfaces.CExpression;

public class CBinaryOperation implements CExpression
{
	public enum CBinarySymbol
	{
		ADD, SUB, MUL, DIV, MOD, EQ, NE, LT, LE, GT, GE,
	}

	public CBinarySymbol symbol;

	public CExpression left;
	public CExpression right;
}