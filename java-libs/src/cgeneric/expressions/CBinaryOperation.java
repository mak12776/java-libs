package cgeneric.expressions;

import cgeneric.interfaces.CExpression;

public class CBinaryOperation
{
	public static enum CBinarySymbol
	{
		ADD, SUB, MUL, DIV, MOD,
		EQ, NE, LT, LE, GT, GE,
	}
	
	public CBinarySymbol symbol;
	
	public CExpression left;
	public CExpression right;
}
