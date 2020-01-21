
package labs.faces.cface.nodes.expressions;

import labs.faces.cface.interfaces.CExpression;

public class CBinaryOperation implements CExpression
{
	public enum CBinarySymbol
	{
		AND, OR, NOT,
		EQ, NE, LT, LE, GT, GE,
		
		ADD, SUB, MUL, DIV, MOD,
		BIT_AND, BIT_OR, BIT_XOR,
		SHIFT_LEFT, SHIFT_RIGHT,
		
		ASSIGN,
		
		ADD_ASSIGN, SUB_ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN,
		BIT_AND_ASSIGN, BIT_OR_ASSIGN, BIT_XOR_ASSIGN,
		SHIFT_LEFT_ASSIGN, SHIFT_RIGHT_ASSIGN,
	}

	public CBinarySymbol symbol;

	public CExpression left;
	public CExpression right;
}
