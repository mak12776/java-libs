package cgeneric.expressions;

import cgeneric.interfaces.CExpression;

public class CUnaryOperation
{
	public enum CUnaryOperationSymbol
	{
		POSITIVE, NEGATIVE,
		POST_ADD, POST_SUB,
	}
	
	public CUnaryOperationSymbol symbol;
	public CExpression value;
}
