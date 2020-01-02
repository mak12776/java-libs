package cgeneric.expressions;

import cgeneric.interfaces.CExpression;

public class CUnaryOperation
{
	public enum CUnaryOperationSymbol
	{
		ADD, SUB, 
	}
	
	public CUnaryOperationSymbol symbol;
	public CExpression value;
}
