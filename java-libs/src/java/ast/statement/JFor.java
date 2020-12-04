package java.ast.statement;

import java.ast.expression.JExpression;
import java.util.LinkedList;

public class JFor
{
	public JExpression init;
	public JExpression test;
	public JExpression step;
	
	public LinkedList<JStatement> body;
}
