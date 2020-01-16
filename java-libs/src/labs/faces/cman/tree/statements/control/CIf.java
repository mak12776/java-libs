
package labs.faces.cman.tree.statements.control;

import java.util.List;

import labs.faces.cman.interfaces.CExpression;
import labs.faces.cman.interfaces.CStatement;

public class CIf implements CStatement
{
	public CExpression test;
	public List<CStatement> statements;
}
