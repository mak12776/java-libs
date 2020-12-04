package java.ast.statement;

import java.util.AbstractMap.SimpleEntry;
import java.ast.expression.JExpression;
import java.util.LinkedList;

public class JIf
{
	public JExpression condition;
	public LinkedList<JStatement> body;
	
	public LinkedList<SimpleEntry<JExpression, JStatement>> ifElseList;
	
	public LinkedList<JStatement> elseBody;
}
