package java.ast.statement;

import java.ast.expression.JExpression;
import java.util.LinkedList;

public class JWhile
{
	public JExpression condition;
	public LinkedList<JStatement> body;
}
