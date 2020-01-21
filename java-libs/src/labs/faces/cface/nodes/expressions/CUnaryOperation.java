
package labs.faces.cface.nodes.expressions;

import labs.faces.cface.interfaces.CExpression;

public class CUnaryOperation implements CExpression
{
	public enum CUnaryOperationSymbol
	{
		PLUS, MINUS, 
		
		POST_ADD, POST_SUB, 
		PRE_ADD, PRE_SUB, 
		
		NOT, BIT_NOT, 
		
		INDIRECT, ADDR_OF, SIZEOF,
	}

	public CUnaryOperationSymbol symbol;
	public CExpression value;
}
