
package cman.tree.expressions;

import cman.interfaces.CExpression;

public class CUnaryOperation implements CExpression
{
	public enum CUnaryOperationSymbol
	{
		POSITIVE, NEGATIVE, POST_ADD, POST_SUB, PRE_ADD, PRE_SUB, NOT, BIT_NOT, INDIRECT, ADDR_OF, SIZEOF,
	}

	public CUnaryOperationSymbol symbol;
	public CExpression value;
}
