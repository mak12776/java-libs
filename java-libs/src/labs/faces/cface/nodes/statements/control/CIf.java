
package labs.faces.cface.nodes.statements.control;

import java.util.List;

import labs.faces.cface.interfaces.CExpression;
import labs.faces.cface.interfaces.CStatement;

public class CIf implements CStatement
{
	public CExpression test;
	public List<CStatement> statements;
	
	public List<CExpression> elseIfTests;
	public List<List<CStatement>> elseIfStatements;
	
	public List<CStatement> elseStatements;
}
