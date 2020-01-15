
package libs.faces.cman.tree.statements.control;

import java.util.List;

import libs.faces.cman.interfaces.CExpression;
import libs.faces.cman.interfaces.CStatement;

public class CIf implements CStatement
{
	public CExpression test;
	public List<CStatement> statements;
}
