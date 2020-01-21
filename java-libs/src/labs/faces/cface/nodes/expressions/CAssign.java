
package labs.faces.cface.nodes.expressions;

import labs.faces.cface.interfaces.CExpression;

public class CAssign implements CExpression
{
	public enum CAssignSymbol
	{
		EQ, ADD, SUB, MUL, DIV, MOD, BIT_AND, BIT_OR, BIT_XOR, SHL, SHR,
	}

	public CAssignSymbol symbol;

	public CExpression left;
	public CExpression right;
}
