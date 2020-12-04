
package labs.faces.jeneric.ast.expressions;

import labs.faces.jeneric.interfaces.JavaExpression;

public abstract class JavaBinaryOperation implements JavaExpression
{
	public enum JavaBinaryOperationSymbol
	{
		ADD, SUB, MUL, DIV, MOD, BIT_AND, BIT_OR, BIT_XOR, SHIFT_LEFT, SHIFT_RIGHT, SHIFT_RIGHT_UNSIGNED,

		AND, OR, EQ, NE, LT, LE, GT, GE,

		INSTANCE_OF,

		ASSIGN, ADD_ASSIGN, SUB_ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, BIT_AND_ASSIGN, BIT_OR_ASSIGN,
		BIT_XOR_ASSIGN, SHIFT_LEFT_ASSIGN, SHIFT_RIGHT_ASSIGN, SHIFT_RIGHT_UNSIGNED_ASSIGN,
	}

	public JavaBinaryOperationSymbol symbol;

	public JavaExpression left;
	public JavaExpression right;
}
